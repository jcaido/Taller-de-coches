package com.Tallerdecoches;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.DTOs.VehiculoDTO;
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

	@Bean
	public CodigoPostalDTO codigoPostalDTO() {
		return new CodigoPostalDTO();
	}

	@Bean
	public PropietarioDTO propietarioDTO() {
		return new PropietarioDTO();
	}

	@Bean
	public VehiculoDTO vehiculoDTO() {
		return new VehiculoDTO();
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(TallerDeCochesApplication.class, args);
		CodigoPostalRepository repository = context.getBean(CodigoPostalRepository.class);

	}
}
