package com.Tallerdecoches.DTOs.propietario;

import com.Tallerdecoches.entities.CodigoPostal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropietarioBusquedasDTO {

    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
    private String domicilio;
    private CodigoPostal codigoPostal;
}
