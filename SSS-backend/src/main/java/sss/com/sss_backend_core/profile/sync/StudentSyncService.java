package sss.com.sss_backend_core.profile.sync;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sss.com.sss_backend_core.profile.entity.Student;
import sss.com.sss_backend_core.profile.identity.IdentityResolutionService;
import sss.com.sss_backend_core.profile.repository.StudentRepository;
import sss.com.sss_backend_core.profile.service.sis.SisClient;
import sss.com.sss_backend_core.profile.service.sis.SisStudentDto;

@Service
public class StudentSyncService {

    private static final Logger log = LoggerFactory.getLogger(StudentSyncService.class);
    private static final int MIN_ADMISSION_YEAR = 2000;

    private final SisClient sisClient;
    private final StudentRepository studentRepository;
    private final IdentityResolutionService identityService;

    public StudentSyncService(SisClient sisClient,
                          StudentRepository studentRepository,
                          IdentityResolutionService identityService) {
    this.sisClient = sisClient;
    this.studentRepository = studentRepository;
    this.identityService = identityService;
    }

    private int currentAcademicYearStart() {
        LocalDate today = LocalDate.now();
        return today.getMonthValue() >= 9 ? today.getYear() : today.getYear() - 1;
    }

    public void syncFreshmen()   { int y = currentAcademicYearStart();     sync("Năm 1", y, y); }
    public void syncSophomores() { int y = currentAcademicYearStart() - 1; sync("Năm 2", y, y); }
    public void syncSeniors()    { int y = currentAcademicYearStart() - 2; sync("Năm 3+", MIN_ADMISSION_YEAR, y); }

    private void sync(String label, int fromYear, int toYear) {
        long start = System.currentTimeMillis();
        log.info("[SYNC {}] Bắt đầu, khóa nhập học {}-{}", label, fromYear, toYear);

        List<SisStudentDto> data = sisClient.fetchByAdmissionYear(fromYear, toYear);
        int saved = 0, failed = 0;
        for (SisStudentDto dto : data) {
            try {
                upsert(dto);
                saved++;
            } catch (Exception e) {
                failed++;
                // Không log nationalId vì là dữ liệu nhạy cảm
                log.warn("[SYNC {}] Lỗi bản ghi MSSV={}: {}", label, dto.studentId(), e.getMessage());
            }
        }
        log.info("[SYNC {}] Xong: nhận {}, lưu {}, lỗi {}, mất {} ms",
                label, data.size(), saved, failed, System.currentTimeMillis() - start);
    }

    private void upsert(SisStudentDto dto) {
        Student s = studentRepository.findById(dto.studentId()).orElseGet(Student::new);
        s.setStudentId(dto.studentId());
        s.setName(dto.fullName());
        s.setMajor(dto.major());
        s.setClassName(dto.className());
        s.setDepartment(dto.department());
        s.setCpa(dto.cpa());
        s.setNationalId(dto.nationalId());
        s.setAdmissionYear(dto.admissionYear());
        s.setLastSyncedAt(LocalDateTime.now());
        s.setEmail(dto.email());
        studentRepository.save(s);
        identityService.registerSis(dto.studentId());
    }
}