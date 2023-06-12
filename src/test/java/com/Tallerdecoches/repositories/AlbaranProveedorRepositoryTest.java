package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class AlbaranProveedorRepositoryTest {
    @Autowired
    private AlbaranProveedorRepository albaranProveedorRepository;

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    private AlbaranProveedor albaranProveedor;
    private CodigoPostal codigoPostal;
    private Proveedor proveedor;

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
    }

    @DisplayName("Test para guardar un albaran de proveedo")
    @Test
    void guardarAlbaranProveedorTest() {
        AlbaranProveedor albaranProveedor1 = AlbaranProveedor.builder()
                .proveedor(proveedor)
                .fechaAlbaran(LocalDate.of(2023, 5,12))
                .numeroAlbaran("45TR")
                .facturado(false)
                .build();
        AlbaranProveedor albaranProveedorGuardado = albaranProveedorRepository.save(albaranProveedor1);
        assertThat(albaranProveedorGuardado.getId()).isGreaterThan(0);
        assertThat(albaranProveedorGuardado.getNumeroAlbaran()).isEqualTo("45TR");
    }

    @DisplayName("Test para listar todos los albaranes de proveedores")
    @Test
    void listarTodosLosAlbaranesProveedorTest() {
        AlbaranProveedor albaranProveedor1 = AlbaranProveedor.builder()
                .proveedor(proveedor)
                .fechaAlbaran(LocalDate.of(2023, 5,12))
                .numeroAlbaran("45TR")
                .facturado(false)
                .build();
        albaranProveedorRepository.save(albaranProveedor1);
        List<AlbaranProveedor> albaranes = albaranProveedorRepository.findAll();
        assertEquals(2, albaranes.size());
    }

    @DisplayName("Test para obtener un albaran de proveedor por su id")
    @Test
    void obtenerAlbaranProveedorPorIdTest() {
        AlbaranProveedor albaran = albaranProveedorRepository.findById(albaranProveedor.getId()).get();
        assertEquals("123E", albaranProveedor.getNumeroAlbaran());
    }

    @DisplayName("Test para obtener una lista de albaranes de proveedor por numero de albaran")
    @Test
    void obtenerAlbaranesProveedorPorNumeroAlbaranTest() {
        AlbaranProveedor albaranProveedor1 = AlbaranProveedor.builder()
                .proveedor(proveedor)
                .fechaAlbaran(LocalDate.of(2023, 5,12))
                .numeroAlbaran("123E")
                .facturado(false)
                .build();
        albaranProveedorRepository.save(albaranProveedor1);
        List<AlbaranProveedor> albaranes = albaranProveedorRepository.findByNumeroAlbaran(albaranProveedor.getNumeroAlbaran());
        assertThat(albaranes.size()).isEqualTo(2);
    }

    @DisplayName("Test para actualizar un albaran de proveedor")
    @Test
    void actualizarAlbaranProveedorTest() {
        AlbaranProveedor albaranProveedorAActualizar = albaranProveedorRepository.findById(albaranProveedor.getId()).get();
        albaranProveedorAActualizar.setProveedor(proveedor);
        albaranProveedorAActualizar.setFechaAlbaran(LocalDate.of(2023, 4,12));
        albaranProveedorAActualizar.setNumeroAlbaran("WWW");
        albaranProveedorRepository.save(albaranProveedorAActualizar);
        assertEquals("WWW", albaranProveedorAActualizar.getNumeroAlbaran());
    }

    @DisplayName("Test para eliminar un albaran de proveedor")
    @Test
    void eliminarAlbaranProveedorTest() {
        albaranProveedorRepository.deleteById(albaranProveedor.getId());
        Optional<AlbaranProveedor> albaranEliminado = albaranProveedorRepository.findById(albaranProveedor.getId());
        assertThat(albaranEliminado).isEmpty();
    }

    @DisplayName("Test para comprobar si existe un albaran de proveedor por su id")
    @Test
    void existeAlbaranProveedorPorIdTest() {
        boolean isExists = albaranProveedorRepository.existsById(albaranProveedor.getId());
        assertTrue(isExists);
    }
}
