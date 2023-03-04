package com.Tallerdecoches.services.almacen;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;

import java.util.List;
import java.util.Map;

public interface InventarioAlmacenService {
    List<MovimientoAlmacenDTO> obtenerInventarioAlmacen();
}
