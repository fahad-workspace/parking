package com.challenge.parking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Fahad Sarwar
 */
@SpringBootApplication
@EnableSwagger2
public class ParkingApplication {

	public static void main(String[] args) {

		SpringApplication.run(ParkingApplication.class, args);
	}

	/*
	 * Swagger configuration.
	 */
	@Bean
	public Docket api() {

		return new Docket(DocumentationType.SWAGGER_2).apiInfo(new ApiInfoBuilder().title("Parking App").description("API Definition for Parking App").version("0.0.1-SNAPSHOT")
				.contact(new Contact("Fahad Sarwar", "https://www.linkedin.com/in/c2sarwar/", "fahad.sagittarius@yahoo.com")).build()).enable(true).select()
				.apis(RequestHandlerSelectors.basePackage("com.challenge.parking.controllers")).paths(PathSelectors.any()).build();
	}
}
