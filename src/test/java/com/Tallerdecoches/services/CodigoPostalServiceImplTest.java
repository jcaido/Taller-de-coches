package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.services.validacionesUnique.CodigoPostalValidacionesUniqueService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CodigoPostalServiceImplTest {

    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PropietarioService propietarioService;
    @Mock
    private CodigoPostalRepository codigoPostalRepository;
    @Mock
    private CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService;
    @InjectMocks
    private CodigoPostalServiceImpl codigoPostalService;

    private CodigoPostal codigoPostal;
    private CodigoPostalDTO codigoPostalDTO;

    private Propietario propietario;

    @Test
    @DisplayName("Test para guardar un Codigo Postal")
    void crearCodigoPostalTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(modelMapper.map(codigoPostalDTO, CodigoPostal.class)).willReturn(codigoPostal);
        given(codigoPostalRepository.save(codigoPostal)).willReturn(codigoPostal);

        //when
        ResponseEntity<CodigoPostalDTO> codigoPostalGuardado = codigoPostalService.crearCodigoPostal(codigoPostalDTO);

        //then
        assertEquals(codigoPostalGuardado.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    @DisplayName("Test para guardar un Codigo Postal, exception id debe ser null")
    void crearCodigoPostalExceptionIdNotNullTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");

        //when
        assertThrows(BadRequestCreacionException.class, () -> {
            codigoPostalService.crearCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para guardar un Codigo Postal, exception codigo ya existe")
    void crearCodigoPostalExceptionCodigoYaExisteTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo())).willReturn(true);

        //when
        assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.crearCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para guardar un Codigo Postal, exception localidad ya existe")
    void crearCodigoPostalExceptionLocalidadYaExisteTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsByLocalidad(codigoPostalDTO.getLocalidad())).willReturn(true);

        //when
        assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.crearCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para comprobar si el codigo de un Codigo Postal ya existe")
    void existsByCodigoTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo())).willReturn(true);

        //when
        boolean isExist = codigoPostalService.existsByCodigo(codigoPostalDTO.getCodigo());

        //then
        assertEquals(isExist, true);
    }

    @Test
    @DisplayName("Test para comprobar si la localidad de un Codigo Postal ya existe")
    void existsByLocalidadTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsByLocalidad(codigoPostalDTO.getLocalidad())).willReturn(true);

        //when
        boolean isExist = codigoPostalService.existsByLocalidad(codigoPostalDTO.getLocalidad());

        //then
        assertEquals(isExist, true);
    }

    @Test
    @DisplayName("Test para obtener una lista con todos los Codigos Postales")
    void findAllTest() {
        //given
        codigoPostal = new CodigoPostal(null, "99100", "Chipiona", "Cadiz");
        CodigoPostal codigoPostal1 = new CodigoPostal(null, "10111", "Arriate", "Cadiz");
        given(codigoPostalRepository.findAll()).willReturn(List.of(codigoPostal, codigoPostal1));

        //when
        List<CodigoPostalDTO> codigosPostales = codigoPostalService.findAll();

        //then
        assertThat(codigosPostales).isNotNull();
        assertEquals(codigosPostales.size(), 2);
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su id")
    void findByIdTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findById(codigoPostal.getId())).willReturn(Optional.of(codigoPostal));

        //when
        ResponseEntity<CodigoPostalDTO> codigoPostalEncontrado = codigoPostalService.findById(codigoPostal.getId());

        //then
        assertEquals(codigoPostalEncontrado.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su id, exception codigo postal no encontrado")
    void findByIdlResourceNotFoundExceptionTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findById(codigoPostal.getId())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findById(codigoPostal.getId());
        });

        //then
        verify(codigoPostalRepository, times(1)).findById(codigoPostal.getId());
        assertThat(codigoPostalRepository.findById(codigoPostal.getId()).isEmpty());
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su codigo")
    void findByCodigoTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findByCodigo(codigoPostal.getCodigo())).willReturn(Optional.of(codigoPostal));

        //when
        ResponseEntity<CodigoPostalDTO> codigoPostalEncontrado = codigoPostalService.findByCodigo(codigoPostal.getCodigo());

        //then
        assertEquals(codigoPostalEncontrado.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su codigo, exception codigo postal no encontrado")
    void findByCodigolResourceNotFoundExceptionTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findByCodigo(codigoPostal.getCodigo())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findByCodigo(codigoPostal.getCodigo());
        });

        //then
        verify(codigoPostalRepository, times(1)).findByCodigo(codigoPostal.getCodigo());
        assertThat(codigoPostalRepository.findByCodigo(codigoPostal.getCodigo()).isEmpty());
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su localidad")
    void findByLocalidadTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findByLocalidad(codigoPostal.getLocalidad())).willReturn(Optional.of(codigoPostal));

        //when
        ResponseEntity<CodigoPostalDTO> codigoPostalEncontrado = codigoPostalService.findByLocalidad(codigoPostal.getLocalidad());

        //then
        assertEquals(codigoPostalEncontrado.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test para obtener un Codigo Postal por su localidad, exception codigo postal no encontrado")
    void findByLocalidadlResourceNotFoundExceptionTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.findByLocalidad(codigoPostal.getLocalidad())).willReturn(Optional.empty());

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.findByLocalidad(codigoPostal.getLocalidad());
        });

        //then
        verify(codigoPostalRepository, times(1)).findByLocalidad(codigoPostal.getLocalidad());
        assertThat(codigoPostalRepository.findByLocalidad(codigoPostal.getLocalidad()).isEmpty());
    }

    @Test
    @DisplayName("Test para obtener una lista con todos los Codigos Postales por Provincia")
    void findByProvinciaTest() {
        //given
        codigoPostal = new CodigoPostal(null, "99100", "Chipiona", "Cadiz");
        CodigoPostal codigoPostal1 = new CodigoPostal(null, "10111", "Arriate", "Cadiz");
        String provincia = "Cadiz";
        given(codigoPostalRepository.findByProvincia(provincia)).willReturn(List.of(codigoPostal, codigoPostal1));

        //when
        List<CodigoPostalDTO> codigosPostales = codigoPostalService.findByProvincia(provincia);

        //then
        assertThat(codigosPostales).isNotNull();
        assertEquals(codigosPostales.size(), 2);
    }

    @Test
    @DisplayName("Test para eliminar un Codigo Postal por su id")
    void deleteByIdTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsById(codigoPostal.getId())).willReturn(true);
        given(propietarioService.obtenerPropietariosPorCodigoPostalHQL(codigoPostal.getId())).willReturn(Collections.emptyList());
        willDoNothing().given(codigoPostalRepository).deleteById(codigoPostal.getId());

        //when
        codigoPostalService.deleteById(codigoPostal.getId());

        //then
        verify(codigoPostalRepository, times(1)).deleteById(codigoPostal.getId());
    }

    @Test
    @DisplayName("Test para eliminar un Codigo Postal por su id, exception codigo postal no encontrado")
    void deleteByIdResourceNotFoundExceptionTest() {
        //given
        long codigoPostalId = 1L;
        given(codigoPostalRepository.existsById(codigoPostalId)).willReturn(false);

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.deleteById(codigoPostalId);
        });

        //then
        verify(codigoPostalRepository, never()).deleteById(codigoPostalId);
    }

    @Test
    @DisplayName("Test para eliminar un Codigo Postal por su id, exception existen  propietarios relacionados")
    void deleteByIdResponseStatusExceptionTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        PropietarioDTO propietario = new PropietarioDTO(1L, "Cristobal", "Rosa", "Arjona", "34019853R", "Pasaje San Vicente 2");
        given(codigoPostalRepository.existsById(codigoPostal.getId())).willReturn(true);
        given(propietarioService.obtenerPropietariosPorCodigoPostalHQL(codigoPostal.getId())).willReturn(List.of(propietario));

        //when
        assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.deleteById(codigoPostal.getId());
        });

        //then
        verify(codigoPostalRepository, never()).deleteById(codigoPostal.getId());
    }

    @Test
    @DisplayName("Test para modificar un Codigo Postal")
    void modificarCodigoPostalTest() {
        //given
        codigoPostal = new CodigoPostal(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.save(codigoPostal)).willReturn(codigoPostal);

        CodigoPostalDTO codigoPostalDTO = new CodigoPostalDTO();
        codigoPostalDTO.setId(1l);
        codigoPostalDTO.setCodigo("99100");
        codigoPostalDTO.setLocalidad("Chipiona");
        codigoPostalDTO.setProvincia("Cadiz");

        given(codigoPostalRepository.existsById(codigoPostalDTO.getId())).willReturn(true);
        given(codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO)).willReturn(true);
        given(codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO)).willReturn(true);
        given(codigoPostalRepository.findById(codigoPostalDTO.getId())).willReturn(Optional.of(codigoPostal));

        //when
        ResponseEntity<CodigoPostalDTO> codigoPostalModificado = codigoPostalService.modificarCodigoPostal(codigoPostalDTO);

        //then
        assertEquals(codigoPostalModificado.getStatusCode(), HttpStatus.OK);
    }

    @Test
    @DisplayName("Test para modificar un Codigo Postal, exception id no debe ser null")
    void modificarCodigoPostalExceptionIdNullTest() {
        //given
        codigoPostalDTO = new CodigoPostalDTO(null, "99100", "Chipiona", "Cadiz");

        //when
        assertThrows(BadRequestModificacionException.class, () -> {
            codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para modificar un Codigo Postal, exception codigo postal no encontrado")
    void ModificarCodigoPostalResourceNotFoundExceptionTest() {
        //given
        CodigoPostalDTO codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");

        //when
        assertThrows(ResourceNotFoundException.class, () -> {
            codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para modificar un Codigo Postal, exception la localidad del Codigo Postal ya existe")
    void ModificarCodigoPostalResponseStatusExceptionLocalidad() {
        //given
        CodigoPostalDTO codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsById(codigoPostalDTO.getId())).willReturn(true);
        given(codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO)).willReturn(false);

        //when
        assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }

    @Test
    @DisplayName("Test para modificar un Codigo Postal, exception el codigo del Codigo Postal ya existe")
    void ModificarCodigoPostalResponseStatusExceptionCodigo() {
        //given
        CodigoPostalDTO codigoPostalDTO = new CodigoPostalDTO(1L, "99100", "Chipiona", "Cadiz");
        given(codigoPostalRepository.existsById(codigoPostalDTO.getId())).willReturn(true);
        given(codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO)).willReturn(true);
        given(codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO)).willReturn(false);

        //when
        assertThrows(ResponseStatusException.class, () -> {
            codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
        });

        //then
        verify(codigoPostalRepository, never()).save(any(CodigoPostal.class));
    }
}