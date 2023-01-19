package com.Tallerdecoches.DTOs.codigoPostal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CodigoPostalCrearDTO {

    @NotBlank(message =  "debe introducir el codigo")
    @Length(min=5, max=5, message = "el codigo postal debe tener 5 digitos")
    private String codigo;
    @NotBlank(message = "debe introducir una localidad")
    private String localidad;
    @NotBlank(message = "debe introducit una provincia")
    private String provincia;
}
