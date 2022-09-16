package com.newbanksystem.spring;

import com.newbanksystem.spring.models.Address;
import com.newbanksystem.spring.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewBanksytemApplication {

	@Autowired
	private AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(NewBanksytemApplication.class, args);
	}
}
