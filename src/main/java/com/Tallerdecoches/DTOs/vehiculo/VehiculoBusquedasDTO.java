package com.Tallerdecoches.DTOs.vehiculo;

import com.Tallerdecoches.entities.Propietario;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class VehiculoBusquedasDTO {

    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Propietario propietario;
}
