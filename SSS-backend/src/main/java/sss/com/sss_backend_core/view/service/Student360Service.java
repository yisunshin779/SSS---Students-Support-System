package sss.com.sss_backend_core.view.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sss.com.sss_backend_core.admission.entity.AdmissionRecord;
import sss.com.sss_backend_core.admission.repository.AdmissionRecordRepository;
import sss.com.sss_backend_core.internship.entity.InternshipRecord;
import sss.com.sss_backend_core.internship.repository.InternshipRecordRepository;
import sss.com.sss_backend_core.lms.entity.LmsActivity;
import sss.com.sss_backend_core.lms.repository.LmsActivityRepository;
import sss.com.sss_backend_core.profile.entity.Student;
import sss.com.sss_backend_core.profile.repository.StudentRepository;
import sss.com.sss_backend_core.view.dto.Student360Dto;
import sss.com.sss_backend_core.view.dto.Student360Dto.AdmissionSummaryDto;
import sss.com.sss_backend_core.view.dto.Student360Dto.InternshipSummaryDto;
import sss.com.sss_backend_core.view.dto.Student360Dto.LmsActivitySummaryDto;

@Service
public class Student360Service {

    private final StudentRepository studentRepository;
    private final LmsActivityRepository lmsRepository;
    private final AdmissionRecordRepository admissionRepository;
    private final InternshipRecordRepository internshipRepository;

    public Student360Service(StudentRepository studentRepository,
                             LmsActivityRepository lmsRepository,
                             AdmissionRecordRepository admissionRepository,
                             InternshipRecordRepository internshipRepository) {
        this.studentRepository = studentRepository;
        this.lmsRepository = lmsRepository;
        this.admissionRepository = admissionRepository;
        this.internshipRepository = internshipRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Student360Dto> getStudent360(String studentId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return Optional.empty();
        }

        Student student = studentOpt.get();

        // 1. Lấy dữ liệu tuyển sinh (nếu có)
        AdmissionSummaryDto admissionDto = admissionRepository.findByStudentId(studentId)
                .map(this::toAdmissionSummary)
                .orElse(null);

        // 2. Lấy lịch sử hoạt động LMS
        List<LmsActivity> activities = lmsRepository.findByStudentIdOrderByOccurredAtDesc(studentId);
        List<LmsActivitySummaryDto> lmsDtos = activities.stream()
                .map(this::toLmsSummary)
                .toList();

        // 3. Lấy lịch sử thực tập doanh nghiệp
        List<InternshipRecord> internships = internshipRepository.findByStudentIdOrderByStartDateDesc(studentId);
        List<InternshipSummaryDto> internshipDtos = internships.stream()
                .map(this::toInternshipSummary)
                .toList();

        // 4. Tính toán các chỉ số tổng hợp chéo
        int totalWarnings = activities.size();
        Double avgInternshipScore = calculateAverageInternshipScore(internships);
        String riskLevel = evaluateAcademicRisk(student.getCpa(), totalWarnings);

        Student360Dto result = new Student360Dto(
                student.getStudentId(),
                student.getName(),
                student.getEmail(),
                student.getMajor(),
                student.getClassName(),
                student.getAdmissionYear(),
                student.getCpa(),
                student.getStatus(),
                totalWarnings,
                avgInternshipScore,
                riskLevel,
                admissionDto,
                lmsDtos,
                internshipDtos
        );

        return Optional.of(result);
    }

    private AdmissionSummaryDto toAdmissionSummary(AdmissionRecord record) {
        return new AdmissionSummaryDto(
                record.getAdmissionYear(),
                record.getAdmissionMethod(),
                record.getSubjectCombination(),
                record.getAdmissionScore(),
                record.getHighSchoolName(),
                record.getPriorityArea()
        );
    }

    private LmsActivitySummaryDto toLmsSummary(LmsActivity activity) {
        return new LmsActivitySummaryDto(
                activity.getExternalEventId(),
                activity.getCourseCode(),
                activity.getEventType(),
                activity.getDetails(),
                activity.getOccurredAt()
        );
    }

    private InternshipSummaryDto toInternshipSummary(InternshipRecord record) {
        return new InternshipSummaryDto(
                record.getCompanyName(),
                record.getPositionTitle(),
                record.getSemester(),
                record.getStatus(),
                record.getMentorName(),
                record.getEvaluationScore(),
                record.getMentorFeedback(),
                record.getStartDate(),
                record.getEndDate()
        );
    }

    private Double calculateAverageInternshipScore(List<InternshipRecord> internships) {
        List<Double> scores = internships.stream()
                .map(InternshipRecord::getEvaluationScore)
                .filter(score -> score != null)
                .toList();
        if (scores.isEmpty()) {
            return null;
        }
        double sum = 0.0;
        for (Double s : scores) {
            sum += s;
        }
        return Math.round((sum / scores.size()) * 100.0) / 100.0;
    }

    private String evaluateAcademicRisk(Double cpa, int lmsWarnings) {
        double safeCpa = (cpa != null) ? cpa : 0.0;
        if (safeCpa < 2.0 || lmsWarnings >= 3) {
            return "HIGH";
        }
        if (safeCpa < 2.5 || lmsWarnings >= 1) {
            return "MEDIUM";
        }
        return "LOW";
    }
}