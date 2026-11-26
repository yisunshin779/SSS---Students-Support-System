package sss.com.sss_backend_core.internship.service;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sss.com.sss_backend_core.internship.dto.InternshipEvaluationDto;
import sss.com.sss_backend_core.internship.entity.InternshipRecord;
import sss.com.sss_backend_core.internship.repository.InternshipRecordRepository;
import sss.com.sss_backend_core.profile.entity.StudentXref;
import sss.com.sss_backend_core.profile.identity.IdentityResolutionService;
import sss.com.sss_backend_core.profile.identity.SourceIdentity;

@Service
public class InternshipIngestService {

    private static final Logger log = LoggerFactory.getLogger(InternshipIngestService.class);

    private final InternshipRecordRepository recordRepository;
    private final IdentityResolutionService identityService;

    public InternshipIngestService(InternshipRecordRepository recordRepository,
                                   IdentityResolutionService identityService) {
        this.recordRepository = recordRepository;
        this.identityService = identityService;
    }

    public int ingestBatch(List<InternshipEvaluationDto> items) {
        int saved = 0;
        for (InternshipEvaluationDto dto : items) {
            try {
                upsert(dto);
                saved++;
            } catch (Exception e) {
                log.warn("[INGEST INTERNSHIP] Lỗi bản ghi placementId={}: {}", dto.getPlacementId(), e.getMessage());
            }
        }
        log.info("[INGEST INTERNSHIP] Đã xử lý {}/{} bản ghi thực tập", saved, items.size());
        return saved;
    }

    private void upsert(InternshipEvaluationDto dto) {
        // Đối soát định danh (ADR 01): doanh nghiệp thường gửi MSSV hoặc email
        SourceIdentity source = new SourceIdentity(
                "ENTERPRISE",
                dto.getPlacementId(),
                dto.getStudentId(),
                null, // Doanh nghiệp thường không lưu CCCD
                dto.getFullName(),
                dto.getEmail()
        );
        StudentXref xref = identityService.resolve(source);

        InternshipRecord record = recordRepository.findByExternalPlacementId(dto.getPlacementId())
                .orElseGet(InternshipRecord::new);

        record.setExternalPlacementId(dto.getPlacementId());
        record.setStudentId(xref.getStudentId());
        record.setCompanyName(dto.getCompanyName());
        record.setPositionTitle(dto.getPositionTitle());
        record.setSemester(dto.getSemester());
        record.setStatus(dto.getStatus());
        record.setMentorName(dto.getMentorName());
        record.setEvaluationScore(dto.getEvaluationScore());
        record.setMentorFeedback(dto.getMentorFeedback());
        record.setStartDate(dto.getStartDate());
        record.setEndDate(dto.getEndDate());
        record.setLastSyncedAt(LocalDateTime.now());

        recordRepository.save(record);
    }
}