package sss.com.sss_backend_core.internship.dto;

import java.time.LocalDate;

public class InternshipEvaluationDto {

    private String placementId;
    private String studentId;
    private String email;
    private String fullName;
    private String companyName;
    private String positionTitle;
    private String semester;
    private String status;
    private String mentorName;
    private Double evaluationScore;
    private String mentorFeedback;
    private LocalDate startDate;
    private LocalDate endDate;

    public InternshipEvaluationDto() {
    }

    public InternshipEvaluationDto(String placementId, String studentId, String email, String fullName,
                                   String companyName, String positionTitle, String semester, String status,
                                   String mentorName, Double evaluationScore, String mentorFeedback,
                                   LocalDate startDate, LocalDate endDate) {
        this.placementId = placementId;
        this.studentId = studentId;
        this.email = email;
        this.fullName = fullName;
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

    public String getPlacementId() { return placementId; }
    public void setPlacementId(String placementId) { this.placementId = placementId; }

    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

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