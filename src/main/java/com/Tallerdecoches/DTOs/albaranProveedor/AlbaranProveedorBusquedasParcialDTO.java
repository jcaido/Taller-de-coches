package com.Tallerdecoches.DTOs.albaranProveedor;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.entities.Proveedor;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AlbaranProveedorBusquedasParcialDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private String proveedorNombre;
}
