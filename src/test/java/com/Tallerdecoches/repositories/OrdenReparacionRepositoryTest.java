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
public class OrdenReparacionRepositoryTest {

    @Autowired
    private OrdenReparacionRepository ordenReparacionRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;
    @Autowired
    private CodigoPostalRepository codigoPostalRepository;
    @Autowired
    private PropietarioRepository propietarioRepository;
    @Autowired
    private ManoDeObraRepository manoDeObraRepository;

    private CodigoPostal codigoPostal;
    private Vehiculo vehiculo;
    private Propietario propietario;
    private ManoDeObra manoDeObra;
    private OrdenReparacion ordenReparacion;

    @BeforeEach
    void setUp() {
        codigoPostal = CodigoPostal.builder()
                .codigo("14920")
                .localidad("Aguilar de la Frontera")
                .provincia("Cordoba")
                .build();
        codigoPostalRepository.save(codigoPostal);
        propietario = Propietario.builder()
                .nombre("Antonio")
                .primerApellido("Perez")
                .segundoApellido("Segundo")
                .dni("44555666G")
                .domicilio("Calle Larga, 56")
                .codigoPostal(codigoPostal)
                .build();
        propietarioRepository.save(propietario);
        vehiculo = Vehiculo.builder()
                .matricula("4455FGH")
                .marca("PEUGEOT")
                .modelo("407")
                .color("blanco")
                .propietario(propietario)
                .build();
        vehiculoRepository.save(vehiculo);
        manoDeObra = ManoDeObra.builder()
                .fechaNuevoPrecio(LocalDate.of(2023, 06, 14))
                .precioHoraClienteTaller(25.65)
                .precioHoraClienteTallerActual(true)
                .build();
        manoDeObraRepository.save(manoDeObra);
        ordenReparacion = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 01))
                .fechaCierre(LocalDate.of(2023, 06, 02))
                .descripcion("FALLO MOTOR")
                .kilometros(29899L)
                .horas(2D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion);
    }

    @DisplayName("Test para guardar una orden de reparacion")
    @Test
    void guardarOrdenReparacionTest() {
        OrdenReparacion ordenReparacion1 = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 05))
                .fechaCierre(LocalDate.of(2023, 06, 06))
                .descripcion("REVISION CAUDALIMETRO")
                .kilometros(50987L)
                .horas(1D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion1);
        assertThat(ordenReparacion1.getId()).isGreaterThan(0);
        assertThat(ordenReparacion1.getDescripcion()).isEqualTo("REVISION CAUDALIMETRO");
    }

    @DisplayName("Test para ortener una lista con todas las ordenes de reparacion")
    @Test
    void listarTodasOrdenesReparacionTest() {
        OrdenReparacion ordenReparacion1 = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 05))
                .fechaCierre(LocalDate.of(2023, 06, 06))
                .descripcion("REVISION CAUDALIMETRO")
                .kilometros(50987L)
                .horas(1D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion1);
        List<OrdenReparacion> ordenes = ordenReparacionRepository.findAll();
        assertEquals(2, ordenes.size());
    }

    @DisplayName("Test para obtener una orden de reparacio por su id")
    @Test
    void obtenerOrdenReparacionPorIdTest() {
        OrdenReparacion ordenReparacionEncontrada = ordenReparacionRepository.findById(ordenReparacion.getId()).get();
        assertEquals("FALLO MOTOR", ordenReparacionEncontrada.getDescripcion());
    }

    @DisplayName("Test para obtener una lista con ordenes de reparacion por fecha de apertura")
    @Test
    void obtenerOrdenesReparacionPorFechaAperturaTest() {
        OrdenReparacion ordenReparacion1 = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 01))
                .fechaCierre(LocalDate.of(2023, 06, 06))
                .descripcion("REVISION CAUDALIMETRO")
                .kilometros(50987L)
                .horas(1D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion1);
        List<OrdenReparacion> ordenes = ordenReparacionRepository.findByFechaApertura(ordenReparacion.getFechaApertura());
        assertEquals(2, ordenes.size());
    }

    @DisplayName("Test para obtener una lista con ordenes de reparacion por fecha de cierre")
    @Test
    void obtenerOrdenesReparacionPorFechaCierreTest() {
        OrdenReparacion ordenReparacion1 = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 02))
                .fechaCierre(LocalDate.of(2023, 06, 02))
                .descripcion("REVISION CAUDALIMETRO")
                .kilometros(50987L)
                .horas(1D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion1);
        List<OrdenReparacion> ordenes = ordenReparacionRepository.findByFechaCierre(ordenReparacion.getFechaCierre());
        assertEquals(2, ordenes.size());
    }

    @DisplayName("Test para obtener una lista de ordenes de reparacion por estado")
    @Test
    void obtenerOrdenesReparacionPorEstadoTest() {
        OrdenReparacion ordenReparacion1 = OrdenReparacion.builder()
                .fechaApertura(LocalDate.of(2023, 06, 05))
                .fechaCierre(LocalDate.of(2023, 06, 06))
                .descripcion("REVISION CAUDALIMETRO")
                .kilometros(50987L)
                .horas(1D)
                .manoDeObra(manoDeObra)
                .cerrada(false)
                .facturada(false)
                .vehiculo(vehiculo)
                .build();
        ordenReparacionRepository.save(ordenReparacion1);
        List<OrdenReparacion> ordenes =ordenReparacionRepository.findByCerrada(ordenReparacion1.getCerrada());
        assertEquals(2, ordenes.size());
    }

    @DisplayName("Test para actualizar una orden de reparacion")
    @Test
    void actualizarOrdenReparacionTest() {
        OrdenReparacion ordenReparacionAActualizar = ordenReparacionRepository.findById(ordenReparacion.getId()).get();
        ordenReparacionAActualizar.setFechaApertura(LocalDate.of(2023, 06, 10));
        ordenReparacionAActualizar.setFechaCierre(LocalDate.of(2023, 06, 11));
        ordenReparacionAActualizar.setDescripcion("AVERIA EMBRAGUE");
        ordenReparacionAActualizar.setKilometros(12988L);
        ordenReparacionAActualizar.setHoras(4D);
        ordenReparacionAActualizar.setCerrada(true);
        ordenReparacionAActualizar.setFacturada(true);
        OrdenReparacion ordenReparacionActualizada = ordenReparacionRepository.save(ordenReparacionAActualizar);
        assertEquals("AVERIA EMBRAGUE", ordenReparacionActualizada.getDescripcion());
        assertEquals(12988L, ordenReparacionActualizada.getKilometros());
    }

    @DisplayName("Test para eliminar una orden de reparacion")
    @Test
    void eliminarOrdenReparacionTest() {
        ordenReparacionRepository.deleteById(ordenReparacion.getId());
        Optional<OrdenReparacion> ordenReparacionEliminada = ordenReparacionRepository.findById(ordenReparacion.getId());
        assertTrue(ordenReparacionEliminada.isEmpty());
    }

}
