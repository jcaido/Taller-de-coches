package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Pieza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PiezaRepositoryTest {

    @Autowired
    private PiezaRepository piezaRepository;

    private Pieza pieza;

    @BeforeEach
    void setUp() {
        pieza = Pieza.builder()
                .referencia("AAAA")
                .nombre("Arandela")
                .precio(4.5)
                .build();
        piezaRepository.save(pieza);
    }

    @DisplayName("Test para guardar una pieza")
    @Test
    void guardarPiezaTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("BBBB")
                .nombre("tornillo")
                .precio(0.65)
                .build();
        Pieza piezaGuardada = piezaRepository.save(pieza1);
        assertThat(piezaGuardada.getId()).isGreaterThan(0);
        assertThat(piezaGuardada.getReferencia()).isEqualTo("BBBB");
    }
}
