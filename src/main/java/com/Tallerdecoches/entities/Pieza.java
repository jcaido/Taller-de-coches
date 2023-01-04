package com.Tallerdecoches.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "piezas")
public class Pieza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "referencia", unique = true)
    private String referencia;
    @Column(name = "nombre_pieza")
    private String nombre;
    @Column(name = "precio_venta")
    private Double precio;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "pieza")
    private List<PiezasReparacion> piezasReparacion = new ArrayList<>();

    public Pieza() {
    }

    public Pieza(Long id, String referencia, String nombre, Double precio) {
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

    public List<PiezasReparacion> getPiezasReparacion() {
        return piezasReparacion;
    }

    public void setPiezasReparacion(List<PiezasReparacion> piezasReparacion) {
        this.piezasReparacion = piezasReparacion;
    }

    @Override
    public String toString() {
        return "Pieza{" +
                "id=" + id +
                ", referencia='" + referencia + '\'' +
                ", nombre='" + nombre + '\'' +
                ", precio=" + precio +
                '}';
    }
}
