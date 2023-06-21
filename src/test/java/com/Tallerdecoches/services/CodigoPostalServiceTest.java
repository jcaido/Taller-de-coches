package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CodigoPostalServiceTest {

    @Mock
    private CodigoPostalRepository codigoPostalRespository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private CodigoPostalServiceImpl codigoPostalService;

    @DisplayName("Test para obtener una lista de codigos postales (service)")
    @Test
    void obtenerCodigosPostalesTodosTest() {
        when(codigoPostalRespository.findAll()).thenReturn(Datos.CODIGOS_POSTALES);
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
        when(codigoPostalRespository.findById(anyLong())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(modelMapper.map(any(), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        CodigoPostalDTO codigoPostalEncontrado = codigoPostalService.findById(Datos.CODIGO_POSTAL_1.getId());

        assertEquals("14920", codigoPostalEncontrado.getCodigo());
    }

    @DisplayName("Test para obtener un codigo postal por su id (service), codigo postal no encontrado")
    @Test
    void codigoPostalNoEncontradoTest() {
        when(codigoPostalRespository.findById(Datos.CODIGO_POSTAL_1.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findById(Datos.CODIGO_POSTAL_1.getId());
        });

        assertTrue(codigoPostalRespository.findById(Datos.CODIGO_POSTAL_DTO_1.getId()).isEmpty());
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (service)")
    @Test
    void obtenerCodigoPostalPorCodigoTest() {
        when(codigoPostalRespository.findByCodigo(anyString())).thenReturn(Optional.of(Datos.CODIGO_POSTAL_1));
        when(modelMapper.map(any(), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        CodigoPostalDTO codigoPostalEncontrado = codigoPostalService.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo());

        assertEquals("Aguilar de la Frontera", codigoPostalEncontrado.getLocalidad());
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (service), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorCodigoCodigoPostalNoEncontradoTest() {
        when(codigoPostalRespository.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findByCodigo(Datos.CODIGO_POSTAL_1.getCodigo());
        });

        assertTrue(codigoPostalRespository.findByCodigo(Datos.CODIGO_POSTAL_DTO_1.getCodigo()).isEmpty());
    }

    @DisplayName("Test para obtener una lista de codigos postales por Provincia")
    @Test
    void obtenerCodigosPostalesPorProvinciaTest() {
        when(codigoPostalRespository.findByProvincia(anyString())).thenReturn(Datos.CODIGOS_POSTALES_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_1), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_2), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_2);
        when(modelMapper.map(eq(Datos.CODIGO_POSTAL_3), eq(CodigoPostalDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_3);

        List<CodigoPostalDTO> codigosPostales = codigoPostalService.findByProvincia(Datos.CODIGO_POSTAL_1.getProvincia());

        assertEquals(3, codigosPostales.size());
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("14920")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getCodigo().equals("45111")));
        assertTrue(codigosPostales.stream().anyMatch(codigoPostalDTO -> codigoPostalDTO.getLocalidad().equals("Zuheros")));
    }
}