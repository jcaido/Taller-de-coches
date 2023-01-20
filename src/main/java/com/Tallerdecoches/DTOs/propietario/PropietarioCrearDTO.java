package com.Tallerdecoches.DTOs.propietario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropietarioCrearDTO {

    @NotBlank(message = "debe introducir el nombre")
    private String nombre;
    @NotBlank(message = "debe introducir el primer apellido")
    private String primerApellido;
    @NotBlank(message = "debe introducir el segundo apellido")
    private String segundoApellido;
    @NotBlank(message = "debe introducir el dni")
    private String dni;
    private String domicilio;
}
