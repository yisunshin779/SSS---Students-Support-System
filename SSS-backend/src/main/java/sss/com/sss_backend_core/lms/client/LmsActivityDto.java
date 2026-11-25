package sss.com.sss_backend_core.lms.client;

import java.time.LocalDateTime;

public class LmsActivityDto {

    private String eventId;
    private String lmsUserId;
    private String studentId;
    private String email;
    private String fullName;
    private String courseCode;
    private String eventType;
    private String details;
    private LocalDateTime occurredAt;

    public LmsActivityDto() {
    }

    public LmsActivityDto(String eventId, String lmsUserId, String studentId, String email,
                          String fullName, String courseCode, String eventType,
                          String details, LocalDateTime occurredAt) {
        this.eventId = eventId;
        this.lmsUserId = lmsUserId;
        this.studentId = studentId;
        this.email = email;
        this.fullName = fullName;
        this.courseCode = courseCode;
        this.eventType = eventType;
        this.details = details;
        this.occurredAt = occurredAt;
    }

    public String getEventId() { return eventId; }
    public void setEventId(String eventId) { this.eventId = eventId; }

    public String getLmsUserId() { return lmsUserId; }
    public void setLmsUserId(String lmsUserId) { this.lmsUserId = lmsUserId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }

    public LocalDateTime getOccurredAt() { return occurredAt; }
    public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
}