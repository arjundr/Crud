package com.example.mytask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.mytask.storage.StorageService;
import com.example.mytask.StorageProperties;


@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class MytaskApplication {

	public static void main(String[] args) {
		SpringApplication.run(MytaskApplication.class, args);
	}
	@Bean
	CommandLineRunner init(StorageService storageService) {
		
		return (args)->{
			
			storageService.init();
		};
	}
	
}
