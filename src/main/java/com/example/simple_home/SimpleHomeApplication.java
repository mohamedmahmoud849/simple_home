package com.example.simple_home;

import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SimpleHomeApplication {


	public static void main(String[] args) {
		SpringApplication.run(SimpleHomeApplication.class, args);
	}

}
