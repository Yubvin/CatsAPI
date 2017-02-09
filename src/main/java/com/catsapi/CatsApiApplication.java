package com.catsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class CatsApiApplication {

	public static HashMap<Long, Cat> hmCat;

	public static void main(String[] args) {

		hmCat = new HashMap<Long, Cat>();

		Cat firstCat = new Cat("Felix", (short) 4, "Bengal");
		hmCat.put(new Long(firstCat.getId()), firstCat);

		SpringApplication.run(CatsApiApplication.class, args);

		Cat secondCat = new Cat("Ayame", (short)10, "Manx");
		hmCat.put(new Long(secondCat.getId()), secondCat);
	}
}
