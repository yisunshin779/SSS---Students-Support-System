package sss.com.sss_backend_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EntityScan(basePackages = "sss.com.sss_backend_core.profile.entity")
@EnableJpaRepositories(basePackages = "sss.com.sss_backend_core.profile.repository")
public class SSS_System {
    public static void main(String[] args) {
        SpringApplication.run(SSS_System.class, args);
    }
}