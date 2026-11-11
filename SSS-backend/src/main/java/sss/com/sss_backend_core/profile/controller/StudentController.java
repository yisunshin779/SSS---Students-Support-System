package sss.com.sss_backend_core.profile.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import sss.com.sss_backend_core.profile.service.StudentService;
import sss.com.sss_backend_core.profile.entity.Student;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudentProfile(@PathVariable String studentId) {
        try {
            Student student = studentService.getStudentIdentity(studentId);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}