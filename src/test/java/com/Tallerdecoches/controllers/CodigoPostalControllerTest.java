package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.repositories.Datos;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

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

}
