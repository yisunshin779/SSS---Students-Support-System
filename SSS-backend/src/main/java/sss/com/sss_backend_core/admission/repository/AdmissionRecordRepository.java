package sss.com.sss_backend_core.admission.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sss.com.sss_backend_core.admission.entity.AdmissionRecord;

public interface AdmissionRecordRepository extends JpaRepository<AdmissionRecord, Long> {

    Optional<AdmissionRecord> findByNationalId(String nationalId);

    Optional<AdmissionRecord> findByStudentId(String studentId);

    List<AdmissionRecord> findByAdmissionYear(Integer admissionYear);
}