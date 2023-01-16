package com.Tallerdecoches.DTOs.piezasReparacion;

import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Pieza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PiezasReparacionBusquedasDTO {

    private Long id;
    private Integer cantidad;
    private Pieza pieza;
    private OrdenReparacion ordenReparacion;
}
