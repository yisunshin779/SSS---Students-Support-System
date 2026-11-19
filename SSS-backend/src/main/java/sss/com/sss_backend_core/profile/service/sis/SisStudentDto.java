package sss.com.sss_backend_core.profile.service.sis;

import java.util.List;

public record SisStudentDto(
        String studentId,      
        String fullName,
        String major,
        String className,
        String department,
        Double cpa,
        String nationalId,
        Integer admissionYear
) {}

record SisPage(List<SisStudentDto> items, int page, int totalPages) {
    boolean hasNext() { return page + 1 < totalPages; }
}