package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Vehiculo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
