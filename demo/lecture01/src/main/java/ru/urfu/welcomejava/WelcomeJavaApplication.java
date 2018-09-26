package ru.urfu.welcomejava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class WelcomeJavaApplication {
	public static void main(String[] args) {
		ApiContextInitializer.init();
		SpringApplication.run(WelcomeJavaApplication.class, args);
	}
}
