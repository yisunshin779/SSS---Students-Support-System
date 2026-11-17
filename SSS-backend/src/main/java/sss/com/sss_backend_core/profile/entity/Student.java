package sss.com.sss_backend_core.profile.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "students")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}