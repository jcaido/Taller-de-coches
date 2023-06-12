package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.entities.Vehiculo;
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
public class VehiculoRepositoryTest {

    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private CodigoPostalRepository codigoPostalRepository;
    @Autowired
    private PropietarioRepository propietarioRepository;

    private CodigoPostal codigoPostal;
    private Vehiculo vehiculo;
    private Propietario propietario;

    @BeforeEach
    void setUp() {
        codigoPostal = CodigoPostal.builder()
                .codigo("14920")
                .localidad("Aguilar de la Frontera")
                .provincia("Cordoba")
                .build();
        codigoPostalRepository.save(codigoPostal);
        propietario = Propietario.builder()
                .nombre("Antonio")
                .primerApellido("Perez")
                .segundoApellido("Segundo")
                .dni("44555666G")
                .domicilio("Calle Larga, 56")
                .codigoPostal(codigoPostal)
                .build();
        propietarioRepository.save(propietario);
        vehiculo = Vehiculo.builder()
                .matricula("4455FGH")
                .marca("PEUGEOT")
                .modelo("407")
                .color("blanco")
                .propietario(propietario)
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
                .propietario(propietario)
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
                .propietario(propietario)
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
                .propietario(propietario)
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
                .propietario(propietario)
                .build();
        vehiculoRepository.save(vehiculoNuevo);
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(vehiculo.getMarca(), vehiculo.getModelo());
        assertThat(vehiculos.size()).isEqualTo(2);
    }

    @DisplayName("Test para actualizar una vehiculo")
    @Test
    void actualizarVehiculoTest() {
        Vehiculo vehiculoAActualizar = vehiculoRepository.findById(vehiculo.getId()).get();
        vehiculoAActualizar.setMatricula("1122SSS");
        vehiculoAActualizar.setMarca("FORD");
        vehiculoAActualizar.setModelo("FOCUS");
        vehiculoAActualizar.setColor("marron");
        Vehiculo vehiculoActualizado = vehiculoRepository.save(vehiculoAActualizar);
        assertThat(vehiculoActualizado.getMatricula()).isEqualTo("1122SSS");
        assertThat(vehiculoActualizado.getMarca()).isEqualTo("FORD");
        assertThat(vehiculoActualizado.getModelo()).isEqualTo("FOCUS");
        assertThat(vehiculoActualizado.getColor()).isEqualTo("marron");
    }

    @DisplayName("Test para eliminar un vehiculo")
    @Test
    void eliminarVehiculoTest() {
        vehiculoRepository.deleteById(vehiculo.getId());
        Optional<Vehiculo> vehiculoEliminado = vehiculoRepository.findById(vehiculo.getId());
        assertThat(vehiculoEliminado).isEmpty();
    }

    @DisplayName("Test para comprobar si existe un vehiculo por su id")
    @Test
    void existeVehiculoPorIdTest() {
        boolean isExists = vehiculoRepository.existsById(vehiculo.getId());
        assertThat(isExists).isTrue();
    }

    @DisplayName("Test para comprobar si existe un vehiculo por su matricula")
    @Test
    void existeVehiculoPorMatriculaTest() {
        boolean isExists = vehiculoRepository.existsByMatricula(vehiculo.getMatricula());
        assertThat(isExists).isTrue();
    }
}
