package com.Tallerdecoches.DTOs.proveedor;

import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.EntradaPieza;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProveedorBusquedasDTO {
    private Long id;
    private String nombre;
    private String dniCif;
    private String domicilio;
    private CodigoPostal codigoPostal;
    private EntradaPieza entradaPieza;
}
