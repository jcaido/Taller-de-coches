package com.Tallerdecoches.DTOs.vehiculo;

import com.Tallerdecoches.entities.Propietario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehiculoBusquedasDTO {

    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Propietario propietario;
}
