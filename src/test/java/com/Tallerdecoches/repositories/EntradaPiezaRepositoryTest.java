package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

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
}
