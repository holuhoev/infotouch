package ru.hse.infotouch.terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = {"ru.hse.infotouch"})
public class InfotouchTerminalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfotouchTerminalApplication.class, args);
	}

}

