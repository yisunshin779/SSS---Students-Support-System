package sss.com.sss_backend_core.profile.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sss.com.sss_backend_core.profile.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {
    
    // Tìm kiếm sinh viên theo định danh duy nhất (MSSV)
    Optional<Student> findByStudentId(String studentId);

    // Lọc danh sách sinh viên theo Viện/Khoa quản lý
    List<Student> findByDepartment(String department);
    
    // Lọc danh sách sinh viên theo Lớp hành chính
    List<Student> findByClassName(String className);
}