package ru.hse.infotouch.terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "ru.hse.infotouch")
@PropertySources(value = {@PropertySource("classpath:application-terminal.properties")})
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.domain"})
public class TerminalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerminalApplication.class, args);
    }
}