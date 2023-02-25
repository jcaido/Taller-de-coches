package com.Tallerdecoches.DTOs.albaranProveedor;

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
public class AlbaranProveedorDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "la fecha del albarán no puede ser nula")
    private LocalDate fechaAlbaran;
    private String numeroAlbaran;
    private Boolean facturado = false;
}
