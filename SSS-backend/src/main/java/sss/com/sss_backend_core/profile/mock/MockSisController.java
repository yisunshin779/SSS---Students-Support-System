package sss.com.sss_backend_core.profile.mock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import sss.com.sss_backend_core.profile.service.sis.SisStudentDto;

@Profile("mock-sis")
@RestController
@RequestMapping("/mock-sis")
public class MockSisController {

    private static final String API_KEY = "mock-key";
    private static final String[] HO = {"Nguyễn", "Trần", "Lê", "Phạm", "Hoàng", "Vũ", "Đào"};
    private static final String[] DEM = {"Văn", "Quang", "Minh", "Quốc", "Hoàng"};
    private static final String[] TEN = {"An", "Anh", "Bình", "Cường", "Châu", "Dũng", "Duy", "Dương", "Hiếu", "Hoàng", "Hưng", "Huy", "Khang", "Khánh", "Khoa", "Kiên",
            "Lâm", "Mạnh", "Minh", "Nam", "Phúc", "Quân", "Sơn", "Sỹ", "Thành", "Triết", "Tuấn", "Tiến", "Tùng", "Vinh"};
    private static final String[] MAJORS = {"Khoa học máy tính", "Kỹ thuật máy tính", "Kỹ thuật Điện tử - Viễn thông", "Kỹ thuật điều khiển và tự động hóa"};
    private static final String[] DEPTS = {"SoICT", "SEEE"};

    @GetMapping("/students")
    public Map<String, Object> students(
            @RequestHeader(value = "X-API-Key", required = false) String key,
            @RequestParam("admissionYearFrom") int from,
            @RequestParam("admissionYearTo") int to,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        if (!API_KEY.equals(key)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "API key không hợp lệ");
        }

        List<SisStudentDto> filtered = buildAll().stream()
                .filter(s -> s.admissionYear() >= from && s.admissionYear() <= to)
                .sorted(Comparator.comparing(SisStudentDto::studentId))
                .toList();

        int totalPages = (int) Math.ceil(filtered.size() / (double) size);
        int start = Math.min(page * size, filtered.size());
        int end = Math.min(start + size, filtered.size());

        return Map.of("items", filtered.subList(start, end), "page", page, "totalPages", totalPages);
    }

    private int academicYearStart() {
        LocalDate t = LocalDate.now();
        return t.getMonthValue() >= 9 ? t.getYear() : t.getYear() - 1;
    }

    private String nameOf(int n) {
        return HO[n % 6] + " " + DEM[(n / 6) % 5] + " " + TEN[n % 15];
    }

    private String nationalIdOf(int year, int seq) {
        return String.format("%012d", 100_000_000_000L + year * 100L + seq);
    }

    /** 6 khóa x 5 sinh viên = 30 bản ghi, kèm 5 ca lỗi cố ý. */
    private List<SisStudentDto> buildAll() {
        int base = academicYearStart();
        List<SisStudentDto> list = new ArrayList<>();
        int n = 0;
        for (int year = base - 5; year <= base; year++) {
            for (int seq = 1; seq <= 5; seq++, n++) {
                String id = String.format("%d%06d", year, seq);
                String name = nameOf(n);
                Double cpa = Math.round((2.0 + seq * 0.45) * 100) / 100.0;
                String nid = nationalIdOf(year, seq);

                if (year == base && seq == 5)     cpa = null;                  // CPA null (hợp lệ)
                if (year == base - 1 && seq == 5) nid = nationalIdOf(year, 1); // trùng CCCD -> lỗi lưu
                if (year == base - 2 && seq == 5) name = null;                 // thiếu họ tên -> lỗi lưu
                if (year == base - 3 && seq == 5) id = id + "1";               // MSSV 11 ký tự -> lỗi lưu
                if (year == base - 4 && seq == 4) name = nameOf(n - 1);        // trùng tên (dùng ở Phần 2)

                list.add(new SisStudentDto(id, name, MAJORS[n % 3], "CNTT-" + (year % 100),
                        DEPTS[n % 2], cpa, nid, year));
            }
        }
        return list;
    }
}