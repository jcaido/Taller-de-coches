package com.Tallerdecoches.DTOs.entradaPieza;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntradaPiezaCrearDTO {
    @NotNull(message = "debe introducir la cantidad")
    private Integer cantidad;
    @NotNull(message = "debe introducir el precio de la entrada")
    private Double precioEntrada;
}
