package sss.com.sss_backend_core.profile.service.sis;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SisClient {

    private final RestClient restClient;
    private final int pageSize;

    public SisClient(@Value("${sis.base-url}") String baseUrl,
                     @Value("${sis.api-key}") String apiKey,
                     @Value("${sis.page-size}") int pageSize,
                     @Value("${sis.connect-timeout-ms}") int connectTimeout,
                     @Value("${sis.read-timeout-ms}") int readTimeout) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeout));
        factory.setReadTimeout(Duration.ofMillis(readTimeout));

        this.pageSize = pageSize;
        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeader("X-API-Key", apiKey)
                .build();
    }

    /** Lấy toàn bộ sinh viên có năm nhập học trong khoảng [fromYear, toYear]. */
    public List<SisStudentDto> fetchByAdmissionYear(int fromYear, int toYear) {
        List<SisStudentDto> all = new ArrayList<>();
        int page = 0;
        SisPage result;
        do {
            final int current = page;
            result = restClient.get()
                    .uri(uri -> uri.path("/students")
                            .queryParam("admissionYearFrom", fromYear)
                            .queryParam("admissionYearTo", toYear)
                            .queryParam("page", current)
                            .queryParam("size", pageSize)
                            .build())
                    .retrieve()
                    .body(SisPage.class);

            if (result == null || result.items() == null) break;
            all.addAll(result.items());
            page++;
        } while (result.hasNext());
        return all;
    }
}