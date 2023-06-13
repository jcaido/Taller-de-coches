package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.Pieza;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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

    @DisplayName("Test para guardar una pieza con la misma referencia")
    @Test
    void guarPiezaMismaReferenciaTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("AAAA")
                .nombre("tornillo")
                .precio(0.65)
                .build();
        assertThrows(DataIntegrityViolationException.class, () -> {
            piezaRepository.save(pieza1);
        });
    }

    @DisplayName("Test para listar todas las piezas")
    @Test
    void listarTodasLasPiezasTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("BBBB")
                .nombre("tornillo")
                .precio(0.65)
                .build();
        piezaRepository.save(pieza1);
        List<Pieza> piezas = piezaRepository.findAll();
        assertEquals(2, piezas.size());
    }

    @DisplayName("Test para obtener una pieza por su id")
    @Test
    void obtenerPiezaPorIdTest() {
        Pieza piezaEncontrada = piezaRepository.findById(pieza.getId()).get();
        assertEquals("AAAA", piezaEncontrada.getReferencia());
    }

    @DisplayName("Test para obtener una pieza por su referencia")
    @Test
    void obtenerPiezaPorReferenciaTest() {
        Pieza piezaEncontrada = piezaRepository.findByReferencia(pieza.getReferencia()).get();
        assertEquals("AAAA", piezaEncontrada.getReferencia());
    }

    @DisplayName("Test para obtener una lista de piezas por nombre")
    @Test
    void obtenerPiezasPorNombreTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("BBBB")
                .nombre("Arandela")
                .precio(0.65)
                .build();
        piezaRepository.save(pieza1);
        List<Pieza> piezas = piezaRepository.findByNombre(pieza.getNombre());
        assertEquals(2, piezas.size());
    }

    @DisplayName("Test para actualizar una pieza")
    @Test
    void actualizarPiezaTest() {
        Pieza piezaAActualizar = piezaRepository.findById(pieza.getId()).get();
        piezaAActualizar.setReferencia("CCCC");
        piezaAActualizar.setNombre("motor");
        piezaAActualizar.setPrecio(4.87);
        Pieza piezaActualizada = piezaRepository.save(piezaAActualizar);
        assertEquals("CCCC", piezaActualizada.getReferencia());
        assertEquals("motor", piezaActualizada.getNombre());
        assertEquals(4.87, piezaActualizada.getPrecio());
    }
}
