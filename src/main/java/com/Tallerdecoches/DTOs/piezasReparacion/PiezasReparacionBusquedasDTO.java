package com.Tallerdecoches.DTOs.piezasReparacion;

import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Pieza;

public class PiezasReparacionBusquedasDTO {

    private Long id;
    private Integer cantidad;
    private Pieza pieza;
    private OrdenReparacion ordenReparacion;

    public PiezasReparacionBusquedasDTO() {
    }

    public PiezasReparacionBusquedasDTO(Long id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
    }

    public PiezasReparacionBusquedasDTO(Long id, Integer cantidad, Pieza pieza, OrdenReparacion ordenReparacion) {
        this.id = id;
        this.cantidad = cantidad;
        this.pieza = pieza;
        this.ordenReparacion = ordenReparacion;
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

    public OrdenReparacion getOrdenReparacion() {
        return ordenReparacion;
    }

    public void setOrdenReparacion(OrdenReparacion ordenReparacion) {
        this.ordenReparacion = ordenReparacion;
    }
}
