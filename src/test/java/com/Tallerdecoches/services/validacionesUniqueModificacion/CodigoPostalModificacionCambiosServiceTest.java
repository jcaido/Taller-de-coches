package com.Tallerdecoches.services.validacionesUniqueModificacion;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalModificacionCambiosService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class CodigoPostalModificacionCambiosServiceTest {

    @Mock
    private CodigoPostalRepository codigoPostalRepository;

    @InjectMocks
    private CodigoPostalModificacionCambiosService codigoPostalModificacioCambiosService;

    private CodigoPostalDTO codigoPostalDTO;

    @Test
    @DisplayName("Test para comprobar si el codigo del Codigo Postal ha cambiado")
    void isCodigoHaCambiadoTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");
        CodigoPostal codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findById(codigoPostal.getId())).willReturn(Optional.of(codigoPostal));

        //when
        boolean isHaCambiado = codigoPostalModificacioCambiosService.isCodigoHaCambiado(codigoPostalDTO);

        //then
        assertEquals(isHaCambiado, false);

    }

    @Test
    @DisplayName("Test para comprobar si la localidad del Codigo Postal ha cambiado")
    void isLocalidadHaCambiadoTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");
        CodigoPostal codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findById(codigoPostal.getId())).willReturn(Optional.of(codigoPostal));

        //when
        boolean isHaCambiado = codigoPostalModificacioCambiosService.isLocalidadHaCambiado(codigoPostalDTO);

        //then
        assertEquals(isHaCambiado, false);

    }
}
