package sss.com.sss_backend_core.internship.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.internship.dto.InternshipEvaluationDto;
import sss.com.sss_backend_core.internship.entity.InternshipRecord;
import sss.com.sss_backend_core.internship.repository.InternshipRecordRepository;
import sss.com.sss_backend_core.internship.service.InternshipIngestService;

@RestController
@RequestMapping("/api")
public class InternshipController {

    private final InternshipIngestService ingestService;
    private final InternshipRecordRepository recordRepository;
    private final String expectedApiKey;

    public InternshipController(InternshipIngestService ingestService,
                                InternshipRecordRepository recordRepository,
                                @Value("${internship.ingest-api-key}") String expectedApiKey) {
        this.ingestService = ingestService;
        this.recordRepository = recordRepository;
        this.expectedApiKey = expectedApiKey;
    }

    // Chiều Push: Doanh nghiệp gửi kết quả thực tập vào SSS
    @PostMapping("/enterprise/internships")
    public ResponseEntity<?> ingestInternships(
            @RequestHeader(value = "X-API-Key", required = false) String apiKey,
            @RequestBody List<InternshipEvaluationDto> payload) {

        if (apiKey == null || !apiKey.equals(expectedApiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid or missing X-API-Key"));
        }

        int savedCount = ingestService.ingestBatch(payload);
        return ResponseEntity.ok(Map.of(
                "received", payload.size(),
                "saved", savedCount
        ));
    }

    // Chiều Đọc: Frontend lấy lịch sử thực tập của 1 sinh viên
    @GetMapping("/students/{studentId}/internships")
    public ResponseEntity<List<InternshipRecord>> getByStudentId(@PathVariable String studentId) {
        return ResponseEntity.ok(recordRepository.findByStudentIdOrderByStartDateDesc(studentId));
    }
}