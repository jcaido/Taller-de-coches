package com.Tallerdecoches.services.almacen;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.DTOs.almacen.MovimientoPiezaDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface InventarioAlmacenService {
    List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(LocalDate fecha);
    List<MovimientoPiezaDTO> obtenerMovimientosPorPieza(String referencia);
}
