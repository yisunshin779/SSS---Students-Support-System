package sss.com.sss_backend_core.lms.mock;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sss.com.sss_backend_core.lms.sync.LmsSyncService;

@RestController
@RequestMapping("/dev/lms")
public class LmsDevController {

    private final LmsSyncService lmsSyncService;

    public LmsDevController(LmsSyncService lmsSyncService) {
        this.lmsSyncService = lmsSyncService;
    }

    @PostMapping("/sync")
    public ResponseEntity<String> triggerSync(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime since) {

        if (since != null) {
            lmsSyncService.syncSince("Manual", since);
        } else {
            lmsSyncService.syncIncremental();
        }
        return ResponseEntity.ok("LMS sync completed.");
    }
}