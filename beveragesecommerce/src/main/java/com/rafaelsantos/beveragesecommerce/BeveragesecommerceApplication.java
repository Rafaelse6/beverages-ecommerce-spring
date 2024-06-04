package com.rafaelsantos.beveragesecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class BeveragesecommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeveragesecommerceApplication.class, args);
	}

}
