package ru.hse.infotouch;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories(basePackages = {"ru.hse.infotouch.repo"})
@EntityScan(basePackages = {"ru.hse.infotouch.domain"})
public class InfotouchCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfotouchCoreApplication.class, args);
    }

}