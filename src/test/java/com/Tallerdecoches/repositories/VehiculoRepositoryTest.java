package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Vehiculo;
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
public class VehiculoRepositoryTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    private Vehiculo vehiculo;

    @BeforeEach
    void setUp() {
        vehiculo = Vehiculo.builder()
                .matricula("4455FGH")
                .marca("PEUGEOT")
                .modelo("407")
                .color("blanco")
                .build();
        vehiculoRepository.save(vehiculo);
    }

    @DisplayName("Test para guardar un vehiculo")
    @Test
    void guardarVehiculoTest() {
        Vehiculo vehiculo1 = Vehiculo.builder()
                .matricula("4444RRR")
                .marca("RENAULT")
                .modelo("CLIO")
                .color("azul")
                .build();
        Vehiculo vehiculoGuardado = vehiculoRepository.save(vehiculo1);
        assertThat(vehiculoGuardado.getId()).isGreaterThan(0);
        assertThat(vehiculoGuardado.getMatricula()).isEqualTo("4444RRR");
    }

    @DisplayName("Test para listar todos los vehiculos")
    @Test
    void listarTodosLosVehiculosTest() {
        Vehiculo vehiculo1 = Vehiculo.builder()
                .matricula("6788UUU")
                .marca("RENAULT")
                .modelo("LAGUNA")
                .color("rojo")
                .build();
        vehiculoRepository.save(vehiculo1);
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();
        assertThat(vehiculos.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener un vehiculo por su id")
    @Test
    void obtenerVehiculoPorIdTest() {
        Vehiculo vehiculoEncontrado = vehiculoRepository.findById(vehiculo.getId()).get();
        assertThat(vehiculoEncontrado.getMatricula()).isEqualTo("4455FGH");
    }

    @DisplayName("Test para obtener un vehiculo por su matricula")
    @Test
    void obtenerVehiculoPorMatriculaTest() {
        Vehiculo vehiculoEncontrado = vehiculoRepository.findByMatricula(vehiculo.getMatricula()).get();
        assertThat(vehiculoEncontrado.getMatricula()).isEqualTo("4455FGH");
    }

    @DisplayName("Test para obtener una lista de vehiculos por marca")
    @Test
    void obtenervehiculosPorMarcaTest() {
        Vehiculo vehiculoNuevo = Vehiculo.builder()
                .matricula("4465HGF")
                .marca("PEUGEOT")
                .modelo("BOXER")
                .color("negro")
                .build();
        vehiculoRepository.save(vehiculoNuevo);
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarca(vehiculo.getMarca());
        assertThat(vehiculos.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener una lista de vehiculos por marca y modelo")
    @Test
    void obtenerVehiculoPorMarcaYModeloTest() {
        Vehiculo vehiculoNuevo = Vehiculo.builder()
                .matricula("4465HGF")
                .marca("PEUGEOT")
                .modelo("407")
                .color("negro")
                .build();
        vehiculoRepository.save(vehiculoNuevo);
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(vehiculo.getMarca(), vehiculo.getModelo());
        assertThat(vehiculos.size()).isEqualTo(2);
    }
}
