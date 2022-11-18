package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CodigoPostalRepositoryTest {

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    private CodigoPostal codigoPostal;

    @BeforeEach
    void setUp() {
        codigoPostal = new CodigoPostal(null, "99100", "Chipiona", "Cadiz");
    }

    @Test
    @DisplayName("Test para guardar un Codigo Postal")
    void saveTest() {
        //given
        CodigoPostal CodigoPostal1 = new CodigoPostal(null, "99000", "Roquetas", "Almeria");

        //when
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(CodigoPostal1);

        //then
        assertThat(nuevoCodigoPostal).isNotNull();
        assertThat(nuevoCodigoPostal.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Test para obtener todos los Codigos Postales")
    void findAllTest() {
        //given
        CodigoPostal CodigoPostal1 = new CodigoPostal(null, "99000", "Roquetas", "Almeria");

        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);
        CodigoPostal otroCodigoostal = codigoPostalRepository.save(CodigoPostal1);

        //when
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findAll();

        //then
        assertThat(codigosPostales).isNotEmpty();
        assertEquals(codigosPostales.size(), 2);
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su id")
    void findByIdTest() {
        //given
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);

        //when
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findById(codigoPostal.getId()).get();

        //then
        assertThat(codigoPostalEncontrado).isNotNull();
        assertEquals(codigoPostalEncontrado.getCodigo(), "99100");

    }

    @Test
    @DisplayName("Test para eliminar un Codigo Postal por su id")
    void deleteByIdTest() {
        //given
        codigoPostalRepository.save(codigoPostal);

        //when
        codigoPostalRepository.deleteById(codigoPostal.getId());
        Optional<CodigoPostal> codigoPostalOp = codigoPostalRepository.findById(codigoPostal.getId());

        //then
        assertThat(codigoPostalOp).isEmpty();
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su codigo")
    void findByCodigoTest() {
        //given
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);

        //when
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findByCodigo(codigoPostal.getCodigo()).get();

        //then
        assertThat(codigoPostalEncontrado).isNotNull();
        assertEquals(codigoPostalEncontrado.getCodigo(), "99100");

    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su localidad")
    void findByLocalidadTest() {
        //given
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);

        //when
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findByLocalidad(codigoPostal.getLocalidad()).get();

        //then
        assertThat(codigoPostalEncontrado).isNotNull();
        assertEquals(codigoPostalEncontrado.getCodigo(), "99100");

    }

    @Test
    @DisplayName("Test para comprobar si un Codigo Postal buscado por su codigo existe ")
    void existsByCodigoTest() {
        //given
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);

        //when
        boolean codigoPostalEncontrado = codigoPostalRepository.existsByCodigo(codigoPostal.getCodigo());

        //then
        assertThat(codigoPostalEncontrado).isNotNull();
        assertEquals(codigoPostalEncontrado, true);

    }

    @Test
    @DisplayName("Test para comprobar si un Codigo Postal buscado por su localidad existe ")
    void existsByLocalidadTest() {
        //given
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);

        //when
        boolean codigoPostalEncontrado = codigoPostalRepository.existsByLocalidad(codigoPostal.getLocalidad());

        //then
        assertThat(codigoPostalEncontrado).isNotNull();
        assertEquals(codigoPostalEncontrado, true);

    }

    @Test
    @DisplayName("Test para obtener todos los Codigos Postales de una provincia")
    void findByProvinciaTest() {
        //given
        CodigoPostal CodigoPostal1 = new CodigoPostal(null, "99000", "Roquetas", "Almeria");
        CodigoPostal CodigoPostal2 = new CodigoPostal(null, "88000", "Vera", "Almeria");

        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);
        CodigoPostal otroCodigoostal = codigoPostalRepository.save(CodigoPostal1);
        CodigoPostal otroCodigoostalMas = codigoPostalRepository.save(CodigoPostal2);

        //when
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findByProvincia("Almeria");

        //then
        assertThat(codigosPostales).isNotEmpty();
        assertEquals(codigosPostales.size(), 2);
    }

}
