package com.Tallerdecoches.DTOs.ordenReparacion;

import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.entities.Vehiculo;
import com.fasterxml.jackson.annotation.JsonFormat;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
public class OrdenReparacionBusquedasDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaApertura;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCierre;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private Double horas;
    private Boolean cerrada;
    private Vehiculo vehiculo;
    private List<PiezasReparacionBusquedasParcialDTO> piezasReparacion;

    public OrdenReparacionBusquedasDTO() {
    }

    public OrdenReparacionBusquedasDTO(Long id, LocalDate fechaApertura, LocalDate fechaCierre, String descripcion, Long kilometros, Double horas, Boolean cerrada) {
        this.id = id;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.descripcion = descripcion;
        this.kilometros = kilometros;
        this.horas = horas;
        this.cerrada = cerrada;
    }

    public OrdenReparacionBusquedasDTO(Long id, LocalDate fechaApertura, LocalDate fechaCierre, String descripcion, Long kilometros, Double horas, Boolean cerrada, Vehiculo vehiculo, List<PiezasReparacionBusquedasParcialDTO> piezasReparacion) {
        this.id = id;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.descripcion = descripcion;
        this.kilometros = kilometros;
        this.horas = horas;
        this.cerrada = cerrada;
        this.vehiculo = vehiculo;
        this.piezasReparacion = piezasReparacion;
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

    public LocalDate getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(LocalDate fechaCierre) {
        this.fechaCierre = fechaCierre;
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

    public Double getHoras() {
        return horas;
    }

    public void setHoras(Double horas) {
        this.horas = horas;
    }

    public Boolean getCerrada() {
        return cerrada;
    }

    public void setCerrada(Boolean cerrada) {
        this.cerrada = cerrada;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<PiezasReparacionBusquedasParcialDTO> getPiezasReparacion() {
        return piezasReparacion;
    }

    public void setPiezasReparacion(List<PiezasReparacionBusquedasParcialDTO> piezasReparacion) {
        this.piezasReparacion = piezasReparacion;
    }
}
