package com.newbanksystem.spring;

import com.newbanksystem.spring.models.Address;
import com.newbanksystem.spring.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NewBanksytemApplication implements CommandLineRunner {

	@Autowired
	private AddressRepository addressRepository;

	public static void main(String[] args) {
		SpringApplication.run(NewBanksytemApplication.class, args);
	}

	public void run(String... args) throws Exception {
		Address address = Address.builder()
				.address("Rua Abura")
				.cep("02542110")
				.city("São Paulo")
				.district("Imirim")
				.secondAddress("Casa 3")
				.number("641a")
				.state("SP")
				.build();

		Address addressSaved = addressRepository.save(address);
		System.out.println(addressSaved);
	}
}
