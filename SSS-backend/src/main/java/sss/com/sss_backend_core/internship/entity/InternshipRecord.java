package sss.com.sss_backend_core.internship.entity;

import java.time.LocalDate;
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
    name = "internship_records",
    indexes = {
        @Index(name = "IX_internship_student_id", columnList = "student_id"),
        @Index(name = "IX_internship_semester", columnList = "semester")
    }
)
public class InternshipRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Khóa chống trùng lặp từ hệ thống đối tác (VD: "FPT-20261-20230001")
    @Column(name = "external_placement_id", length = 80, nullable = false, unique = true)
    private String externalPlacementId;

    // MSSV đã đối soát qua student_xref (null nếu UNMATCHED)
    @Column(name = "student_id", length = 10)
    private String studentId;

    @Column(name = "company_name", length = 150, nullable = false)
    private String companyName;

    @Column(name = "position_title", length = 100, nullable = false)
    private String positionTitle;

    // VD: "2026.1", "2026.2"
    @Column(name = "semester", length = 20, nullable = false)
    private String semester;

    // VD: ONGOING, COMPLETED, TERMINATED
    @Column(name = "status", length = 30, nullable = false)
    private String status;

    @Column(name = "mentor_name", length = 100)
    private String mentorName;

    @Column(name = "evaluation_score")
    private Double evaluationScore;

    @Column(name = "mentor_feedback", length = 1000)
    private String mentorFeedback;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    public InternshipRecord() {
    }

    public InternshipRecord(String externalPlacementId, String studentId, String companyName,
                            String positionTitle, String semester, String status,
                            String mentorName, Double evaluationScore, String mentorFeedback,
                            LocalDate startDate, LocalDate endDate, LocalDateTime lastSyncedAt) {
        this.externalPlacementId = externalPlacementId;
        this.studentId = studentId;
        this.companyName = companyName;
        this.positionTitle = positionTitle;
        this.semester = semester;
        this.status = status;
        this.mentorName = mentorName;
        this.evaluationScore = evaluationScore;
        this.mentorFeedback = mentorFeedback;
        this.startDate = startDate;
        this.endDate = endDate;
        this.lastSyncedAt = lastSyncedAt;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalPlacementId() { return externalPlacementId; }
    public void setExternalPlacementId(String externalPlacementId) { this.externalPlacementId = externalPlacementId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public String getPositionTitle() { return positionTitle; }
    public void setPositionTitle(String positionTitle) { this.positionTitle = positionTitle; }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getMentorName() { return mentorName; }
    public void setMentorName(String mentorName) { this.mentorName = mentorName; }

    public Double getEvaluationScore() { return evaluationScore; }
    public void setEvaluationScore(Double evaluationScore) { this.evaluationScore = evaluationScore; }

    public String getMentorFeedback() { return mentorFeedback; }
    public void setMentorFeedback(String mentorFeedback) { this.mentorFeedback = mentorFeedback; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDateTime getLastSyncedAt() { return lastSyncedAt; }
    public void setLastSyncedAt(LocalDateTime lastSyncedAt) { this.lastSyncedAt = lastSyncedAt; }
}