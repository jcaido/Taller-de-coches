package com.Tallerdecoches.DTOs.ordenReparacion;

import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.entities.Vehiculo;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

public class OrdenReparacionBusquedasParcialDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaApertura;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private String vehiculoMatricula;
    private String vehiculoMarca;
    private String vehiculoModelo;

    public OrdenReparacionBusquedasParcialDTO() {
    }

    public OrdenReparacionBusquedasParcialDTO(Long id, LocalDate fechaApertura, String descripcion, Long kilometros, String vehiculoMatricula, String vehiculoMarca, String vehiculoModelo) {
        this.id = id;
        this.fechaApertura = fechaApertura;
        this.descripcion = descripcion;
        this.kilometros = kilometros;
        this.vehiculoMatricula = vehiculoMatricula;
        this.vehiculoMarca = vehiculoMarca;
        this.vehiculoModelo = vehiculoModelo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDate fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getKilometros() {
        return kilometros;
    }

    public void setKilometros(Long kilometros) {
        this.kilometros = kilometros;
    }

    public String getVehiculoMatricula() {
        return vehiculoMatricula;
    }

    public void setVehiculoMatricula(String vehiculoMatricula) {
        this.vehiculoMatricula = vehiculoMatricula;
    }

    public String getVehiculoMarca() {
        return vehiculoMarca;
    }

    public void setVehiculoMarca(String vehiculoMarca) {
        this.vehiculoMarca = vehiculoMarca;
    }

    public String getVehiculoModelo() {
        return vehiculoModelo;
    }

    public void setVehiculoModelo(String vehiculoModelo) {
        this.vehiculoModelo = vehiculoModelo;
    }
}
