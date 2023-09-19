package com.rcq.rcqback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RcqbackApplication {

	public static void main(String[] args) {
		SpringApplication.run(RcqbackApplication.class, args);
	}

}
