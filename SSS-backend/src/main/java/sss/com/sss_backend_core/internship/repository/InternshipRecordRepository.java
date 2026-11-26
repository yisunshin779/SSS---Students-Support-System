package sss.com.sss_backend_core.internship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sss.com.sss_backend_core.internship.entity.InternshipRecord;

public interface InternshipRecordRepository extends JpaRepository<InternshipRecord, Long> {

    Optional<InternshipRecord> findByExternalPlacementId(String externalPlacementId);

    List<InternshipRecord> findByStudentIdOrderByStartDateDesc(String studentId);
}