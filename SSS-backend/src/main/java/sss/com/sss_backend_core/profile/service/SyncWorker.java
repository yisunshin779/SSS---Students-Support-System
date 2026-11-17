package sss.com.sss_backend_core.profile.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SyncWorker {

    // Đồng bộ Sinh viên Năm 1 (Chạy vào 1:00 AM)
    @Scheduled(cron = "0 0 1 * * ?")
    public void syncFreshmen() {
        // Logic gọi API lấy dữ liệu sinh viên năm 1
        System.out.println("Đang kéo dữ liệu sinh viên Năm 1...");
    }

    // Đồng bộ Sinh viên Năm 2 (Chạy vào 2:00 AM)
    @Scheduled(cron = "...")
    public void syncSophomores() {
        // Logic gọi API lấy dữ liệu sinh viên năm 2
    }

    // Đồng bộ Sinh viên Năm 3+ (Chạy vào 3:00 AM)
    @Scheduled(cron = "...")
    public void syncSeniors() {
        // Logic gọi API lấy dữ liệu sinh viên năm 3 trở lên
    }
}