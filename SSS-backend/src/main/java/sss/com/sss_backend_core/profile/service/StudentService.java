package sss.com.sss_backend_core.profile.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sss.com.sss_backend_core.profile.entity.Student;
import sss.com.sss_backend_core.profile.repository.StudentRepository;

@Service
@RequiredArgsConstructor
public class StudentService {

    // Dependency Injection: Tự động nối Database vào tầng Service
    private final StudentRepository studentRepository;

    // Logic rút trích Hồ sơ Vàng định danh sinh viên
    public Student getStudentIdentity(String studentId) {
        return studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy dữ liệu định danh cho MSSV: " + studentId));
    }
}