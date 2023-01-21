package com.Tallerdecoches.DTOs.vehiculo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoCrearDTO {

    @NotBlank(message = "debe introducir la matricula")
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
}
