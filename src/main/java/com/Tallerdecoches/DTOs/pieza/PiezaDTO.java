package com.Tallerdecoches.DTOs.pieza;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PiezaDTO {
    private Long id;
    @NotBlank(message =  "debe introducir la refenciare")
    private String referencia;
    @NotBlank(message =  "debe introducir el nombre")
    private String nombre;
    private Double precio;
}
