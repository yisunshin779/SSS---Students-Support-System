package sss.com.sss_backend_core.lms.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class LmsSyncWorker {

    private static final Logger log = LoggerFactory.getLogger(LmsSyncWorker.class);
    private final LmsSyncService lmsSyncService;

    public LmsSyncWorker(LmsSyncService lmsSyncService) {
        this.lmsSyncService = lmsSyncService;
    }

    // Đồng bộ hoạt động LMS mỗi 30 phút
    @Scheduled(cron = "0 */30 * * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncLmsActivities() {
        run("Incremental", lmsSyncService::syncIncremental);
    }

    private void run(String label, Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            log.error("[SYNC LMS {}] Thất bại toàn bộ: {}", label, e.getMessage(), e);
        }
    }
}