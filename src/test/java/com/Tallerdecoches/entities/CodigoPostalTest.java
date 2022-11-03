package com.Tallerdecoches.entities;

import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CodigoPostalTest {

	@Autowired
	CodigoPostalRepository repository;

	@Test
	void crearCodigoPostalTest() {
		CodigoPostal codigoPostal = new CodigoPostal(null, "14920", "Aguilar", "Cordoba");
		repository.save(codigoPostal);
	}

	@Test
	void listarCodigosPostalesTest() {
		repository.findAll();
	}

	@Test
	void modificarCodigoPostalTest() {

		Optional<CodigoPostal> codigo = repository.findById(10L);

		if (codigo.isPresent()) {
			codigo.get().setLocalidad("Linares");
			repository.save(codigo.get());
		}
	}

	@Test
	void borrarCodigoPostalTet() {

		Optional<CodigoPostal> codigo = repository.findById(10L);

		if (codigo.isPresent())
			repository.delete(codigo.get());
	}

	@Test
	void consultarCodigoPostalPorIdTest() {

		Optional<CodigoPostal> codigo = repository.findById(1L);

		if (codigo.isPresent()) {
			System.out.println(codigo.get().getCodigo());
			System.out.println(codigo.get().getLocalidad());
			System.out.println(codigo.get().getProvincia());
		}
	}
}
