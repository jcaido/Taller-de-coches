package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class EntradaPiezaRepositoryTest {

    @Autowired
    private EntradaPiezaRepository entradaPiezaRepository;

    @Autowired
    private PiezaRepository piezaRepository;

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private AlbaranProveedorRepository albaranProveedorRepository;

    private Pieza pieza;
    private CodigoPostal codigoPostal;
    private Proveedor proveedor;
    private AlbaranProveedor albaranProveedor;
    private EntradaPieza entradaPieza;

    @BeforeEach
    void setUp() {
        codigoPostal = CodigoPostal.builder()
                .codigo("14920")
                .localidad("Aguilar de la Frontera")
                .provincia("Cordoba")
                .build();
        codigoPostalRepository.save(codigoPostal);
        proveedor = Proveedor.builder()
                .nombre("GRUPO PEÑA SA")
                .dniCif("B14567876")
                .domicilio("Calle Grande, 6")
                .codigoPostal(codigoPostal)
                .build();
        proveedorRepository.save(proveedor);
        albaranProveedor = AlbaranProveedor.builder()
                .proveedor(proveedor)
                .fechaAlbaran(LocalDate.of(2023, 6,12))
                .numeroAlbaran("123E")
                .facturado(false)
                .build();
        albaranProveedorRepository.save(albaranProveedor);
        pieza = Pieza.builder()
                .referencia("AAAA")
                .nombre("Arandela")
                .precio(4.5)
                .build();
        piezaRepository.save(pieza);
        entradaPieza = EntradaPieza.builder()
                .pieza(pieza)
                .cantidad(5)
                .precioEntrada(2.45)
                .albaranProveedor(albaranProveedor)
                .build();
        entradaPiezaRepository.save(entradaPieza);
    }

    @DisplayName("Test para guardar una entrada de una pieza")
    @Test
    void guardarEntradaPiezaTest() {
        EntradaPieza entradaPieza1 = EntradaPieza.builder()
                .pieza(pieza)
                .cantidad(2)
                .precioEntrada(3.56)
                .albaranProveedor(albaranProveedor)
                .build();
        EntradaPieza entradaPiezaGuardada = entradaPiezaRepository.save(entradaPieza1);
        assertThat(entradaPiezaGuardada.getId()).isGreaterThan(0);
        assertThat(entradaPiezaGuardada.getCantidad()).isEqualTo(2);
    }

    @DisplayName("Test para listar todas las entradas de piezas")
    @Test
    void listarTodasEntradaPiezasTest() {
        EntradaPieza entradaPieza1 = EntradaPieza.builder()
                .pieza(pieza)
                .cantidad(2)
                .precioEntrada(3.56)
                .albaranProveedor(albaranProveedor)
                .build();
        EntradaPieza entradaPiezaGuardada = entradaPiezaRepository.save(entradaPieza1);
        List<EntradaPieza> litaEntradaPiezas = entradaPiezaRepository.findAll();
        assertEquals(2, litaEntradaPiezas.size());
    }

    @DisplayName("Test para obtener una entrada de pieza por su id")
    @Test
    void obtenerEntradaPiezaPorIdTest() {
        EntradaPieza entradaPiezaEncontrada = entradaPiezaRepository.findById(entradaPieza.getId()).get();
        assertEquals(5, entradaPiezaEncontrada.getCantidad());
    }

    @DisplayName("Test para actualizar una entrada de pieza")
    @Test
    void actualizarEntradaPiezaTest() {
        EntradaPieza entradaPiezaAActualizar = entradaPiezaRepository.findById(entradaPieza.getId()).get();
        entradaPiezaAActualizar.setCantidad(2);
        entradaPiezaAActualizar.setPrecioEntrada(1.45);
        entradaPiezaAActualizar.setPieza(pieza);
        entradaPiezaAActualizar.setAlbaranProveedor(albaranProveedor);
        assertEquals(2, entradaPiezaAActualizar.getCantidad());
        assertEquals(1.45, entradaPiezaAActualizar.getPrecioEntrada());
    }

    @DisplayName("Test para eliminar una entrada de pieza")
    @Test
    void eliminarEntradaPiezaTest() {
        entradaPiezaRepository.deleteById(entradaPieza.getId());
        Optional<EntradaPieza> entradaPiezaEliminada = entradaPiezaRepository.findById(entradaPieza.getId());
        assertTrue(entradaPiezaEliminada.isEmpty());
    }
}
