package sss.com.sss_backend_core.view.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.view.dto.Student360Dto;
import sss.com.sss_backend_core.view.service.Student360Service;

@RestController
@RequestMapping("/api/students")
public class Student360Controller {

    private final Student360Service student360Service;

    public Student360Controller(Student360Service student360Service) {
        this.student360Service = student360Service;
    }

    @GetMapping("/{studentId}/360")
    public ResponseEntity<Student360Dto> getStudent360(@PathVariable String studentId) {
        return student360Service.getStudent360(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}