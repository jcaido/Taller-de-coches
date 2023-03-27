package com.Tallerdecoches.DTOs.facturaProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.Tallerdecoches.entities.Proveedor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FacturaProveedorBusquedasDTO {
    private Long id;
    private Proveedor proveedor;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaFactura;
    private String numeroFactura;
    private Integer tipoIVA;
    private Boolean contabilizada;
    private List<AlbaranProveedorBusquedasDTO> albaranesProveedores;
}
