package ru.hse.infotouch.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(scanBasePackages = {"ru.hse.infotouch"})
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.core.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.core.domain"})
public class InfotouchFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfotouchFeedApplication.class, args);
    }

}

