package com.Tallerdecoches.DTOs.manoDeObra;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ManoDeObraCrearDTO {
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha del nuevo prevcio horario no puede ser nula")
    private LocalDate fechaNuevoPrecio;
    @NotNull(message = "El precio hora de la mano de obra no puede ser nulo")
    private Double precioHoraClienteTaller;
    @NotNull(message = "El precio actual debe ser verdadero o falso")
    private Boolean precioHoraClienteTallerActual;
}
