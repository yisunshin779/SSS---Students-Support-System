package sss.com.sss_backend_core.admission.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.admission.entity.AdmissionRecord;
import sss.com.sss_backend_core.admission.repository.AdmissionRecordRepository;
import sss.com.sss_backend_core.admission.sync.AdmissionSyncService;

@RestController
@RequestMapping("/api")
public class AdmissionController {

    private final AdmissionSyncService syncService;
    private final AdmissionRecordRepository recordRepository;

    public AdmissionController(AdmissionSyncService syncService,
                               AdmissionRecordRepository recordRepository) {
        this.syncService = syncService;
        this.recordRepository = recordRepository;
    }

    // Kích hoạt đồng bộ tuyển sinh theo năm (thay cho Cron)
    @PostMapping("/admin/admission/sync")
    public ResponseEntity<String> syncByYear(@RequestParam int year) {
        syncService.syncByYear(year);
        return ResponseEntity.ok("Admission sync completed for year " + year);
    }

    // Frontend lấy thông tin đầu vào của sinh viên cho màn hình Hồ sơ 360°
    @GetMapping("/students/{studentId}/admission")
    public ResponseEntity<AdmissionRecord> getByStudentId(@PathVariable String studentId) {
        return recordRepository.findByStudentId(studentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}