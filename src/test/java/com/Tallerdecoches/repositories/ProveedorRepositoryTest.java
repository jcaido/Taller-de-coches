package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Proveedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ProveedorRepositoryTest {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CodigoPostalRepository codigoPostalRepository;

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
    }

    @DisplayName("Test para guardar un proveedor")
    @Test
    void guardarProveedorTest() {
        CodigoPostal codigoPostal1 = CodigoPostal.builder()
                .codigo("28700")
                .localidad("Lucena")
                .provincia("Cordoba")
                .build();
        codigoPostalRepository.save(codigoPostal1);

        Proveedor proveedor1 = Proveedor.builder()
                .nombre("RECACOR SL")
                .dniCif("B22333444")
                .domicilio("Calle Alta, 6")
                .codigoPostal(codigoPostal1)
                .build();
        proveedorRepository.save(proveedor1);
        assertThat(proveedor1.getNombre()).isEqualTo("RECACOR SL");
        assertThat(proveedor1.getCodigoPostal().getLocalidad()).isEqualTo("Lucena");
    }
}
