package com.Tallerdecoches.DTOs.vehiculo;

import com.Tallerdecoches.entities.Propietario;

public class VehiculoBusquedasDTO {

    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private Propietario propietario;

    public VehiculoBusquedasDTO() {
    }

    public VehiculoBusquedasDTO(Long id, String matricula, String marca, String modelo, String color) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
    }

    public VehiculoBusquedasDTO(Long id, String matricula, String marca, String modelo, String color, Propietario propietario) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.propietario = propietario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Propietario getPropietario() {
        return propietario;
    }

    public void setPropietario(Propietario propietario) {
        this.propietario = propietario;
    }
}
