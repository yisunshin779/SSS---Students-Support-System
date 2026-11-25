package sss.com.sss_backend_core.lms.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.lms.client.LmsActivityDto;
import sss.com.sss_backend_core.lms.client.LmsPage;

@RestController
@RequestMapping("/mock/lms")
public class MockLmsController {

    @GetMapping("/activities")
    public LmsPage<LmsActivityDto> getActivities(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "200") int size) {

        LocalDateTime now = LocalDateTime.now();

        List<LmsActivityDto> allMockData = List.of(
                // Case 1: Có đủ MSSV và Email -> Khớp thẳng (MATCHED)
                new LmsActivityDto(
                        "LMS-EVT-001",
                        "lms_u_101",
                        "20230001", // Đổi thành 1 MSSV có thật trong bảng students của bạn
                        "a.nv230001@sis.hust.edu.vn",
                        "Nguyễn Văn A",
                        "IT3080",
                        "ABSENCE_WARNING",
                        "Vắng mặt 3 buổi lý thuyết liên tiếp",
                        now.minusHours(2)
                ),
                // Case 2: Không có MSSV, chỉ có Email -> Khớp qua Email (MATCHED)
                new LmsActivityDto(
                        "LMS-EVT-002",
                        "lms_u_102",
                        null,
                        "b.tt230002@sis.hust.edu.vn", // Đổi thành email có thật trong bảng students
                        "Trần Thị B",
                        "IT3170",
                        "ASSIGNMENT_MISSED",
                        "Không nộp bài tập tuần 5",
                        now.minusHours(1)
                ),
                // Case 3: Sinh viên chưa từng đồng bộ từ SIS -> UNMATCHED (student_id sẽ là null)
                new LmsActivityDto(
                        "LMS-EVT-003",
                        "lms_u_999",
                        null,
                        "unknown@sis.hust.edu.vn",
                        "Lê Văn Lạ",
                        "IT4040",
                        "QUIZ_FAILED",
                        "Điểm kiểm tra giữa kỳ 2.5/10",
                        now.minusMinutes(30)
                )
        );

        // Lọc theo mốc thời gian since (nếu có)
        List<LmsActivityDto> filtered = (since == null)
                ? allMockData
                : allMockData.stream().filter(a -> a.getOccurredAt().isAfter(since)).toList();

        // Phân trang đơn giản
        int fromIndex = page * size;
        if (fromIndex >= filtered.size()) {
            return new LmsPage<>(List.of(), page, size, false);
        }
        int toIndex = Math.min(fromIndex + size, filtered.size());
        boolean hasNext = toIndex < filtered.size();

        return new LmsPage<>(filtered.subList(fromIndex, toIndex), page, size, hasNext);
    }
}