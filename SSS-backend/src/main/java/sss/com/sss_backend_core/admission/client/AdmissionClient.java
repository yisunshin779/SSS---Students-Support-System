package sss.com.sss_backend_core.admission.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AdmissionClient {

    private final RestClient restClient;

    public AdmissionClient(
            @Value("${admission.base-url}") String baseUrl,
            @Value("${admission.api-key}") String apiKey,
            @Value("${admission.connect-timeout-ms:5000}") int connectTimeoutMs,
            @Value("${admission.read-timeout-ms:30000}") int readTimeoutMs) {

        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofMillis(connectTimeoutMs));
        factory.setReadTimeout(Duration.ofMillis(readTimeoutMs));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .defaultHeader("X-API-Key", apiKey)
                .build();
    }

    public AdmissionPage<AdmissionRecordDto> fetchByYear(int year, int page, int size) {
        return restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/records")
                        .queryParam("year", year)
                        .queryParam("page", page)
                        .queryParam("size", size)
                        .build())
                .retrieve()
                .body(new ParameterizedTypeReference<AdmissionPage<AdmissionRecordDto>>() {});
    }
}
