package com.app.ModernKids;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class TrendyKidsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrendyKidsApplication.class, args);
	}

	@GetMapping("/hello")
	public String sayHello(){
		System.out.println("In spring boot app");
		return "Hello Spring World!";
	}

}
