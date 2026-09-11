package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DemoApplication {

	@Value("${test.profile:NOT_FOUND}")
	private String profileTest;
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner dbDebug(
			Environment env) {

		return args -> {

//            System.out.println(
//                    "spring.datasource.url = " +
//                            env.getProperty(
//                                    "spring.datasource.url"));
//
//            System.out.println(
//                    "spring.datasource.username = " +
//                            env.getProperty(
//                                    "spring.datasource.username"));
//
//            System.out.println(
//                    "spring.datasource.password = " +
//                            env.getProperty(
//                                    "spring.datasource.password"));

			System.out.println(
					"PROFILE TEST = " + profileTest);
		};
	}
}
