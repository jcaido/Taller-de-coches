package com.Tallerdecoches.DTOs.vehiculo;

public class VehiculoBusquedasParcialDTO {

    private Long id;
    private String matricula;
    private String marca;
    private String modelo;
    private String propietarioDni;

    public VehiculoBusquedasParcialDTO() {
    }

    public VehiculoBusquedasParcialDTO(Long id, String matricula, String marca, String modelo, String propietarioDni) {
        this.id = id;
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.propietarioDni = propietarioDni;
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

    public String getPropietarioDni() {
        return propietarioDni;
    }

    public void setPropietarioDni(String propietarioDni) {
        this.propietarioDni = propietarioDni;
    }
}
