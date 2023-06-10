package com.Tallerdecoches.DTOs.facturaCliente;

import com.Tallerdecoches.entities.Propietario;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacturaClienteDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha de la factura no puede ser nula")
    private LocalDate fechaFactura;
    private String serie;
    private Long numeroFactura;
    private Integer tipoIVA;
    private Propietario propietario;
}
