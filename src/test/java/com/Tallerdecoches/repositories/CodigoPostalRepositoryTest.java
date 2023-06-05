package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class CodigoPostalRepositoryTest {

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    private CodigoPostal codigoPostal;

    @BeforeEach
    void setUp() {
        codigoPostal = CodigoPostal.builder()
                .codigo("14500")
                .localidad("Lucena")
                .provincia("Cordoba")
                .build();
        codigoPostalRepository.save(codigoPostal);
    }

    @DisplayName("Test para guardar un codigo postal")
    @Test
    void guardarCodigoPostalTest() {
        CodigoPostal codigoPostal1 = CodigoPostal.builder()
                .codigo("14920")
                .localidad("Aguilar de la Frontera")
                .provincia("Cordoba")
                .build();
        CodigoPostal codigoPostalGuardado = codigoPostalRepository.save(codigoPostal1);
        assertThat(codigoPostalGuardado).isNotNull();
        assertThat(codigoPostalGuardado.getId()).isGreaterThan(0);
        assertThat(codigoPostalGuardado.getCodigo()).isEqualTo("14920");
    }

    @DisplayName("Test para listar todos los codigos postales")
    @Test
    void listarTodosLosEmpleadosTest() {
        CodigoPostal codigoPostal2 = CodigoPostal.builder()
                .codigo("23456")
                .localidad("Maracena")
                .provincia("Granada")
                .build();
        codigoPostalRepository.save(codigoPostal2);
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findAll();
        assertThat(codigosPostales.size()).isEqualTo(2);
    }
    @DisplayName("Test para obtener un codigo postal por su id")
    @Test
    void obtenerCodigoPostalPorIdTest() {
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findById(codigoPostal.getId()).get();
        assertThat(codigoPostalEncontrado.getLocalidad()).isEqualTo("Lucena");
    }

    @DisplayName("Test para obtener un codigo postal por su codigo")
    @Test
    void obtenerCodigoPostalPorCodigo() {
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findByCodigo(codigoPostal.getCodigo()).get();
        assertThat(codigoPostalEncontrado.getCodigo()).isEqualTo("14500");
    }

    @DisplayName("Test para obtener un codigo postal por su localidad")
    @Test
    void obtenerCodigoPostalPorLocalidad() {
        CodigoPostal codigoPostalEncontrado = codigoPostalRepository.findByLocalidad(codigoPostal.getLocalidad()).get();
        assertThat(codigoPostalEncontrado.getLocalidad()).isEqualTo("Lucena");
    }

}
