package com.ssafy.ChallenMungs;

import com.ssafy.ChallenMungs.common.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EnableScheduling
@SpringBootApplication
public class ChallenMungsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChallenMungsApplication.class, args);
		System.out.println("야호! 성공적인 빌드에요!");

	}

}
