package com.ssafy.ChallenMungs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class ChallenMungsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallenMungsApplication.class, args);
	}

}
