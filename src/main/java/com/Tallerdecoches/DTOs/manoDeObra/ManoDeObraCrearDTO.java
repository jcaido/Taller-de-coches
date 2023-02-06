package com.Tallerdecoches.DTOs.manoDeObra;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManoDeObraCrearDTO {
    @NotNull(message = "El precio hora de la mano de obra no puede ser nulo")
    private Double precioHoraClienteTaller;
}
