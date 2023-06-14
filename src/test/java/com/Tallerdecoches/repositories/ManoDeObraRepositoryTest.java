package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.ManoDeObra;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ManoDeObraRepositoryTest {

    @Autowired
    private ManoDeObraRepository manoDeObraRepository;

    private ManoDeObra manoDeObra;

    @BeforeEach
    void setUp() {
        manoDeObra = ManoDeObra.builder()
                .fechaNuevoPrecio(LocalDate.of(2023, 06, 14))
                .precioHoraClienteTaller(25.65)
                .precioHoraClienteTallerActual(true)
                .build();
        manoDeObraRepository.save(manoDeObra);
    }
}
