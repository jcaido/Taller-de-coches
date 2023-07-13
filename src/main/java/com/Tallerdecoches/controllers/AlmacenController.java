package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.DTOs.almacen.MovimientoPiezaDTO;
import com.Tallerdecoches.services.almacen.InventarioAlmacenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Obtener el inventario de almacén actual", description = "Obtener el inventario de almacén actual")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Inventario de almacén obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MovimientoAlmacenDTO.class))
                    }),
    })
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

        return inventarioAlmacenService.obtenerMovimientosPorPieza(pieza);
    }
}
