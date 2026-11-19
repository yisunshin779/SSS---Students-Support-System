package sss.com.sss_backend_core.profile.mock;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import sss.com.sss_backend_core.profile.entity.Student;
import sss.com.sss_backend_core.profile.repository.StudentRepository;
import sss.com.sss_backend_core.profile.sync.StudentSyncService;

@Profile("mock-sis")
@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {

    private final StudentSyncService syncService;
    private final StudentRepository studentRepository;

    @PostMapping("/sync/{group}")
    public String sync(@PathVariable("group") String group) {
        switch (group) {
            case "freshmen" -> syncService.syncFreshmen();
            case "sophomores" -> syncService.syncSophomores();
            case "seniors" -> syncService.syncSeniors();
            default -> { return "Nhóm không hợp lệ: freshmen | sophomores | seniors"; }
        }
        return "Đã chạy xong nhóm " + group + ", xem log và /dev/students/summary";
    }

    @GetMapping("/students/summary")
    public Map<String, Object> summary() {
        List<Student> all = studentRepository.findAll();
        Map<String, Long> byYear = all.stream().collect(Collectors.groupingBy(
                s -> String.valueOf(s.getAdmissionYear()), TreeMap::new, Collectors.counting()));
        Map<String, Object> out = new LinkedHashMap<>();
        out.put("total", all.size());
        out.put("byAdmissionYear", byYear);
        out.put("cpaNull", all.stream().filter(s -> s.getCpa() == null).count());
        out.put("neverSynced", all.stream().filter(s -> s.getLastSyncedAt() == null).count());
        return out;
    }
}