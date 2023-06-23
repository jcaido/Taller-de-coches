package com.Tallerdecoches.DTOs.propietario;

import com.Tallerdecoches.entities.CodigoPostal;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PropietarioBusquedasDTO {

    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
    private String domicilio;
    private CodigoPostal codigoPostal;
}
