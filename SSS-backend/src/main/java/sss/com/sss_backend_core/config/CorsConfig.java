package sss.com.sss_backend_core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Value("${app.cors.allowed-origins}")
    private String[] allowedOrigins;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Áp dụng cho toàn bộ endpoint bắt đầu bằng /api/
                .allowedOrigins(allowedOrigins)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*") // Cho phép các header như Content-Type, Authorization
                .allowCredentials(true) // Cho phép gửi kèm Cookie hoặc JWT header
                .maxAge(3600); // Cache kết quả Preflight trong 1 giờ để giảm request OPTIONS thừa
    }
}