package com.Tallerdecoches.DTOs.albaranProveedor;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasParcialDTO;
import com.Tallerdecoches.entities.FacturaProveedor;
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
public class AlbaranProveedorBusquedasDTO {
    private Long id;
    private Proveedor proveedor;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha del albarán no puede ser nula")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private Boolean facturado = false;
    private List<EntradaPiezaBusquedasParcialDTO> entradasPiezas;
    private FacturaProveedor facturaProveedor;
}
