package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.propietario.PropietarioCrearDTO;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.services.propietario.PropietarioService;
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
public class PropietarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PropietarioService propietarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("Test para guardar un propietario (controller) ")
    @Test
    void guardarPropietarioTest() throws Exception {
        when(propietarioService.crearPropietario(any(PropietarioCrearDTO.class), anyLong())).thenReturn(Datos.PROPIETARIO_DTO_1);

        ResultActions response = mockMvc.perform(post("/api/propietarios/{id_codigoPostal}", Datos.CODIGO_POSTAL_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.PROPIETARIO_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(Datos.PROPIETARIO_DTO_1.getNombre())))
                .andExpect(jsonPath("$.primerApellido", is(Datos.PROPIETARIO_DTO_1.getPrimerApellido())))
                .andExpect(jsonPath("$.segundoApellido", is(Datos.PROPIETARIO_DTO_1.getSegundoApellido())))
                .andExpect(jsonPath("$.dni", is(Datos.PROPIETARIO_DTO_1.getDni())))
                .andExpect(jsonPath("$.domicilio", is(Datos.PROPIETARIO_DTO_1.getDomicilio())));
    }

    @DisplayName("Test para guardar un propietario (controller), DNI del propietario ya existe")
    @Test
    void guardarPropietarioDNIYaExisteTest() throws Exception {
        when(propietarioService.crearPropietario(any(PropietarioCrearDTO.class), anyLong())).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El DNI del propietario ya existe"));

        ResultActions response = mockMvc.perform(post("/api/propietarios/{id_codigoPostal}", Datos.CODIGO_POSTAL_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.PROPIETARIO_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", is("409 CONFLICT \"El DNI del propietario ya existe\"")));
    }
}
