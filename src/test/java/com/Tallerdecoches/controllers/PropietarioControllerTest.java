package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.propietario.PropietarioCrearDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
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

    @DisplayName("Test para guardar un propietario (controller), codigo postal asociado no existe")
    @Test
    void guardarPropietarioCodigoPostalAsociadoNoExisteTest() throws Exception {
        when(propietarioService.crearPropietario(any(PropietarioCrearDTO.class), anyLong())).thenThrow(new ResponseStatusException(HttpStatus.CONFLICT, "El codigo Postal asociado al propietario no existe"));

        ResultActions response = mockMvc.perform(post("/api/propietarios/{id_codigoPostal}", Datos.CODIGO_POSTAL_1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.PROPIETARIO_CREAR_DTO_1)));

        response.andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.mensaje", is("409 CONFLICT \"El codigo Postal asociado al propietario no existe\"")));
    }

    @DisplayName("Test para obtener una lista con todos los propietarios (controller)")
    @Test
    void obtenerPropietariosTodosTest() throws Exception {
        when(propietarioService.findAll()).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista parcial con todos los propietarios (controller)")
    @Test
    void obtenerPropietariosParcialTodosTest() throws Exception {
        when(propietarioService.findAllPartial()).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/parcial"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1.size())));
    }

    @DisplayName("Test para obtener un propietario por su id (controller)")
    @Test
    void obtenerPropietarioPorIdTest() throws Exception {
        when(propietarioService.findById(anyLong())).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(Datos.PROPIETARIO_BUSQUEDAS_DTO_1.getNombre())));
    }

    @DisplayName("Test para obtener un propietario por su id (controller), propietario no encontrado")
    @Test
    void obtenerPropietarioPorIdPropietarioNoEncontradoTest() throws Exception {
        when(propietarioService.findById(anyLong())).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/propietarios/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test para obtener un propietario por su dni (controller)")
    @Test
    void obtenerPropietarioPorDniTest() throws Exception {
        when(propietarioService.findByDni(anyString())).thenReturn(Datos.PROPIETARIO_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/dni/{dni}", "44000888T"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is(Datos.PROPIETARIO_BUSQUEDAS_DTO_1.getNombre())));
    }

    @DisplayName("Test para obtener un propietario por su dni (controller), propietario no encontrado")
    @Test
    void obtenerPropietarioPorDniPropietarioNoEncontradoTest() throws Exception {
        when(propietarioService.findByDni(anyString())).thenThrow(ResourceNotFoundException.class);

        ResultActions response = mockMvc.perform(get("/api/propietarios/dni/{dni}", "44000888T"));

        response.andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("Test para obtener una lista de propietarios por nombre")
    @Test
    void obtenerPropietariosPorNombreTest() throws Exception {
        when(propietarioService.findByNombre(anyString())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/nombre/{nombre}", "Antonio"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista de propietarios por primer apellido")
    @Test
    void obtenerPropietariosPorPrimerApellidoTest() throws Exception {
        when(propietarioService.findByPrimerApellido(anyString())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/primer-apellido/{primerApellido}", "Perez"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista parcial de propietarios por primer apellido")
    @Test
    void obtenerPropietariosParcialPorPrimerApellidoTest() throws Exception {
        when(propietarioService.findByPrimerApellidoPartial(anyString())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/primer-apellido/parcial/{primerApellido}", "Perez"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista de propietarios por nombre mas apellidos")
    @Test
    void obtenerPropietariosPorNombreMasPrimerApellidoTest() throws Exception {
        when(propietarioService.findByNombreAndPrimerApellidoAndSegundoApellido(anyString(), anyString(), anyString())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/nombre-apellidos/{nombre}-{primerApellido}-{segundoApellido}", "Antonio", "Perez", "Jimenez"));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista de propietarios por codigo postal")
    @Test
    void obtenerPropietariosPorCodigoPostalTest() throws Exception {
        when(propietarioService.ObtenerPropietariosPorCodigoPostal(anyLong())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/codigo_postal/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_DTO_1.size())));
    }

    @DisplayName("Test para obtener una lista parcial de propietarios por codigo postal")
    @Test
    void obtenerPropietariosParcialPorCodigoPostalest() throws Exception {
        when(propietarioService.obtenerPropietariosPorCodigoPostalParcial(anyLong())).thenReturn(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1);

        ResultActions response = mockMvc.perform(get("/api/propietarios/codigo_postal/parcial/{id}", 1L));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(Datos.PROPIETARIOS_BUSQUEDAS_PARCIAL_DTO_1.size())));
    }

    @DisplayName("Test para modificar un propietario (controller)")
    @Test
    void modificarPropietarioTest() throws Exception {
        when(propietarioService.modificarPropietario(Datos.PROPIETARIO_DTO_1, Datos.CODIGO_POSTAL_1.getId())).thenReturn(Datos.PROPIETARIO_DTO_1);

        ResultActions response = mockMvc.perform(put("/api/propietarios/{id_codigoPostal}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(Datos.PROPIETARIO_DTO_1)));

        response.andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("Test para eliminar un propietario")
    @Test
    void eliminarPropietarioTest() throws Exception {
        when(propietarioService.deleteById(anyLong())).thenReturn("Propietario eliminado con exito");

        ResultActions response = mockMvc.perform(delete("/api/propietarios/{id}", Datos.PROPIETARIO_1.getId()));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Propietario eliminado con exito"));
    }
}
