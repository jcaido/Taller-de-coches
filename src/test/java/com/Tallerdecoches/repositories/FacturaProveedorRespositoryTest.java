package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Proveedor;
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
public class FacturaProveedorRespositoryTest {

    @Autowired
    FacturaProveedorRepository facturaProveedorRepository;

    @Autowired
    CodigoPostalRepository codigoPostalRepository;

    @Autowired
    ProveedorRepository proveedorRepository;

    private FacturaProveedor facturaProveedor;
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
        facturaProveedor = FacturaProveedor.builder()
                .fechaFactura(LocalDate.of(2023, 06, 10))
                .numeroFactura("AAAA")
                .tipoIVA(21)
                .contabilizada(false)
                .proveedor(proveedor)
                .build();
        facturaProveedorRepository.save(facturaProveedor);
    }

    @DisplayName("Test para guardar una factura de proveedor")
    @Test
    void guardarFacturaProveedorTest() {
        FacturaProveedor facturaProveedor1 = FacturaProveedor.builder()
                .fechaFactura(LocalDate.of(2023, 06, 14))
                .numeroFactura("BBBB")
                .tipoIVA(21)
                .contabilizada(false)
                .proveedor(proveedor)
                .build();
        FacturaProveedor facturaProveedorGuardada = facturaProveedorRepository.save(facturaProveedor1);
        assertThat(facturaProveedorGuardada.getId()).isGreaterThan(0);
        assertThat(facturaProveedorGuardada.getNumeroFactura()).isEqualTo("BBBB");
    }

    @DisplayName("Test para listar todas las facturas de proveedores")
    @Test
    void ListarTodasFacturasProveedoresTest() {
        FacturaProveedor facturaProveedor1 = FacturaProveedor.builder()
                .fechaFactura(LocalDate.of(2023, 06, 14))
                .numeroFactura("BBBB")
                .tipoIVA(21)
                .contabilizada(false)
                .proveedor(proveedor)
                .build();
        FacturaProveedor facturaProveedorGuardada = facturaProveedorRepository.save(facturaProveedor1);
        List<FacturaProveedor> facturas = facturaProveedorRepository.findAll();
        assertThat(facturas.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener una factura de proveedor por su id")
    @Test
    void obtenerFacturaProveedorPorIdTest() {
        FacturaProveedor factura = facturaProveedorRepository.findById(facturaProveedor.getId()).get();
        assertThat(factura.getNumeroFactura()).isEqualTo("AAAA");
    }

    @DisplayName("Test para obtener una factura de proveedor por el numero de factura")
    @Test
    void obtenerFacturasProveedorPorNumeroFacturaTest() {
        FacturaProveedor factura = facturaProveedorRepository.findByNumeroFactura(facturaProveedor.getNumeroFactura());
        assertThat(factura.getNumeroFactura()).isEqualTo("AAAA");
    }

    @DisplayName("Test para actualizar una factura de proveedor")
    @Test
    void actualizarFacturaProveedorTest() {
        FacturaProveedor facturaAActualizar = facturaProveedorRepository.findById(facturaProveedor.getId()).get();
        facturaAActualizar.setFechaFactura(LocalDate.of(2023, 06, 01));
        facturaAActualizar.setNumeroFactura("SSSS");
        facturaAActualizar.setTipoIVA(12);
        facturaAActualizar.setContabilizada(false);
        facturaAActualizar.setProveedor(proveedor);
        FacturaProveedor facturaProveedorActualizada = facturaProveedorRepository.save(facturaAActualizar);
        assertThat(facturaProveedorActualizada.getNumeroFactura()).isEqualTo("SSSS");
    }
}
