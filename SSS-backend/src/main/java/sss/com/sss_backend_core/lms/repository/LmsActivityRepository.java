package sss.com.sss_backend_core.lms.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import sss.com.sss_backend_core.lms.entity.LmsActivity;

public interface LmsActivityRepository extends JpaRepository<LmsActivity, Long> {

    Optional<LmsActivity> findByExternalEventId(String externalEventId);

    @Query("SELECT MAX(a.occurredAt) FROM LmsActivity a")
    Optional<LocalDateTime> findLatestOccurredAt();

    List<LmsActivity> findByStudentIdOrderByOccurredAtDesc(String studentId);
}