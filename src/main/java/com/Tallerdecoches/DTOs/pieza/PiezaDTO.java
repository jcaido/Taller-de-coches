package com.Tallerdecoches.DTOs.pieza;

import javax.validation.constraints.NotBlank;

public class PiezaDTO {
    private Long id;
    @NotBlank(message =  "debe introducir la refenciare")
    private String referencia;
    @NotBlank(message =  "debe introducir el nombre")
    private String nombre;
    private Double precio;

    public PiezaDTO() {
    }

    public PiezaDTO(Long id, String referencia, String nombre, Double precio) {
        this.id = id;
        this.referencia = referencia;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }
}
