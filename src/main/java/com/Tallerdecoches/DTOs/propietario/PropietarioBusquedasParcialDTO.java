package com.Tallerdecoches.DTOs.propietario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropietarioBusquedasParcialDTO {

    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
}
