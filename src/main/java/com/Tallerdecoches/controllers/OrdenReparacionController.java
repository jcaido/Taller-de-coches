package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.ordenReparacion.*;
import com.Tallerdecoches.services.ordenReparacion.OrdenReparacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Crear una nueva orden de reparación", description = "Crear una nueva orden de reparación")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "oreden de reparación creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT [El vehiculo asociado a la orden de reparacion no existe]",
                    content = @Content),
    })
    @PostMapping("/{idVehiculo}")
    public ResponseEntity<OrdenReparacionDTO> crearOrdenesReparacion(@Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO,
            @Parameter(description = "id del vehículo", required = true) @PathVariable Long idVehiculo) {

        return new ResponseEntity<>(ordenReparacionService.crearOrdenReparacion(ordenReparacionDTO, idVehiculo), HttpStatus.CREATED);
    }
    @Operation(summary = "Obtener una orden de reparación por su id", description = "Obtener una orden de reparación por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Orden de reparacion no encontrada",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrdenReparacionBusquedasDTO> obtenerOrdenReparacionPorId(
            @Parameter(description = "id de la orden de reparación", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Obtener una orden de reparación parcial por su id", description = "campos id, fecha apertura, descripción, kilómetros, matrícula, marca, modelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden de reparación obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = OrdenReparacionBusquedasParcialDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "Orden de reparacion no encontrada",
                    content = @Content)
    })
    @GetMapping("/parcial/{id}")
    public ResponseEntity<OrdenReparacionBusquedasParcialDTO> obtenerOrdenReparacionPorIdParcial(
            @Parameter(description = "id de la orden de reparación", required = true) @PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.findByIdParcial(id), HttpStatus.OK);
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

    //Obtener ordenes de reparacion por estado (abiertas o cerradas), campos(id, fechaApertura, descripcion, kilometros, matricula)
    @GetMapping("/cerrada-parcial/{cerrada}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcial(@PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerradaParcial(cerrada);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) ordenada for fecha apertura, campos(id, fechaApertura, descripcion, kilometros, matricula)
    @GetMapping("/cerrada-parcial-sort/{cerrada}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialByFechaAperturaAsc(@PathVariable Boolean cerrada) {

        return ordenReparacionService.findByCerradaParcialByFechaAperturaAsc(cerrada);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) parcial y por fecha de apertura
    @GetMapping("/cerrada-parcial/{cerrada}/{fechaApertura}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialFechaApertura(
            @PathVariable Boolean cerrada,
            @PathVariable(name="fechaApertura")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaApertura) {

        return ordenReparacionService.findByCerradaParcialPorFechaApertura(cerrada, fechaApertura);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) entre fechas de cierre
    @GetMapping("/cerrada/{cerrada}/{fechaCierreInicial}/{fechaCierreFinal}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerradaEntreFechasDeCierre(
            @PathVariable Boolean cerrada,
            @PathVariable(name="fechaCierreInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierreInicial,
            @PathVariable(name="fechaCierreFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaCierreFinal) {

        return ordenReparacionService.findByCerradaEntreFechasDeCierre(cerrada, fechaCierreInicial, fechaCierreFinal);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) parcial y por vehiculo
    @GetMapping("/cerrada-parcial-vehiculo/{cerrada}/{id_vehiculo}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialVehiculo(
            @PathVariable Boolean cerrada,
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.findByCerradaParcialPorVehiculo(cerrada, id_vehiculo);
    }

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) por vehiculo
    @GetMapping("/cerrada-vehiculo/{cerrada}/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorIsCerradaVehiculo(
            @PathVariable Boolean cerrada,
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.findByCerradaPorVehiculo(cerrada, id_vehiculo);
    }

    //Obtener ordenes de reparacion por vehiculo
    @GetMapping("/vehiculo/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(@PathVariable Long id_vehiculo) {

        return ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id_vehiculo);
    }

    //Obtener ordenes de reparacion cerradas pendientes de facturar
    @GetMapping("/cerradas-ptes-facturar")
    public List<OrdenReparacionReducidaDTO> obtenerOrdenesReparacionCerradasPtesFacturar() {

        return ordenReparacionService.obtenerOrdenesReparacionCerradasPtesFacturar();
    }

    //Modificar una orden de reparacion
    @PutMapping("/{id_vehiculo}")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacion(@Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO, @PathVariable Long id_vehiculo) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacion(ordenReparacionDTO, id_vehiculo), HttpStatus.OK);
    }

    //Modificar una orden de reparacion, solo las horas
    @PutMapping("/horas")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionHoras(@RequestBody OrdenReparacionHorasDTO ordenReparacionHorasDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionHoras(ordenReparacionHorasDTO), HttpStatus.OK);
    }

    //Modificar una orden de reparacion, solo la fecha de cierre
    @PutMapping("/cierre")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionCierre(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionCierre(ordenReparacionCierreDTO), HttpStatus.OK);
    }

    //Modificar una orden de reparacion, abrir orden
    @PutMapping("/abrir")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionAbrir(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return new ResponseEntity<>(ordenReparacionService.modificarOrdenReparacionAbrir(ordenReparacionCierreDTO), HttpStatus.OK);
    }

    //Eliminar una orden de reparacion existente (no cerrada)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPropietario(@PathVariable Long id) {

        return new ResponseEntity<>(ordenReparacionService.deleteById(id), HttpStatus.OK);
    }
}
