package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

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
}
