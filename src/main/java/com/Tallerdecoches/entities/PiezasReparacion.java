package com.Tallerdecoches.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "piezas_reparacion")
public class PiezasReparacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Pieza pieza;
    private Integer cantidad;
    @OneToOne()
    private OrdenReparacion ordenReparacion;

    public PiezasReparacion() {
    }

    public PiezasReparacion(Long id, Pieza pieza, Integer cantidad) {
        this.id = id;
        this.pieza = pieza;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public OrdenReparacion getOrdenReparacion() {
        return ordenReparacion;
    }

    public void setOrdenReparacion(OrdenReparacion ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
    }
}
