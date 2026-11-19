package sss.com.sss_backend_core.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import sss.com.sss_backend_core.profile.entity.MatchStatus;
import sss.com.sss_backend_core.profile.entity.StudentXref;

public interface StudentXrefRepository extends JpaRepository<StudentXref, Long> {
    Optional<StudentXref> findBySourceSystemAndSourceId(String sourceSystem, String sourceId);
    List<StudentXref> findByStatusNot(MatchStatus status);
}