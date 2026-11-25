package sss.com.sss_backend_core.lms.sync;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sss.com.sss_backend_core.lms.client.LmsActivityDto;
import sss.com.sss_backend_core.lms.client.LmsClient;
import sss.com.sss_backend_core.lms.client.LmsPage;
import sss.com.sss_backend_core.lms.entity.LmsActivity;
import sss.com.sss_backend_core.lms.repository.LmsActivityRepository;
import sss.com.sss_backend_core.profile.entity.StudentXref;
import sss.com.sss_backend_core.profile.identity.IdentityResolutionService;
import sss.com.sss_backend_core.profile.identity.SourceIdentity;

@Service
public class LmsSyncService {

    private static final Logger log = LoggerFactory.getLogger(LmsSyncService.class);
    private static final int PAGE_SIZE = 200;

    private final LmsClient lmsClient;
    private final LmsActivityRepository activityRepository;
    private final IdentityResolutionService identityService;

    public LmsSyncService(LmsClient lmsClient,
                          LmsActivityRepository activityRepository,
                          IdentityResolutionService identityService) {
        this.lmsClient = lmsClient;
        this.activityRepository = activityRepository;
        this.identityService = identityService;
    }

    /**
     * Đồng bộ tăng dần từ mốc thời gian gần nhất đã lưu trong DB.
     * Nếu DB chưa có dữ liệu, mặc định kéo dữ liệu trong 24 giờ gần nhất.
     */
    public void syncIncremental() {
        LocalDateTime since = activityRepository.findLatestOccurredAt()
                .orElseGet(() -> LocalDateTime.now().minusHours(24));
        syncSince("Incremental", since);
    }

    /**
     * Dùng cho DevController / Thunder Client khi muốn ép đồng bộ từ một mốc bất kỳ.
     */
    public void syncSince(String label, LocalDateTime since) {
        long start = System.currentTimeMillis();
        log.info("[SYNC LMS {}] Bắt đầu kéo dữ liệu từ mốc {}", label, since);

        int page = 0;
        int totalReceived = 0, saved = 0, failed = 0;

        while (true) {
            LmsPage<LmsActivityDto> lmsPage = lmsClient.fetchActivities(since, page, PAGE_SIZE);
            if (lmsPage == null || lmsPage.items() == null || lmsPage.items().isEmpty()) {
                break;
            }

            totalReceived += lmsPage.items().size();
            for (LmsActivityDto dto : lmsPage.items()) {
                try {
                    upsert(dto);
                    saved++;
                } catch (Exception e) {
                    failed++;
                    log.warn("[SYNC LMS {}] Lỗi bản ghi eventId={}: {}", label, dto.getEventId(), e.getMessage());
                }
            }

            if (!lmsPage.hasNext()) {
                break;
            }
            page++;
        }

        log.info("[SYNC LMS {}] Xong: nhận {}, lưu {}, lỗi {}, mất {} ms",
                label, totalReceived, saved, failed, System.currentTimeMillis() - start);
    }

    private void upsert(LmsActivityDto dto) {
        // 1. Đối soát định danh (ADR 01)
        SourceIdentity source = new SourceIdentity(
            "LMS",
            dto.getLmsUserId(),
            dto.getStudentId(), // MSSV nếu LMS có gửi kèm (có thể null)
            null,               // LMS không có CCCD (nationalId)
            dto.getFullName(),
            dto.getEmail()
        );
        StudentXref xref = identityService.resolve(source);

        // 2. Chống trùng lặp (Idempotency)
        LmsActivity activity = activityRepository.findByExternalEventId(dto.getEventId())
            .orElseGet(LmsActivity::new);

        activity.setExternalEventId(dto.getEventId());
        activity.setLmsUserId(dto.getLmsUserId());
        activity.setStudentId(xref.getStudentId());
        activity.setCourseCode(dto.getCourseCode());
        activity.setEventType(dto.getEventType());
        activity.setDetails(dto.getDetails());
        activity.setOccurredAt(dto.getOccurredAt());
        activity.setLastSyncedAt(LocalDateTime.now());

        activityRepository.save(activity);
    }
}