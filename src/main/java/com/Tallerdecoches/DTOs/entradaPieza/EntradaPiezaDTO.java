package com.Tallerdecoches.DTOs.entradaPieza;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntradaPiezaDTO {
    private Long id;
    @NotNull(message = "debe introducir la fecha de la entrada")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaEntrada;
    @NotBlank(message = "debe introducir el numero de albarán")
    private String numeroAlbaran;
    @NotNull(message = "debe introducir la cntidad")
    private Integer cantidad;
    @NotNull(message = "debe introducir el precio de la entrada")
    private Double precioEntrada;
}
