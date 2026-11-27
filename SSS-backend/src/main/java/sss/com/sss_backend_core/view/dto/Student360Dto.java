package sss.com.sss_backend_core.view.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Student360Dto {

    // 1. Thông tin gốc từ SIS (Golden Record - đã lược bỏ CCCD nhạy cảm)
    private String studentId;
    private String name;
    private String email;
    private String major;
    private String className;
    private Integer admissionYear;
    private Double cpa;
    private String status;

    // 2. Chỉ số tổng hợp chéo (Derived Metrics cho Decision Support)
    private int totalLmsWarnings;
    private Double averageInternshipScore;
    private String academicRiskLevel; // LOW, MEDIUM, HIGH

    // 3. Dữ liệu chi tiết từ 3 nguồn còn lại
    private AdmissionSummaryDto admission;
    private List<LmsActivitySummaryDto> lmsActivities;
    private List<InternshipSummaryDto> internships;

    public Student360Dto() {
    }

    public Student360Dto(String studentId, String Name, String email, String major,
                         String className, Integer admissionYear, Double cpa, String status,
                         int totalLmsWarnings, Double averageInternshipScore, String academicRiskLevel,
                         AdmissionSummaryDto admission, List<LmsActivitySummaryDto> lmsActivities,
                         List<InternshipSummaryDto> internships) {
        this.studentId = studentId;
        this.name = name;
        this.email = email;
        this.major = major;
        this.className = className;
        this.admissionYear = admissionYear;
        this.cpa = cpa;
        this.status = status;
        this.totalLmsWarnings = totalLmsWarnings;
        this.averageInternshipScore = averageInternshipScore;
        this.academicRiskLevel = academicRiskLevel;
        this.admission = admission;
        this.lmsActivities = lmsActivities;
        this.internships = internships;
    }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public Integer getAdmissionYear() { return admissionYear; }
    public void setAdmissionYear(Integer admissionYear) { this.admissionYear = admissionYear; }

    public Double getCpa() { return cpa; }
    public void setCpa(Double cpa) { this.cpa = cpa; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTotalLmsWarnings() { return totalLmsWarnings; }
    public void setTotalLmsWarnings(int totalLmsWarnings) { this.totalLmsWarnings = totalLmsWarnings; }

    public Double getAverageInternshipScore() { return averageInternshipScore; }
    public void setAverageInternshipScore(Double averageInternshipScore) { this.averageInternshipScore = averageInternshipScore; }

    public String getAcademicRiskLevel() { return academicRiskLevel; }
    public void setAcademicRiskLevel(String academicRiskLevel) { this.academicRiskLevel = academicRiskLevel; }

    public AdmissionSummaryDto getAdmission() { return admission; }
    public void setAdmission(AdmissionSummaryDto admission) { this.admission = admission; }

    public List<LmsActivitySummaryDto> getLmsActivities() { return lmsActivities; }
    public void setLmsActivities(List<LmsActivitySummaryDto> lmsActivities) { this.lmsActivities = lmsActivities; }

    public List<InternshipSummaryDto> getInternships() { return internships; }
    public void setInternships(List<InternshipSummaryDto> internships) { this.internships = internships; }

    // --- Inner DTOs gọn nhẹ cho từng nguồn ---

    public static class AdmissionSummaryDto {
        private Integer admissionYear;
        private String admissionMethod;
        private String subjectCombination;
        private Double admissionScore;
        private String highSchoolName;
        private String priorityArea;

        public AdmissionSummaryDto() {
        }

        public AdmissionSummaryDto(Integer admissionYear, String admissionMethod,
                                   String subjectCombination, Double admissionScore,
                                   String highSchoolName, String priorityArea) {
            this.admissionYear = admissionYear;
            this.admissionMethod = admissionMethod;
            this.subjectCombination = subjectCombination;
            this.admissionScore = admissionScore;
            this.highSchoolName = highSchoolName;
            this.priorityArea = priorityArea;
        }

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
    }

    public static class LmsActivitySummaryDto {
        private String externalEventId;
        private String courseCode;
        private String eventType;
        private String details;
        private LocalDateTime occurredAt;

        public LmsActivitySummaryDto() {
        }

        public LmsActivitySummaryDto(String externalEventId, String courseCode,
                                     String eventType, String details, LocalDateTime occurredAt) {
            this.externalEventId = externalEventId;
            this.courseCode = courseCode;
            this.eventType = eventType;
            this.details = details;
            this.occurredAt = occurredAt;
        }

        public String getExternalEventId() { return externalEventId; }
        public void setExternalEventId(String externalEventId) { this.externalEventId = externalEventId; }

        public String getCourseCode() { return courseCode; }
        public void setCourseCode(String courseCode) { this.courseCode = courseCode; }

        public String getEventType() { return eventType; }
        public void setEventType(String eventType) { this.eventType = eventType; }

        public String getDetails() { return details; }
        public void setDetails(String details) { this.details = details; }

        public LocalDateTime getOccurredAt() { return occurredAt; }
        public void setOccurredAt(LocalDateTime occurredAt) { this.occurredAt = occurredAt; }
    }

    public static class InternshipSummaryDto {
        private String companyName;
        private String positionTitle;
        private String semester;
        private String status;
        private String mentorName;
        private Double evaluationScore;
        private String mentorFeedback;
        private LocalDate startDate;
        private LocalDate endDate;

        public InternshipSummaryDto() {
        }

        public InternshipSummaryDto(String companyName, String positionTitle, String semester,
                                    String status, String mentorName, Double evaluationScore,
                                    String mentorFeedback, LocalDate startDate, LocalDate endDate) {
            this.companyName = companyName;
            this.positionTitle = positionTitle;
            this.semester = semester;
            this.status = status;
            this.mentorName = mentorName;
            this.evaluationScore = evaluationScore;
            this.mentorFeedback = mentorFeedback;
            this.startDate = startDate;
            this.endDate = endDate;
        }

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
    }
}