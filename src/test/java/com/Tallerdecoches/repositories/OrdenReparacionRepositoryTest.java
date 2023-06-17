package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;

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
}
