package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Propietario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PropietarioRepositoryTest {

    @Autowired
    private PropietarioRepository propietarioRepository;

    private Propietario propietario;

    @BeforeEach
    void setUp() {
        propietario = Propietario.builder()
                .nombre("Antonio")
                .primerApellido("Perez")
                .segundoApellido("Segundo")
                .dni("44555666G")
                .domicilio("Calle Larga, 56")
                .build();
        propietarioRepository.save(propietario);
    }

    @DisplayName("Test para guardar un propietario")
    @Test
    void guardarPropietarioTest() {
        Propietario propietario1 = Propietario.builder()
                .nombre("Jaime")
                .primerApellido("Rodriguez")
                .segundoApellido("Jimenez")
                .dni("55888999A")
                .domicilio("Calle Fuentecita, 98")
                .build();
        Propietario propietarioGuardado = propietarioRepository.save(propietario1);
        assertThat(propietarioGuardado.getId()).isGreaterThan(0);
        assertThat(propietarioGuardado.getNombre()).isEqualTo("Jaime");
    }
}
