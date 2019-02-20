package ru.hse.infotouch.terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@SpringBootApplication(scanBasePackages = {"ru.hse.infotouch"})
@PropertySources(value = {@PropertySource("classpath:application-terminal.properties")})
public class InfotouchTerminalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfotouchTerminalApplication.class, args);
	}

}

