package sss.com.sss_backend_core.lms.client;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class LmsClient {

    private final RestClient restClient;

    public LmsClient(
            @Value("${lms.base-url}") String baseUrl,
            @Value("${lms.api-key}") String apiKey,
            @Value("${lms.connect-timeout-ms:5000}") int connectTimeoutMs,
            @Value("${lms.read-timeout-ms:30000}") int readTimeoutMs) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        factory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeader("X-API-Key", apiKey)
                .build();
    }

    public LmsPage<LmsActivityDto> fetchActivities(LocalDateTime since, int page, int size) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/activities")
                        .queryParam("since", since.toString())
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<LmsPage<LmsActivityDto>>() {});
    }
}