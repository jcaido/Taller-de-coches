package com.Tallerdecoches.DTOs.almacen;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MovimientoAlmacenDTO {
    private String piezaReferencia;
    private String piezaNombre;
    private Long total;
    public record Movimiento(String piezaReferencia, String piezaNombre) {};
}
