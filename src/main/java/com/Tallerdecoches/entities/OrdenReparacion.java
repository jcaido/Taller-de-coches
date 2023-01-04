package com.Tallerdecoches.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "orden_reparacion")
public class OrdenReparacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_apertura")
    private LocalDate fechaApertura;
    @Column(name = "fecha_cierre")
    private LocalDate fechaCierre;
    private String descripcion;
    private Long kilometros;
    private Double horas;
    private Boolean cerrada;
    @OneToOne()
    private Vehiculo vehiculo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "ordenReparacion")
    private List<PiezasReparacion> piezasReparacion = new ArrayList<>();

    public OrdenReparacion() {
    }

    public OrdenReparacion(Long id, LocalDate fechaApertura, LocalDate fechaCierre, String descripcion, Long kilometros, Double horas, boolean cerrada) {
        this.id = id;
        this.fechaApertura = fechaApertura;
        this.fechaCierre = fechaCierre;
        this.descripcion = descripcion;
        this.kilometros = kilometros;
        this.horas = horas;
        this.cerrada = cerrada;
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

    public Boolean isCerrada() {
        return cerrada;
    }

    public void setCerrada(boolean cerrada) {
        this.cerrada = cerrada;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }
    public List<PiezasReparacion> getPiezasReparacion() {
        return piezasReparacion;
    }

    public void setPiezasReparacion(List<PiezasReparacion> piezasReparacion) {
        this.piezasReparacion = piezasReparacion;
    }

    @Override
    public String toString() {
        return "OrdenReparacion{" +
                "id=" + id +
                ", fechaApertura=" + fechaApertura +
                ", fechaCierre=" + fechaCierre +
                ", descripcion='" + descripcion + '\'' +
                ", kilometros=" + kilometros +
                ", horas=" + horas +
                ", cerrada=" + cerrada +
                '}';
    }
}
