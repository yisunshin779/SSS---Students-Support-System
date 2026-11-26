package sss.com.sss_backend_core.admission.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "admission_records",
    indexes = {
        @Index(name = "IX_admission_student_id", columnList = "student_id"),
        @Index(name = "IX_admission_year", columnList = "admission_year")
    }
)
public class AdmissionRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "national_id", length = 20, nullable = false, unique = true)
    private String nationalId;

    @Column(name = "application_code", length = 30)
    private String applicationCode;

    // Khớp từ SIS qua IdentityResolutionService (null nếu SIS chưa sync khóa này)
    @Column(name = "student_id", length = 10)
    private String studentId;

    @Column(name = "full_name", length = 100, nullable = false)
    private String fullName;

    @Column(name = "admission_year", nullable = false)
    private Integer admissionYear;

    // VD: THPT, DGNL, XTTN
    @Column(name = "admission_method", length = 50)
    private String admissionMethod;

    // VD: A00, A01, D01
    @Column(name = "subject_combination", length = 10)
    private String subjectCombination;

    @Column(name = "admission_score")
    private Double admissionScore;

    @Column(name = "high_school_name", length = 150)
    private String highSchoolName;

    @Column(name = "priority_area", length = 10)
    private String priorityArea;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    public AdmissionRecord() {
    }

    public AdmissionRecord(String nationalId, String applicationCode, String studentId,
                           String fullName, Integer admissionYear, String admissionMethod,
                           String subjectCombination, Double admissionScore,
                           String highSchoolName, String priorityArea, LocalDateTime lastSyncedAt) {
        this.nationalId = nationalId;
        this.applicationCode = applicationCode;
        this.studentId = studentId;
        this.fullName = fullName;
        this.admissionYear = admissionYear;
        this.admissionMethod = admissionMethod;
        this.subjectCombination = subjectCombination;
        this.admissionScore = admissionScore;
        this.highSchoolName = highSchoolName;
        this.priorityArea = priorityArea;
        this.lastSyncedAt = lastSyncedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getApplicationCode() { return applicationCode; }
    public void setApplicationCode(String applicationCode) { this.applicationCode = applicationCode; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public Integer getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }

    public String getAdmissionMethod() { return admissionMethod; }
    public void setAdmissionMethod(String admissionMethod) { this.admissionMethod = admissionMethod; }

    public String getSubjectCombination() { return subjectCombination; }
    public void setSubjectCombination(String subjectCombination) { this.subjectCombination = subjectCombination; }

    public Double getAdmissionScore() { return admissionScore; }
    public void setAdmissionScore(Double admissionScore) { this.admissionScore = admissionScore; }

    public String getHighSchoolName() { return highSchoolName; }
    public void setHighSchoolName(String highSchoolName) { this.highSchoolName = highSchoolName; }

    public String getPriorityArea() { return priorityArea; }
    public void setPriorityArea(String priorityArea) { this.priorityArea = priorityArea; }

    public LocalDateTime getLastSyncedAt() { return lastSyncedAt; }
    public void setLastSyncedAt(LocalDateTime lastSyncedAt) { this.lastSyncedAt = lastSyncedAt; }
}