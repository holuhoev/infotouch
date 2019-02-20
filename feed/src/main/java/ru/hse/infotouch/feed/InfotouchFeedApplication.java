package ru.hse.infotouch.feed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@SpringBootApplication(scanBasePackages = {"ru.hse.infotouch"})
@PropertySources(value = {@PropertySource("classpath:application-feed.properties")})
public class InfotouchFeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InfotouchFeedApplication.class, args);
    }

}

