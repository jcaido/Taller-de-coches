package com.Tallerdecoches.DTOs.entradaPieza;

import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.entities.Proveedor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EntradaPiezaBusquedasDTO {
    private Long id;
    private Proveedor proveedor;
    private LocalDate fechaEntrada;
    private String numeroAlbaran;
    private Pieza pieza;
    private Integer cantidad;
    private Double precioEntrada;
    private Boolean facturada = false;
}
