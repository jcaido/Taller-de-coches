package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
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
}
