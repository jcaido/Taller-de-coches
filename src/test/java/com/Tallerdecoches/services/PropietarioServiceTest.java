package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.services.propietario.PropietarioModificacionCambiosService;
import com.Tallerdecoches.services.propietario.PropietarioServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropietarioServiceTest {

    @Mock
    private PropietarioRepository propietarioRepository;

    @Mock
    private CodigoPostalRepository codigoPostalRepository;

    @Mock
    private PropietarioModificacionCambiosService propietarioModificacionCambiosService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PropietarioServiceImpl propietarioService;

    @DisplayName("Test para guardar un propietario (service)")
    @Test
    void guardarPropietarioTest() {
        when(modelMapper.map(eq(Datos.PROPIETARIO_CREAR_DTO_1), eq(Propietario.class))).thenReturn(Datos.PROPIETARIO_1);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioDTO.class))).thenReturn(Datos.PROPIETARIO_DTO_1);
        when(codigoPostalRepository.findById(anyLong())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(propietarioModificacionCambiosService.validacionCodigoPostal(anyLong())).thenReturn(true);
        when(propietarioRepository.save(Datos.PROPIETARIO_1)).thenReturn(Datos.PROPIETARIO_1);

        PropietarioDTO propietarioGuardado = propietarioService.crearPropietario(Datos.PROPIETARIO_CREAR_DTO_1, Datos.CODIGO_POSTAL_1.getId());

        assertEquals("Antonio", propietarioGuardado.getNombre());
    }

    @DisplayName("Test para guardar un propietario (service), el dni del propietario ya existe")
    @Test
    void guardarPropietarioDniPropietarioYaExisteTest() {
        when(propietarioRepository.existsByDni(Datos.PROPIETARIO_CREAR_DTO_1.getDni())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            PropietarioDTO propietarioGuardado = propietarioService.crearPropietario(Datos.PROPIETARIO_CREAR_DTO_1, Datos.CODIGO_POSTAL_1.getId());
        });

        assertEquals("409 CONFLICT \"El DNI del propietario ya existe\"", exception.getMessage());
    }

    @DisplayName("Test para guardar un propietario (service), el codigo postal asoociado no existe")
    @Test
    void guardarPropietarioCodigoPostalAsociadoNoExisteTest() {
        when(propietarioRepository.existsByDni(Datos.PROPIETARIO_CREAR_DTO_1.getDni())).thenReturn(false);
        when(propietarioModificacionCambiosService.validacionCodigoPostal(anyLong())).thenReturn(false);

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            PropietarioDTO propietarioGuardado = propietarioService.crearPropietario(Datos.PROPIETARIO_CREAR_DTO_1, Datos.CODIGO_POSTAL_1.getId());
        });

        assertEquals("409 CONFLICT \"El codigo Postal asociado al propietario no existe\"", exception.getMessage());
    }

    @DisplayName("Test para obtener una lista con todos los propietarios (service)")
    @Test
    void obtenerPropietariosTodosTest() {
        when(propietarioRepository.findAll()).thenReturn(Datos.PROPIETARIOS);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);
        when(modelMapper.map(eq(Datos.PROPIETARIO_2), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_2);

        List<PropietarioBusquedasDTO> propietarios = propietarioService.findAll();

        assertEquals(2, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getNombre().equals("Antonio")));
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getDomicilio().equals("Calle San Anton, 54")));
    }

    @DisplayName("Test para obtener una lista parcial con todos los propietarios (service)")
    @Test
    void obtenerPropietariosTodosParcialTest() {
        when(propietarioRepository.findAll()).thenReturn(Datos.PROPIETARIOS);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasParcialDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_1);
        when(modelMapper.map(eq(Datos.PROPIETARIO_2), eq(PropietarioBusquedasParcialDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_2);

        List<PropietarioBusquedasParcialDTO> propietarios = propietarioService.findAllPartial();

        assertEquals(2, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getNombre().equals("Antonio")));
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getDni().equals("55000900B")));
    }

    @DisplayName("Test para obtener un propietario por su id (service)")
    @Test
    void obtenerPropietarioPorIdTest() {
        when(propietarioRepository.findById(anyLong())).thenReturn(Optional.of(Datos.PROPIETARIO_1));
        when(modelMapper.map(any(), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        PropietarioBusquedasDTO propietarioEncontrado = propietarioService.findById(Datos.PROPIETARIO_1.getId());

        assertEquals("44000888T", propietarioEncontrado.getDni());
    }

    @DisplayName("Test para obtener un propietario por su id (service), propietario no encontrado")
    @Test
    void obtenerPropietarioPorIdPropietarioNoEncontradoTest() {
        when(propietarioRepository.findById(Datos.PROPIETARIO_1.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            propietarioService.findById(Datos.PROPIETARIO_1.getId());
        });

        assertTrue(propietarioRepository.findById(Datos.PROPIETARIO_BUSQUEDAS_DTO_1.getId()).isEmpty());
        assertEquals("Propietario con id " + Datos.PROPIETARIO_BUSQUEDAS_DTO_1.getId() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test para obtener un propietario por dni (service)")
    @Test
    void obtenerPropietarioPorDniTest() {
        when(propietarioRepository.findByDni(anyString())).thenReturn(Optional.of(Datos.PROPIETARIO_1));
        when(modelMapper.map(any(), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        PropietarioBusquedasDTO propietarioEncontrado = propietarioService.findByDni(Datos.PROPIETARIO_1.getDni());

        assertEquals("44000888T", propietarioEncontrado.getDni());
    }

    @DisplayName("Test para obtener un propietario  por su dni (service), propietario no encontrado")
    @Test
    void obtenerPropietarioPorDniPropietarioNoEncontradoTest() {
        when(propietarioRepository.findByDni(Datos.PROPIETARIO_1.getDni())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            PropietarioBusquedasDTO propietarioEncontrado = propietarioService.findByDni(Datos.PROPIETARIO_1.getDni());
        });

        assertEquals("Propietario con dni " + Datos.PROPIETARIO_1.getDni() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test  para obtener una lista de propietarios por nombre (service)")
    @Test
    void obtenerPropietariosPorNombreTest() {
        when(propietarioRepository.findByNombre(anyString())).thenReturn(Datos.PROPIETARIOS_1);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);
        when(modelMapper.map(eq(Datos.PROPIETARIO_3), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_3);

        List<PropietarioBusquedasDTO> propietarios = propietarioService.findByNombre(Datos.PROPIETARIO_1.getNombre());

        assertEquals(2, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getDni().equals("44000888T")));
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getDni().equals("22777555K")));
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getDomicilio().equals("Calle Vinuesa, 87")));
    }

    @DisplayName("Test para obtener una lista de propietarios por primer apellido (service)")
    @Test
    void obtenerPropietariosPorPrimerApellidoTest() {
        when(propietarioRepository.findByPrimerApellido(anyString())).thenReturn(Datos.PROPIETARIOS_2);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        List<PropietarioBusquedasDTO> propietarios = propietarioService.findByPrimerApellido(Datos.PROPIETARIO_1.getPrimerApellido());

        assertEquals(1, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getPrimerApellido().equals("Perez")));
    }

    @DisplayName("Test para obtener una lista parcial de propietarios por primer apellido (service)")
    @Test
    void obtenerPropietariosParcialPorPrimerApellidoTest() {
        when(propietarioRepository.findByPrimerApellido(anyString())).thenReturn(Datos.PROPIETARIOS_2);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasParcialDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_1);

        List<PropietarioBusquedasParcialDTO> propietarios = propietarioService.findByPrimerApellidoPartial(Datos.PROPIETARIO_1.getPrimerApellido());

        assertEquals(1, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getPrimerApellido().equals("Perez")));
    }

    @DisplayName("Test para  obtener una lista de propietarios por nombre completo (service)")
    @Test
    void obtenerPropietariosPorNombreCompletoTest() {
        when(propietarioRepository.findByNombreAndPrimerApellidoAndSegundoApellido(anyString(), anyString(), anyString())).thenReturn(Datos.PROPIETARIOS_2);
        when(modelMapper.map(eq(Datos.PROPIETARIO_1), eq(PropietarioBusquedasDTO.class))).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        List<PropietarioBusquedasDTO> propietarios = propietarioService.findByNombreAndPrimerApellidoAndSegundoApellido(Datos.PROPIETARIO_1.getNombre(), Datos.PROPIETARIO_1.getPrimerApellido(), Datos.PROPIETARIO_1.getSegundoApellido());

        assertEquals(1, propietarios.size());
        assertTrue(propietarios.stream().anyMatch(propietario -> propietario.getPrimerApellido().equals("Perez")));
    }

    @DisplayName("Test para obtener una lista de propietarios por codigo postal (service)")
    @Test
    void obtenerPropietariosPorCodigoPostalTest() {
        when(codigoPostalRepository.findById(anyLong())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        Datos.CODIGO_POSTAL_1.setPropietarios(List.of(Datos.PROPIETARIO_1, Datos.PROPIETARIO_2));

        List<PropietarioBusquedasDTO> propietarios = propietarioService.ObtenerPropietariosPorCodigoPostal(Datos.CODIGO_POSTAL_1.getId());

        assertEquals(2, propietarios.size());
    }

    @DisplayName("Test para obtener una lista de propietarios por codigo postal (service), codigo postal no encontrado")
    @Test
    void obtenerPropietariosPorCodigoPostalCodigoPostalNoEncontradoTest() {
        when(codigoPostalRepository.findById(anyLong())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            List<PropietarioBusquedasDTO> propietarios = propietarioService.ObtenerPropietariosPorCodigoPostal(Datos.CODIGO_POSTAL_1.getId());
        });

        assertEquals("Codigo Postal con id " + Datos.CODIGO_POSTAL_1.getId() + " no se encuentra", exception.getMessage());
    }
}

