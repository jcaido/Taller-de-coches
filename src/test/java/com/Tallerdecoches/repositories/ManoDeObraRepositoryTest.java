package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.ManoDeObra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

    @DisplayName("Test para guardar una mano de obra")
    @Test
    void guardarManoDeObraTest() {
        ManoDeObra manoDeObra1 = ManoDeObra.builder()
                .fechaNuevoPrecio(LocalDate.of(2023, 06, 13))
                .precioHoraClienteTaller(22.98)
                .precioHoraClienteTallerActual(false)
                .build();
        ManoDeObra manoDeObraGuardada = manoDeObraRepository.save(manoDeObra1);
        assertThat(manoDeObraGuardada).isNotNull();
        assertThat(manoDeObraGuardada.getId()).isGreaterThan(0);
        assertThat(manoDeObraGuardada.getPrecioHoraClienteTaller()).isEqualTo(22.98);
    }
}
