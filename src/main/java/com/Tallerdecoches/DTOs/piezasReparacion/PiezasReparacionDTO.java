package com.Tallerdecoches.DTOs.piezasReparacion;

public class PiezasReparacionDTO {
    private Long id;
    private Integer cantidad;

    public PiezasReparacionDTO() {
    }

    public PiezasReparacionDTO(Long id, Integer cantidad) {
        this.id = id;
        this.cantidad = cantidad;
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
}
