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
        CodigoPostal codigoPostalGuardado = codigoPostalRepository.save(Datos.CODIGO_POSTAL_1);
        assertThat(codigoPostalGuardado).isNotNull();
        assertThat(codigoPostalGuardado.getId()).isGreaterThan(0);
        assertThat(codigoPostalGuardado.getCodigo()).isEqualTo("14920");
    }

    @DisplayName("Test para listar todos los codigos postales")
    @Test
    void listarTodosLosCodigosPostalesTest() {
        codigoPostalRepository.save(Datos.CODIGO_POSTAL_1);
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

    @DisplayName("Test para obtener una lista de codigos postales por provincia")
    @Test
    void obtenerCodigoPostalPorProvincia() {
        codigoPostalRepository.save(Datos.CODIGO_POSTAL_2);
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findByProvincia(codigoPostal.getProvincia());
        assertThat(codigosPostales.size()).isEqualTo(2);
    }

    @DisplayName("Test para actualizar un codigo postal")
    @Test
    void actualizarCodigoPostalTest() {
        //CodigoPostal codigoPostalAActualizar = codigoPostalRepository.findById(codigoPostal.getId()).get();
        CodigoPostal codigoPostalAActualizar = codigoPostalRepository.findById(codigoPostal.getId()).get();
        codigoPostalAActualizar.setCodigo("33333");
        codigoPostalAActualizar.setLocalidad("Fuengirola");
        codigoPostalAActualizar.setProvincia("Malaga");
        CodigoPostal codigoPostalActualizado = codigoPostalRepository.save(codigoPostalAActualizar);
        assertThat(codigoPostalActualizado.getCodigo()).isEqualTo("33333");
        assertThat(codigoPostalActualizado.getLocalidad()).isEqualTo("Fuengirola");
        assertThat(codigoPostalActualizado.getProvincia()).isEqualTo("Malaga");
    }

    @DisplayName("Test para eliminar un codigo postal")
    @Test
    void eliminarCodigoPostalTest() {
        codigoPostalRepository.deleteById(codigoPostal.getId());
        Optional<CodigoPostal> codigoPostalEliminado = codigoPostalRepository.findById(codigoPostal.getId());
        assertThat(codigoPostalEliminado).isEmpty();
    }

    @DisplayName("Test para comprobar si existe un codigo postal por su codigo")
    @Test
    void existsCodigoPostalByCodigoTest() {
        boolean isExists = codigoPostalRepository.existsByCodigo(codigoPostal.getCodigo());
        assertThat(isExists).isTrue();
    }

    @DisplayName("Test para comprobar si existe un codigo postal pr su localidad")
    @Test
    void existsCodigoPostalByLocalidad() {
        boolean isExists = codigoPostalRepository.existsByLocalidad(codigoPostal.getLocalidad());
        assertThat(isExists).isTrue();
    }

}
