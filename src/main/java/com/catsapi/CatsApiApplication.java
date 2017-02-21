package com.catsapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;

@SpringBootApplication
@ComponentScan
public class CatsApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatsApiApplication.class, args);

	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}

	@Bean
	public CommandLineRunner initializeDb(CatRepository repository){
		return (args) -> {
			repository.deleteAll();

			for(int i = 1; i <= 5; i++) {
				repository.save(new Cat(("Cat№" + i), (short)i, ("Breed№" + i)));
			}
		};
	}
}
