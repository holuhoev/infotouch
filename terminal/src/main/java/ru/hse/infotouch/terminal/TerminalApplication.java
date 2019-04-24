package ru.hse.infotouch.terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.hse.infotouch.util.config.YamlPropertySourceFactory;

@SpringBootApplication(scanBasePackages = "ru.hse.infotouch")
@PropertySource(factory = YamlPropertySourceFactory.class,
        value = {
                "classpath:application-terminal.yaml",
                "classpath:application.yaml"
        })
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.domain.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.domain.models"})
public class TerminalApplication {

    public static void main(String[] args) {
        SpringApplication.run(TerminalApplication.class, args);
    }
}