package sss.com.sss_backend_core.profile.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "student_xref",
       uniqueConstraints = @UniqueConstraint(columnNames = {"source_system", "source_id"}))
public class StudentXref {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_system", length = 20, nullable = false)
    private String sourceSystem;          // "SIS", "LMS", "ADMISSION"...

    @Column(name = "source_id", length = 64, nullable = false)
    private String sourceId;              // ID ở hệ thống nguồn

    @Column(name = "student_id", length = 10)
    private String studentId;             // MSSV trong bảng students (null nếu chưa khớp)

    @Column(name = "match_rule", length = 20)
    private String matchRule;             // MSSV | NATIONAL_ID | NAME | CONFLICT | MANUAL

    private Double confidence;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private MatchStatus status;

    @Column(length = 255)
    private String note;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // JPA bắt buộc phải có constructor không tham số
    public StudentXref() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(String matchRule) {
        this.matchRule = matchRule;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    public MatchStatus getStatus() {
        return status;
    }

    public void setStatus(MatchStatus status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}