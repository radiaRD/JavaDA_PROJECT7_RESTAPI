package com.nnk.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpringBootApplication
public class Application {
	private static final Logger logger = LogManager.getLogger(Application.class);
	public static void main(String[] args) {
		logger.info("Initializing Application");
		SpringApplication.run(Application.class, args);
	}
}
