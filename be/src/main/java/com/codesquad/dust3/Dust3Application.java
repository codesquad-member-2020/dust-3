package com.codesquad.dust3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Dust3Application {

	public static void main(String[] args) {
		SpringApplication.run(Dust3Application.class, args);
	}

}
