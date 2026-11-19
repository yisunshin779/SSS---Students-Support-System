package sss.com.sss_backend_core.profile.identity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import sss.com.sss_backend_core.profile.entity.MatchStatus;
import sss.com.sss_backend_core.profile.entity.Student;
import sss.com.sss_backend_core.profile.entity.StudentXref;
import sss.com.sss_backend_core.profile.repository.StudentRepository;
import sss.com.sss_backend_core.profile.repository.StudentXrefRepository;

@Service
@RequiredArgsConstructor
public class IdentityResolutionService {

    private final StudentRepository studentRepository;
    private final StudentXrefRepository xrefRepository;

    /** SIS là nguồn gốc của MSSV, nên bản ghi SIS luôn tự khớp với chính nó. */
    @Transactional
    public StudentXref registerSis(String studentId) {
        StudentXref x = xrefRepository.findBySourceSystemAndSourceId("SIS", studentId)
                .orElseGet(StudentXref::new);
        x.setSourceSystem("SIS");
        x.setSourceId(studentId);
        return saveMatched(x, studentId, "MSSV", 1.0);
    }

    @Transactional
    public StudentXref resolve(SourceIdentity src) {
        StudentXref x = xrefRepository
                .findBySourceSystemAndSourceId(src.sourceSystem(), src.sourceId())
                .orElseGet(StudentXref::new);
        if (x.getStatus() == MatchStatus.MATCHED) return x;   // đã khớp (kể cả duyệt tay) thì giữ nguyên
        x.setSourceSystem(src.sourceSystem());
        x.setSourceId(src.sourceId());

        Optional<Student> byMssv = blank(src.studentId())
                ? Optional.empty() : studentRepository.findById(src.studentId());
        Optional<Student> byCccd = blank(src.nationalId())
                ? Optional.empty() : studentRepository.findByNationalId(src.nationalId());

        // Xung đột: hai khóa chắc chắn nhưng trỏ về hai người khác nhau
        if (byMssv.isPresent() && byCccd.isPresent()
                && !byMssv.get().getStudentId().equals(byCccd.get().getStudentId())) {
            return saveReview(x, null, "CONFLICT", 0.0, "MSSV " + byMssv.get().getStudentId()
                    + " và CCCD trỏ về MSSV " + byCccd.get().getStudentId());
        }
        if (byMssv.isPresent()) return saveMatched(x, byMssv.get().getStudentId(), "MSSV", 1.0);
        if (byCccd.isPresent()) return saveMatched(x, byCccd.get().getStudentId(), "NATIONAL_ID", 0.95);

        // Khớp mờ theo tên: không bao giờ tự động gộp
        String key = NameNormalizer.normalize(src.fullName());
        if (key.isEmpty()) return saveUnmatched(x, "Không đủ thông tin để khớp");

        List<Student> candidates = studentRepository.findAll().stream()
                .filter(s -> key.equals(NameNormalizer.normalize(s.getName())))
                .toList();

        if (candidates.size() == 1)
            return saveReview(x, candidates.get(0).getStudentId(), "NAME", 0.6, "Chỉ khớp theo tên, cần duyệt");
        if (candidates.size() > 1)
            return saveReview(x, null, "NAME", 0.3, candidates.size() + " sinh viên trùng tên");
        return saveUnmatched(x, "Không tìm thấy sinh viên nào");
    }

    /** Data steward duyệt tay một ca đang chờ. */
    @Transactional
    public StudentXref approve(Long xrefId, String studentId) {
        StudentXref x = xrefRepository.findById(xrefId)
                .orElseThrow(() -> new IllegalArgumentException("Không có xref " + xrefId));
        if (!studentRepository.existsById(studentId))
            throw new IllegalArgumentException("Không có sinh viên " + studentId);
        return saveMatched(x, studentId, "MANUAL", 1.0);
    }

    private StudentXref saveMatched(StudentXref x, String studentId, String rule, double conf) {
        return save(x, studentId, rule, conf, MatchStatus.MATCHED, null);
    }
    private StudentXref saveReview(StudentXref x, String candidate, String rule, double conf, String note) {
        return save(x, candidate, rule, conf, MatchStatus.NEEDS_REVIEW, note);
    }
    private StudentXref saveUnmatched(StudentXref x, String note) {
        return save(x, null, null, 0.0, MatchStatus.UNMATCHED, note);
    }
    private StudentXref save(StudentXref x, String studentId, String rule, double conf,
                             MatchStatus status, String note) {
        x.setStudentId(studentId);
        x.setMatchRule(rule);
        x.setConfidence(conf);
        x.setStatus(status);
        x.setNote(note);
        x.setUpdatedAt(LocalDateTime.now());
        return xrefRepository.save(x);
    }
    private boolean blank(String s) { return s == null || s.isBlank(); }
}