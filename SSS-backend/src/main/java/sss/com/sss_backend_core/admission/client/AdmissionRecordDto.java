package sss.com.sss_backend_core.admission.client;

public class AdmissionRecordDto {

    private String nationalId;
    private String applicationCode;
    private String fullName;
    private Integer admissionYear;
    private String admissionMethod;
    private String subjectCombination;
    private Double admissionScore;
    private String highSchoolName;
    private String priorityArea;

    public AdmissionRecordDto() {
    }

    public AdmissionRecordDto(String nationalId, String applicationCode, String fullName,
                              Integer admissionYear, String admissionMethod, String subjectCombination,
                              Double admissionScore, String highSchoolName, String priorityArea) {
        this.nationalId = nationalId;
        this.applicationCode = applicationCode;
        this.fullName = fullName;
        this.admissionYear = admissionYear;
        this.admissionMethod = admissionMethod;
        this.subjectCombination = subjectCombination;
        this.admissionScore = admissionScore;
        this.highSchoolName = highSchoolName;
        this.priorityArea = priorityArea;
    }

    public String getNationalId() { return nationalId; }
    public void setNationalId(String nationalId) { this.nationalId = nationalId; }

    public String getApplicationCode() { return applicationCode; }
    public void setApplicationCode(String applicationCode) { this.applicationCode = applicationCode; }

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
}