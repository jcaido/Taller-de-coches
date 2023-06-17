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
public class PiezasReparacionRepositoryTest {

    @Autowired
    private PiezasReparacionRepository piezasReparacionRepository;
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
    @Autowired
    private PiezaRepository piezasRepository;

    private CodigoPostal codigoPostal;
    private Vehiculo vehiculo;
    private Propietario propietario;
    private ManoDeObra manoDeObra;
    private OrdenReparacion ordenReparacion;
    private Pieza pieza;
    private PiezasReparacion piezasReparacion;

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
        pieza = Pieza.builder()
                .referencia("AAAA")
                .nombre("Arandela")
                .precio(4.5)
                .build();
        piezasRepository.save(pieza);
        piezasReparacion = PiezasReparacion.builder()
                .pieza(pieza)
                .cantidad(3)
                .ordenReparacion(ordenReparacion)
                .build();
        piezasReparacionRepository.save(piezasReparacion);
    }

    @DisplayName("Test para imputar una pieza a una orden de reparacion")
    @Test
    void imputarPiezaOrdenReparacionTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("BBBB")
                .nombre("manguito")
                .precio(2.5)
                .build();
        piezasRepository.save(pieza1);
        PiezasReparacion piezasReparacion1 = PiezasReparacion.builder()
                .pieza(pieza1)
                .cantidad(2)
                .ordenReparacion(ordenReparacion)
                .build();
        piezasReparacionRepository.save(piezasReparacion1);
        assertThat(piezasReparacion1.getId()).isGreaterThan(0);
        assertEquals(2, piezasReparacion1.getCantidad());
    }

    @DisplayName("Test para obtener una lista con todas la piezas imputadas a ordenes de reparacion")
    @Test
    void obtenerTodasPiezasReparacionTest() {
        Pieza pieza1 = Pieza.builder()
                .referencia("BBBB")
                .nombre("manguito")
                .precio(2.5)
                .build();
        piezasRepository.save(pieza1);
        PiezasReparacion piezasReparacion1 = PiezasReparacion.builder()
                .pieza(pieza1)
                .cantidad(2)
                .ordenReparacion(ordenReparacion)
                .build();
        piezasReparacionRepository.save(piezasReparacion1);
        List<PiezasReparacion> piezasReparacionEncontradas = piezasReparacionRepository.findAll();
        assertEquals(2, piezasReparacionEncontradas.size());
    }

    @DisplayName("Test para obtener una pieza reparacion por su id")
    @Test
    void obtenerPiezaReparacionPorIdTest() {
        PiezasReparacion piezaReparacionEncontrada = piezasReparacionRepository.findById(piezasReparacion.getId()).get();
        assertEquals(3, piezaReparacionEncontrada.getCantidad());
    }

    @DisplayName("Test para actualizar una pieza reparacion")
    @Test
    void actualizarPiezaReparacionTest() {
        PiezasReparacion piezaReparacionAActualizar = piezasReparacionRepository.findById(piezasReparacion.getId()).get();
        piezaReparacionAActualizar.setCantidad(6);
        PiezasReparacion piezaReparacionActualizada = piezasReparacionRepository.save(piezaReparacionAActualizar);
        assertEquals(6, piezaReparacionActualizada.getCantidad());
    }

    @DisplayName("Test para eliminar una pieza reparacion")
    @Test
    void eliminarPiezaReparacionTest() {
        piezasReparacionRepository.deleteById(piezasReparacion.getId());
        Optional<PiezasReparacion> piezasReparacionEliminada = piezasReparacionRepository.findById(piezasReparacion.getId());
        assertTrue(piezasReparacionEliminada.isEmpty());
    }
}
