package ru.hse.infotouch.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"ru.hse.infotouch"})
public class InfotouchFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfotouchFeedApplication.class, args);
    }

}

