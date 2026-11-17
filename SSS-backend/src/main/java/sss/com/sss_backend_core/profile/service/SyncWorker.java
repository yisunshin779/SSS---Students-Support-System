package sss.com.sss_backend_core.profile.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncWorker {

    private static final Logger log = LoggerFactory.getLogger(SyncWorker.class);
    private final StudentSyncService syncService;

    public SyncWorker(StudentSyncService syncService) {
        this.syncService = syncService;
    }

    // Sinh viên Năm 1 - 1:00 AM
    @Scheduled(cron = "0 0 1 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncFreshmen() {
        run("Năm 1", syncService::syncFreshmen);
    }

    // Sinh viên Năm 2 - 2:00 AM
    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncSophomores() {
        run("Năm 2", syncService::syncSophomores);
    }

    // Sinh viên Năm 3+ - 3:00 AM
    @Scheduled(cron = "0 0 3 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void syncSeniors() {
        run("Năm 3+", syncService::syncSeniors);
    }

    // Một nhóm lỗi không được làm hỏng các nhóm còn lại
    private void run(String label, Runnable task) {
        try {
            task.run();
        } catch (Exception e) {
            log.error("[SYNC {}] Thất bại toàn bộ: {}", label, e.getMessage(), e);
        }
    }
}