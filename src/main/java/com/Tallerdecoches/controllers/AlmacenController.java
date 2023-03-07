package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.DTOs.almacen.MovimientoPiezaDTO;
import com.Tallerdecoches.services.almacen.InventarioAlmacenService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/almacen")
public class AlmacenController {

    private final InventarioAlmacenService inventarioAlmacenService;

    public AlmacenController(InventarioAlmacenService inventarioAlmacenService) {
        this.inventarioAlmacenService = inventarioAlmacenService;
    }

    @GetMapping("/inventario")
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacen() {

        return inventarioAlmacenService.obtenerInventarioAlmacenFecha(LocalDate.now());
    }

    @GetMapping("/inventario/{fecha}")
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(@PathVariable(name="fecha") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {

        return inventarioAlmacenService.obtenerInventarioAlmacenFecha(fecha);
    }

    @GetMapping("/movimientos/{pieza}")
    public List<MovimientoPiezaDTO> obtenerEntradasPorPieza(@PathVariable(name="pieza") String pieza) {

        return inventarioAlmacenService.obtenerEntradasPorPieza(pieza);
    }
}
