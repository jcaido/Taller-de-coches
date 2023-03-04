package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.services.almacen.InventarioAlmacenService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/almacen/inventario")
public class AlmacenController {

    private final InventarioAlmacenService inventarioAlmacenService;

    public AlmacenController(InventarioAlmacenService inventarioAlmacenService) {
        this.inventarioAlmacenService = inventarioAlmacenService;
    }

    @GetMapping()
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacen() {

        return inventarioAlmacenService.obtenerInventarioAlmacen();
    }
}
