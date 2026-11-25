package sss.com.sss_backend_core.lms.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.lms.entity.LmsActivity;
import sss.com.sss_backend_core.lms.repository.LmsActivityRepository;

@RestController
@RequestMapping("/api")
public class LmsActivityController {

    private final LmsActivityRepository activityRepository;

    public LmsActivityController(LmsActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    // Lấy lịch sử hoạt động LMS của 1 sinh viên cụ thể (sắp xếp mới nhất trước)
    @GetMapping("/students/{studentId}/lms-activities")
    public ResponseEntity<List<LmsActivity>> getByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(activityRepository.findByStudentIdOrderByOccurredAtDesc(studentId));
    }

    // Lấy toàn bộ hoạt động LMS (tiện kiểm tra nhanh trên Postman/Frontend)
    @GetMapping("/lms/activities")
    public ResponseEntity<List<LmsActivity>> getAllActivities() {
        return ResponseEntity.ok(activityRepository.findAll());
    }
}