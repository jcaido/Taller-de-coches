package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import com.Tallerdecoches.services.ordenReparacion.OrdenReparacionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/ordenesReparacion")
public class OrdenReparacionController {

    private final OrdenReparacionService ordenReparacionService;

    public OrdenReparacionController(OrdenReparacionService ordenReparacionService) {
        this.ordenReparacionService = ordenReparacionService;
    }

    //crear una nueva Orden de reparacion
    @PostMapping("/{idVehiculo}")
    public ResponseEntity<OrdenReparacionDTO> crearOrdenesReparacion(@Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO
            , @PathVariable Long idVehiculo) {

        return ordenReparacionService.crearOrdenReparacion(ordenReparacionDTO, idVehiculo);
    }

    //Obtener una orden de reparacion por su id
    @GetMapping("/{id}")
    public ResponseEntity<OrdenReparacionBusquedasDTO> obtenerOrdenReparacionPorId(@PathVariable Long id) {

        return ordenReparacionService.findById(id);
    }

    //Obtener una lista con todas las ordenes de reparacion
    @GetMapping
    public List<OrdenReparacionBusquedasDTO> obtenerTodasLasOrdenesDeReparacion() {

        return ordenReparacionService.findAll();
    }

    //Obtener ordenes de reparacion por fecha de apertura (@RequestParam)
    @GetMapping("/fechaapertura")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaAperturaRP(@RequestParam(value="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByFechaApertura(fechaApertura);
    }

    //Obtener ordenes de reparacion por fecha de apertura (@PathVariable)
    @GetMapping("/fechaapertura/{fechaApertura}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaAperturaPV(@PathVariable(name="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByFechaApertura(fechaApertura);
    }

    //Obtener ordenes de reparacion por fecha de cierre (@PathVariable)
    @GetMapping("/fechacierre/{fechaCierre}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorFechaCierre(@PathVariable(name="fechaCierre")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierre) {

        return ordenReparacionService.findByFechaApertura(fechaCierre);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) (@PathVariable)
    @GetMapping("/cerrada/{cerrada}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerrada(@PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerrada(cerrada);
    }

    //Obtener ordenes de reparacion por vehiculo
    @GetMapping("/vehiculo/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(@PathVariable Long id_vehiculo) {

        return ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id_vehiculo);
    }
}
