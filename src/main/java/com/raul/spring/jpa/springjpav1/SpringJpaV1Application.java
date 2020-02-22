package com.raul.spring.jpa.springjpav1;

import com.raul.spring.jpa.springjpav1.models.service.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringJpaV1Application implements CommandLineRunner {

	@Autowired
	IUploadService uploadService;

	public static void main(String[] args) {
		SpringApplication.run(SpringJpaV1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadService.deleteAll();
		uploadService.init();
	}
}
