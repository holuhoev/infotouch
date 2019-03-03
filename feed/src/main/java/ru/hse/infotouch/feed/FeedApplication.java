package ru.hse.infotouch.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.hse.infotouch")
@PropertySources(value = {@PropertySource("classpath:application-feed.properties")})
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.domain"})
public class FeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(FeedApplication.class, args);
    }
}