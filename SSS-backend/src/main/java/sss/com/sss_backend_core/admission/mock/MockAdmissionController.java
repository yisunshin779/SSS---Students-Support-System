package sss.com.sss_backend_core.admission.mock;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.admission.client.AdmissionPage;
import sss.com.sss_backend_core.admission.client.AdmissionRecordDto;

@RestController
@RequestMapping("/mock/admission")
public class MockAdmissionController {

    @GetMapping("/records")
    public AdmissionPage<AdmissionRecordDto> getRecords(
            @RequestParam int year,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "500") int size) {

        List<AdmissionRecordDto> mockData = List.of(
                new AdmissionRecordDto(
                        "001205001234", // Đổi thành 1 số CCCD có thật trong bảng students để test MATCHED
                        "TS2026-0001",
                        "Nguyễn Văn A",
                        year,
                        "THPT",
                        "A00",
                        28.15,
                        "THPT Chuyên Hà Nội - Amsterdam",
                        "KV3"
                ),
                new AdmissionRecordDto(
                        "001205009999", // CCCD chưa có trong SIS -> UNMATCHED
                        "TS2026-0002",
                        "Phạm Thị C",
                        year,
                        "DGNL",
                        "K00",
                        88.5,
                        "THPT Chu Văn An",
                        "KV2-NT"
                )
        );

        int fromIndex = page * size;
        if (fromIndex >= mockData.size()) {
            return new AdmissionPage<>(List.of(), page, size, false);
        }
        int toIndex = Math.min(fromIndex + size, mockData.size());
        return new AdmissionPage<>(mockData.subList(fromIndex, toIndex), page, size, toIndex < mockData.size());
    }
}