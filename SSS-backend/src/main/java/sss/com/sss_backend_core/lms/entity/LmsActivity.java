package sss.com.sss_backend_core.lms.entity;

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
    name = "lms_activities",
    indexes = {
        @Index(name = "IX_lms_activities_student_id", columnList = "student_id"),
        @Index(name = "IX_lms_activities_occurred_at", columnList = "occurred_at")
    }
)
public class LmsActivity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "external_event_id", length = 64, nullable = false, unique = true)
    private String externalEventId;

    @Column(name = "lms_user_id", length = 64, nullable = false)
    private String lmsUserId;

    @Column(name = "student_id", length = 10)
    private String studentId;

    @Column(name = "course_code", length = 20, nullable = false)
    private String courseCode;

    @Column(name = "event_type", length = 50, nullable = false)
    private String eventType;

    @Column(name = "details", length = 500)
    private String details;

    @Column(name = "occurred_at", nullable = false)
    private LocalDateTime occurredAt;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    // Constructor mặc định bắt buộc cho JPA
    public LmsActivity() {
    }

    // Constructor đầy đủ tham số (không gồm id tự tăng)
    public LmsActivity(String externalEventId, String lmsUserId, String studentId,
                       String courseCode, String eventType, String details,
                       LocalDateTime occurredAt, LocalDateTime lastSyncedAt) {
        this.externalEventId = externalEventId;
        this.lmsUserId = lmsUserId;
        this.studentId = studentId;
        this.courseCode = courseCode;
        this.eventType = eventType;
        this.details = details;
        this.occurredAt = occurredAt;
        this.lastSyncedAt = lastSyncedAt;
    }

    // Getter & Setter (nếu bạn không dùng @Data của Lombok thì thêm vào đây)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getExternalEventId() { return externalEventId; }
    public void setExternalEventId(String externalEventId) { this.externalEventId = externalEventId; }

    public String getLmsUserId() { return lmsUserId; }
    public void setLmsUserId(String lmsUserId) { this.lmsUserId = lmsUserId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }

    public LocalDateTime getLastSyncedAt() { return lastSyncedAt; }
    public void setLastSyncedAt(LocalDateTime lastSyncedAt) { this.lastSyncedAt = lastSyncedAt; }
}