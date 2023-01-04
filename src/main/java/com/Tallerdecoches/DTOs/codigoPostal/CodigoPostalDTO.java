package com.Tallerdecoches.DTOs.codigoPostal;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;

public class CodigoPostalDTO {

    private Long id;
    @NotBlank(message =  "debe introducir el codigo")
    @Length(min=5, max=5, message = "el codigo postal debe tener 5 digitos")
    private String codigo;
    @NotBlank(message = "debe introducir una localidad")
    private String localidad;
    @NotBlank(message = "debe introducit una provincia")
    private String provincia;

    public CodigoPostalDTO() {
    }

    public CodigoPostalDTO(Long id, String codigo, String localidad, String provincia) {
        this.id = id;
        this.codigo = codigo;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }
}
