package com.Tallerdecoches;

import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TallerDeCochesApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(TallerDeCochesApplication.class, args);
		CodigoPostalRepository repository = context.getBean(CodigoPostalRepository.class);

	}
}
