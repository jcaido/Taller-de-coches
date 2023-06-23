package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalServiceImpl;
import com.Tallerdecoches.services.propietario.PropietarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CodigoPostalServiceTest {

    @Mock
    private CodigoPostalRepository codigoPostalRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PropietarioService propietarioService;

    @InjectMocks
    private CodigoPostalServiceImpl codigoPostalService;

    @DisplayName("Test para guardar un codigo postal (service)")
    @Test
    void guardarCodigoPostalTest() {
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_CREAR_DTO_1), eq(CodigoPostal.class))).thenReturn(Datos.CODIGO_POSTAL_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_1), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);
        when(codigoPostalRepository.save(Datos.CODIGO_POSTAL_1)).thenReturn(Datos.CODIGO_POSTAL_1);

        CodigoPostalDTO codigoPostalGuardado = codigoPostalService.crearCodigoPostal(Datos.CODIGO_POSTAL_CREAR_DTO_1);

        assertEquals("14920", codigoPostalGuardado.getCodigo());
    }

    @DisplayName("Test para guardar un codigo postal (service), el numero del codigo postal ya existe")
    @Test
    void guardarCodigoPostalNumeroCodigoPostalYaExisteTest() {
        when(codigoPostalRepository.existsByCodigo(Datos.CODIGO_POSTAL_CREAR_DTO_1.getCodigo())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, ()-> {
            codigoPostalService.crearCodigoPostal(Datos.CODIGO_POSTAL_CREAR_DTO_1);
        });

        assertEquals("409 CONFLICT \"El numero del codigo postal ya existe\"", exception.getMessage());
    }

    @DisplayName("Test para guardar un codigo postal (service), la localidad ya existe")
    @Test
    void guardarCodigoPostalLocalidadYaExisteTest() {
        when(codigoPostalRepository.existsByLocalidad(Datos.CODIGO_POSTAL_CREAR_DTO_1.getLocalidad())).thenReturn(true);

        Exception exception = assertThrows(ResponseStatusException.class, ()-> {
            codigoPostalService.crearCodigoPostal(Datos.CODIGO_POSTAL_CREAR_DTO_1);
        });

        assertEquals("409 CONFLICT \"la localidad del codigo postal ya existe\"", exception.getMessage());
    }

    @DisplayName("Test para obtener una lista de codigos postales (service)")
    @Test
    void obtenerCodigosPostalesTodosTest() {
        when(codigoPostalRepository.findAll()).thenReturn(Datos.CODIGOS_POSTALES);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_1), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_2), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_2);

        List<CodigoPostalDTO> codigosPostales = codigoPostalService.findAll();

        assertEquals(2, codigosPostales.size());
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("14920")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("45111")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getLocalidad().equals("Zuheros")));
    }

    @DisplayName("Test para obtener un codigo postal por su id (service)")
    @Test
    void obtenerCodigoPostalPorIdTest() {
        when(codigoPostalRepository.findById(anyLong())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(modelMapper.map(any(), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        CodigoPostalDTO codigoPostalEncontrado = codigoPostalService.findById(Datos.CODIGO_POSTAL_1.getId());

        assertEquals("14920", codigoPostalEncontrado.getCodigo());
    }

    @DisplayName("Test para obtener un codigo postal por su id (service), codigo postal no encontrado")
    @Test
    void codigoPostalNoEncontradoTest() {
        when(codigoPostalRepository.findById(Datos.CODIGO_POSTAL_1.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findById(Datos.CODIGO_POSTAL_1.getId());
        });

        assertTrue(codigoPostalRepository.findById(Datos.CODIGO_POSTAL_DTO_1.getId()).isEmpty());
        assertEquals("Codigo Postal con id " + Datos.CODIGO_POSTAL_DTO_1.getId() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (service)")
    @Test
    void obtenerCodigoPostalPorCodigoTest() {
        when(codigoPostalRepository.findByCodigo(anyString())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(modelMapper.map(any(), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        CodigoPostalDTO codigoPostalEncontrado = codigoPostalService.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo());

        assertEquals("Aguilar de la Frontera", codigoPostalEncontrado.getLocalidad());
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (service), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorCodigoCodigoPostalNoEncontradoTest() {
        when(codigoPostalRepository.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo());
        });

        assertTrue(codigoPostalRepository.findByCodigo(Datos.CODIGO_POSTAL_DTO_1.getCodigo()).isEmpty());
        assertEquals("Codigo Postal con codigo " + Datos.CODIGO_POSTAL_DTO_1.getCodigo() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test para obtener una lista de codigos postales por Provincia (service)")
    @Test
    void obtenerCodigosPostalesPorProvinciaTest() {
        when(codigoPostalRepository.findByProvincia(anyString())).thenReturn(Datos.CODIGOS_POSTALES_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_1), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_2), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_2);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_3), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_3);

        List<CodigoPostalDTO> codigosPostales = codigoPostalService.findByProvincia(Datos.CODIGO_POSTAL_1.getProvincia());

        assertEquals(3, codigosPostales.size());
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("14920")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("45111")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getLocalidad().equals("Zuheros")));
    }

    @DisplayName("Test para obtener un codigo postal por su localidad (service)")
    @Test
    void obtenerCodigoPostalPorLocalidadTest() {
        when(codigoPostalRepository.findByLocalidad(anyString())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(modelMapper.map(any(), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        CodigoPostalDTO codigoPostalEncontrado = codigoPostalService.findByLocalidad(Datos.CODIGO_POSTAL_1.getLocalidad());

        assertEquals("Aguilar de la Frontera", codigoPostalEncontrado.getLocalidad());
    }

    @DisplayName("Test para obtener un codigo postal por su localidad (service), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorLocalidadCodigoPostalNoEncontradoTest() {
        when(codigoPostalRepository.findByLocalidad(Datos.CODIGO_POSTAL_1.getLocalidad())).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findByLocalidad(Datos.CODIGO_POSTAL_1.getLocalidad());
        });

        assertTrue(codigoPostalRepository.findByLocalidad(Datos.CODIGO_POSTAL_DTO_1.getLocalidad()).isEmpty());
        assertEquals("Codigo Postal con localidad " + Datos.CODIGO_POSTAL_DTO_1.getLocalidad() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test para eliminar un codigo postal (service)")
    @Test
    void eliminarCodigoPostalTest() {

        when(codigoPostalRepository.existsById(Datos.CODIGO_POSTAL_1.getId())).thenReturn(true);
        when(propietarioService.obtenerPropietariosPorCodigoPostalHQL(Datos.CODIGO_POSTAL_1.getId())).thenReturn(Collections.emptyList());
        willDoNothing().given(codigoPostalRepository).deleteById(Datos.CODIGO_POSTAL_1.getId());

        codigoPostalService.deleteById(Datos.CODIGO_POSTAL_1.getId());

        verify(codigoPostalRepository, times(1)).deleteById(Datos.CODIGO_POSTAL_1.getId());
    }

    @DisplayName("Test para eliminar un codigo postal (service), codigo postal no encontrado")
    @Test
    void eliminarCodigoPostalCodigoPostalNoEncontradoTest() {
        when(codigoPostalRepository.existsById(Datos.CODIGO_POSTAL_1.getId())).thenReturn(false);

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.deleteById(Datos.CODIGO_POSTAL_1.getId());
        });

        assertEquals("Codigo Postal con id " + Datos.CODIGO_POSTAL_DTO_1.getId() + " no se encuentra", exception.getMessage());
    }

    @DisplayName("Test para eliminar un codigo postal(service), existen propietarios relacionados")
    @Test
    void eliminarCodigoPostalExistenPropietariosRelacionadosTest() {
        when(codigoPostalRepository.existsById(Datos.CODIGO_POSTAL_1.getId())).thenReturn(true);
        when(propietarioService.obtenerPropietariosPorCodigoPostalHQL(Datos.CODIGO_POSTAL_1.getId())).thenReturn(List.of(Datos.PROPIETARIO_BUSQUEDAS_1, Datos.PROPIETARIO_BUSQUEDAS_2));

        Exception exception = assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.deleteById(Datos.CODIGO_POSTAL_1.getId());
        });

        assertEquals("409 CONFLICT \"Existen propietarios relacionados con ese codigo postal\"", exception.getMessage());
    }
}
