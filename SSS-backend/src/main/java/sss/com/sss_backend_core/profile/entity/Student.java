package sss.com.sss_backend_core.profile.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @Column(name = "student_id", length = 10, nullable = false)
    private String studentId;

    @Column(name = "full_name", nullable = false)
    private String name;

    @Column(name = "major")
    private String major;

    @Column(name = "class_name")
    private String className;

    @Column(name = "department")
    private String department;

    @Column(name = "admission_year")
    private Integer admissionYear;

    @Column(name = "last_synced_at")
    private LocalDateTime lastSyncedAt;

    @JsonIgnore
    @Column(name = "cpa")
    private Double cpa;

    @JsonIgnore
    @Column(name = "national_id", length = 12, unique = true)
    private String nationalId;

    @Column(name = "email")
    private String email;

    @Column(name = "status", length = 30)
    private String status; // VD: ACTIVE, WARNING_1, SUSPENDED, GRADUATED

    // JPA bắt buộc phải có constructor không tham số
    public Student() {
    }

    // Constructor đầy đủ (thứ tự trùng thứ tự các trường ở trên)
    public Student(String studentId, String name, String major, String className,
                   String department, Integer admissionYear, LocalDateTime lastSyncedAt,
                   Double cpa, String nationalId, String email, String status) {
        this.studentId = studentId;
        this.name = name;
        this.major = major;
        this.className = className;
        this.department = department;
        this.admissionYear = admissionYear;
        this.lastSyncedAt = lastSyncedAt;
        this.cpa = cpa;
        this.nationalId = nationalId;
        this.email = email;
        this.status = status;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Integer getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(Integer admissionYear) {
        this.admissionYear = admissionYear;
    }

    public LocalDateTime getLastSyncedAt() {
        return lastSyncedAt;
    }

    public void setLastSyncedAt(LocalDateTime lastSyncedAt) {
        this.lastSyncedAt = lastSyncedAt;
    }

    public Double getCpa() {
        return cpa;
    }

    public void setCpa(Double cpa) {
        this.cpa = cpa;
    }

    public String getNationalId() {
        return nationalId;
    }

    public void setNationalId(String nationalId) {
        this.nationalId = nationalId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() { 
        return status; 
    }
    public void setStatus(String status) { 
        this.status = status; 
    }
}