package com.sumit.ridesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class RidesystemApplication {

	public static void main(
			String[] args
	) {

		SpringApplication.run(
				RidesystemApplication.class,
				args
		);

		System.out.println(
				"================================="
		);

		System.out.println(
				"CABZI BACKEND STARTED SUCCESSFULLY"
		);

		System.out.println(
				"================================="
		);
	}
}