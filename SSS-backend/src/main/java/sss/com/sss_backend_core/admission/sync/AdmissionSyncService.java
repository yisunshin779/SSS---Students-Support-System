package sss.com.sss_backend_core.admission.sync;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sss.com.sss_backend_core.admission.client.AdmissionClient;
import sss.com.sss_backend_core.admission.client.AdmissionPage;
import sss.com.sss_backend_core.admission.client.AdmissionRecordDto;
import sss.com.sss_backend_core.admission.entity.AdmissionRecord;
import sss.com.sss_backend_core.admission.repository.AdmissionRecordRepository;
import sss.com.sss_backend_core.profile.entity.StudentXref;
import sss.com.sss_backend_core.profile.identity.IdentityResolutionService;
import sss.com.sss_backend_core.profile.identity.SourceIdentity;

@Service
public class AdmissionSyncService {

    private static final Logger log = LoggerFactory.getLogger(AdmissionSyncService.class);
    private static final int PAGE_SIZE = 500;

    private final AdmissionClient admissionClient;
    private final AdmissionRecordRepository recordRepository;
    private final IdentityResolutionService identityService;

    public AdmissionSyncService(AdmissionClient admissionClient,
                                AdmissionRecordRepository recordRepository,
                                IdentityResolutionService identityService) {
        this.admissionClient = admissionClient;
        this.recordRepository = recordRepository;
        this.identityService = identityService;
    }

    public void syncByYear(int year) {
        long start = System.currentTimeMillis();
        log.info("[SYNC ADMISSION] Bắt đầu kéo dữ liệu tuyển sinh năm {}", year);

        int page = 0;
        int totalReceived = 0, saved = 0, failed = 0;

        while (true) {
            AdmissionPage<AdmissionRecordDto> response = admissionClient.fetchByYear(year, page, PAGE_SIZE);
            if (response == null || response.getItems() == null || response.getItems().isEmpty()) {
                break;
            }

            totalReceived += response.getItems().size();
            for (AdmissionRecordDto dto : response.getItems()) {
                try {
                    upsert(dto);
                    saved++;
                } catch (Exception e) {
                    failed++;
                    // Không log nationalId vì là dữ liệu nhạy cảm
                    log.warn("[SYNC ADMISSION] Lỗi bản ghi hồ sơ={}: {}", dto.getApplicationCode(), e.getMessage());
                }
            }

            if (!response.isHasNext()) {
                break;
            }
            page++;
        }

        log.info("[SYNC ADMISSION] Năm {} xong: nhận {}, lưu {}, lỗi {}, mất {} ms",
                year, totalReceived, saved, failed, System.currentTimeMillis() - start);
    }

    private void upsert(AdmissionRecordDto dto) {
        // Đối soát qua CCCD (nationalId) -> trả về studentId của SIS
        String externalKey = dto.getApplicationCode() != null ? dto.getApplicationCode() : dto.getNationalId();
        SourceIdentity source = new SourceIdentity(
                "ADMISSION",
                externalKey,
                null,                // Chưa có MSSV lúc tuyển sinh
                dto.getNationalId(), // Khóa đối soát chính
                dto.getFullName(),
                null                 // Chưa có email trường
        );
        StudentXref xref = identityService.resolve(source);

        AdmissionRecord record = recordRepository.findByNationalId(dto.getNationalId())
                .orElseGet(AdmissionRecord::new);

        record.setNationalId(dto.getNationalId());
        record.setApplicationCode(dto.getApplicationCode());
        record.setStudentId(xref.getStudentId());
        record.setFullName(dto.getFullName());
        record.setAdmissionYear(dto.getAdmissionYear());
        record.setAdmissionMethod(dto.getAdmissionMethod());
        record.setSubjectCombination(dto.getSubjectCombination());
        record.setAdmissionScore(dto.getAdmissionScore());
        record.setHighSchoolName(dto.getHighSchoolName());
        record.setPriorityArea(dto.getPriorityArea());
        record.setLastSyncedAt(LocalDateTime.now());

        recordRepository.save(record);
    }
}