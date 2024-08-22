package com.example.demo.TooDevs.br;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"service", "dao", "controller", "domain", "com.example.demo.TooDevs.br" , "util"})
@EnableJpaRepositories(basePackages = "dao")
@EntityScan(basePackages = "domain")
public class ApiParaBoletimEscolarApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiParaBoletimEscolarApplication.class, args);
	}

}
