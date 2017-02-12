package com.catsapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

@SpringBootApplication
public class CatsApiApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatsApiApplication.class, args);

	}

	@Bean
	public CommandLineRunner initializeDb(CatRepository repository){
		return (args) -> {
			repository.deleteAll();

			for(int i = 1; i < 20; i++) {
				repository.save(new Cat(("Cat№" + i), (short)i, ("Breed№" + i)));
			}
		};
	}
}
