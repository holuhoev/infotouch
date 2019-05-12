package ru.hse.infotouch.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.hse.infotouch.util.config.YamlPropertySourceFactory;

@SpringBootApplication(scanBasePackages = "ru.hse.infotouch")
@PropertySource(factory = YamlPropertySourceFactory.class,
        value = {
                "classpath:application-admin.yaml",
                "classpath:application.yaml"
        })
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.domain.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.domain.models"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
    }
}