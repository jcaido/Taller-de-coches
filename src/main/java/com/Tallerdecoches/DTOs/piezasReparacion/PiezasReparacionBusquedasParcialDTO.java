package com.Tallerdecoches.DTOs.piezasReparacion;

import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Pieza;

public class PiezasReparacionBusquedasParcialDTO {

    private Long id;
    private Integer cantidad;
    private Pieza pieza;

    public PiezasReparacionBusquedasParcialDTO() {
    }

    public PiezasReparacionBusquedasParcialDTO(Long id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public PiezasReparacionBusquedasParcialDTO(Long id, Integer cantidad, Pieza pieza) {
        this.id = id;
        this.cantidad = cantidad;
        this.pieza = pieza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }
}
