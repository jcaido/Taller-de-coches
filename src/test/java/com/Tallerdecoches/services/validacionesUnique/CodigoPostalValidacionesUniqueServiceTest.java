package com.Tallerdecoches.services.validacionesUnique;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CodigoPostalValidacionesUniqueServiceTest {

    @Mock
    private CodigoPostalRepository codigoPostalRepository;

    @Mock
    private CodigoPostalModificacionCambiosService codigoPostalModificacionCambiosService;

    @InjectMocks
    private CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService;

    private CodigoPostalDTO codigoPostalDTO;

    @Test
    @DisplayName("Test para validar la localidad en la modificacion del Codigo Postal")
    void validacionUniqueLocalidadTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(!codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)).willReturn(false);
        given(codigoPostalModificacionCambiosService.isLocalidadHaCambiado(codigoPostalDTO)).willReturn(true);
        given(codigoPostalRepository.existsByLocalidad(codigoPostalDTO.getLocalidad())).willReturn(true);
        given(codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)).willReturn(true);
        given(!codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo())).willReturn(false);

        //when
        boolean validacion = codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO);

        //then
        assertEquals(validacion, false);
    }

    @Test
    @DisplayName("Test para no validar la localidad en la modificacion del Codigo Postal")
    void NovalidacionUniqueLocalidadTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(!codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)).willReturn(true);

        //when
        boolean validacion = codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO);

        //then
        assertEquals(validacion, true);
    }

    @Test
    @DisplayName("Test para validar el codigo en la modificacion del Codigo Postal")
    void validacionUniqueCodigoTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)).willReturn(true);
        given(codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo())).willReturn(true);

        //when
        boolean validacion = codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO);

        //then
        assertEquals(validacion, false);
    }

    @Test
    @DisplayName("Test para no validar el codigo en la modificacion del Codigo Postal")
    void NovalidacionUniqueCodigoTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");

        given(codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)).willReturn(false);

        //when
        boolean validacion = codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO);

        //then
        assertEquals(validacion, true);
    }
}
