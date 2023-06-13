package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Pieza;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

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
}
