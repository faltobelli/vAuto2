package com.cox.vAuto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class VAutoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VAutoApplication.class, args);
	}
}
