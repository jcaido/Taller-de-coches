package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.ManoDeObra;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

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

    @DisplayName("Test para listar todas las mano de obra")
    @Test
    void listarTodasLasManoDeObraTest() {
        ManoDeObra manoDeObra1 = ManoDeObra.builder()
                .fechaNuevoPrecio(LocalDate.of(2023, 06, 13))
                .precioHoraClienteTaller(22.98)
                .precioHoraClienteTallerActual(false)
                .build();
        ManoDeObra manoDeObraGuardada = manoDeObraRepository.save(manoDeObra1);
        List<ManoDeObra> listaManoDeObra = manoDeObraRepository.findAll();
        assertThat(listaManoDeObra.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener una mano de obra por su id")
    @Test
    void obtenerManoDeObraPorIdTest() {
        ManoDeObra manoDeObraEncontrada = manoDeObraRepository.findById(manoDeObra.getId()).get();
        assertThat(manoDeObraEncontrada.getPrecioHoraClienteTaller()).isEqualTo(25.65);
    }

    @DisplayName("Test para obtener la mano de obra actual")
    @Test
    void obtenerManoDeObraActualTest() {
        ManoDeObra manoDeObraActual = manoDeObraRepository.findByPrecioHoraClienteTallerActual(true).get();
        assertThat(manoDeObraActual.getPrecioHoraClienteTallerActual()).isTrue();
    }
}
