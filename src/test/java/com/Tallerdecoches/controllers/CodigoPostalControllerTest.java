package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CodigoPostalControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CodigoPostalService codigoPostalService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test para guardar un codigo postal (controller)")
    @Test
    void crearCodigoPostalTest() throws Exception {

        when(codigoPostalService.crearCodigoPostal(any(CodigoPostalCrearDTO.class))).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        ResultActions response = mockMvc.perform(post("/api/codigosPostales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.CODIGO_POSTAL_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.codigo", is(Datos.CODIGO_POSTAL_DTO_1.getCodigo())))
                .andExpect(jsonPath("$.localidad", is(Datos.CODIGO_POSTAL_DTO_1.getLocalidad())))
                .andExpect(jsonPath("$.provincia", is(Datos.CODIGO_POSTAL_DTO_1.getProvincia())));
    }

    @DisplayName("Test para guardar un codigo postal (controller), numero del codigo postal ya existe")
    @Test
    void crearCodigoPostalNumeroCodigoPostalYaExisteTest() throws Exception {
        when(codigoPostalService.crearCodigoPostal(any(CodigoPostalCrearDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El numero del codigo postal ya existe"));

        ResultActions response = mockMvc.perform(post("/api/codigosPostales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.CODIGO_POSTAL_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", is("409 CONFLICT \"El numero del codigo postal ya existe\"")));
    }

    @DisplayName("Test para guardar un codigo postal (controller), localidad del codigo postal ya existe")
    @Test
    void crearCodigoPostalLocalidadCodigoPostalYaExisteTest() throws Exception {
        when(codigoPostalService.crearCodigoPostal(any(CodigoPostalCrearDTO.class))).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "la localidad del codigo postal ya existe"));

        ResultActions response = mockMvc.perform(post("/api/codigosPostales")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.CODIGO_POSTAL_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", is("409 CONFLICT \"la localidad del codigo postal ya existe\"")));
    }

    @DisplayName("Test para obtener una lista con todos los codigos postales (controller)")
    @Test
    void obtenerEmpleadosTodosTest() throws Exception {
        when(codigoPostalService.findAll()).thenReturn(Datos.CODIGOS_POSTALES_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.CODIGOS_POSTALES_DTO_1.size())));
    }

    @DisplayName("Test para obtener un codigo postal por id (controller)")
    @Test
    void obtenerCodigoPostalPorIdTest() throws Exception {
        when(codigoPostalService.findById(1L)).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(Datos.CODIGO_POSTAL_DTO_1.getCodigo())));
    }

    @DisplayName("Test para obtener un codigo postal por id (controller), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorIdCodigoPostalNoEncontradoTest() throws Exception {
        when(codigoPostalService.findById(1L)).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (controller)")
    @Test
    void obtenerCodigoPostalPorCodigoTest() throws Exception {
        when(codigoPostalService.findByCodigo("14920")).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/codigo/{codigo}", "14920"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(Datos.CODIGO_POSTAL_DTO_1.getCodigo())));
    }

    @DisplayName("Test para obtener un codigo postal por su codigo (controller), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorCodigoCodigoPostalNoEncontradoTest() throws Exception {
        when(codigoPostalService.findByCodigo("14920")).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/codigo/{codigo}", "14920"));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test para obtener una lista de codigos postales por provincia (controller)")
    @Test
    void obtenerCodigosPostalesPorProvinciaTest() throws Exception {
        when(codigoPostalService.findByProvincia("Cordoba")).thenReturn(Datos.CODIGOS_POSTALES_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/provincia/{provincia}", "Cordoba"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.CODIGOS_POSTALES_DTO_1.size())));
    }

    @DisplayName("Test para obtener un codigo postal por su localidad (controller)")
    @Test
    void obtenerCodigoPostalPorLocalidadTest() throws Exception {
        when(codigoPostalService.findByLocalidad("Aguilar de la Frontera")).thenReturn(Datos.CODIGO_POSTAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/localidad/{localidad}", "Aguilar de la Frontera"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo", is(Datos.CODIGO_POSTAL_DTO_1.getCodigo())));
    }

    @DisplayName("Test para obtener un codigo postal por su localidad (controller), codigo postal no encontrado")
    @Test
    void obtenerCodigoPostalPorLocalidadCodigoPostalNoEncontradoTest() throws Exception {
        when(codigoPostalService.findByLocalidad("Aguilar de la Frontera")).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/codigosPostales/localidad/{localidad}", "Aguilar de la Frontera"));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

}
