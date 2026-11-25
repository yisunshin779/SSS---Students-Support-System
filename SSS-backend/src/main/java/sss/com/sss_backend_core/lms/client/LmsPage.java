package sss.com.sss_backend_core.lms.client;

import java.util.List;

public record LmsPage<T>(
        List<T> items,
        int page,
        int size,
        boolean hasNext
) {}