package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.ordenReparacion.*;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
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

    //Obtener una orden de reparacion por su id parcial
    @GetMapping("/parcial/{id}")
    public ResponseEntity<OrdenReparacionBusquedasParcialDTO> obtenerOrdenReparacionPorIdParcial(@PathVariable Long id) {

        return ordenReparacionService.findByIdParcial(id);
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

    //Obtener ordenes de reparacion por estado (abiertas o cerradas) parcial y por vehiculo
    @GetMapping("/cerrada-parcial-vehiculo/{cerrada}/{id_vehiculo}")
    public List<OrdenReparacionBusquedasParcialDTO> obtenerOrdenesReparacionPorIsCerradaParcialVehiculo(
            @PathVariable Boolean cerrada,
            @PathVariable Long id_vehiculo) {

        return ordenReparacionService.findByCerradaParcialPorVehiculo(cerrada, id_vehiculo);
    }
    //Obtener ordenes de reparacion por vehiculo
    @GetMapping("/vehiculo/{id_vehiculo}")
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(@PathVariable Long id_vehiculo) {

        return ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id_vehiculo);
    }

    //Modificar una orden de reparacion
    @PutMapping("/{id_vehiculo}")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacion(@Valid @RequestBody OrdenReparacionDTO ordenReparacionDTO, @PathVariable Long id_vehiculo) {

        return ordenReparacionService.modificarOrdenReparacion(ordenReparacionDTO, id_vehiculo);
    }

    //Modificar una orden de reparacion, solo las horas
    @PutMapping("/horas")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionHoras(@RequestBody OrdenReparacionHorasDTO ordenReparacionHorasDTO) {

        return ordenReparacionService.modificarOrdenReparacionHoras(ordenReparacionHorasDTO);
    }

    //Modificar una orden de reparacion, solo la fecha de cierre
    @PutMapping("/cierre")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionCierre(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return ordenReparacionService.modificarOrdenReparacionCierre(ordenReparacionCierreDTO);
    }

    //Modificar una orden de reparacion, abrir orden
    @PutMapping("/abrir")
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacionAbrir(@RequestBody OrdenReparacionCierreDTO ordenReparacionCierreDTO) {

        return ordenReparacionService.modificarOrdenReparacionAbrir(ordenReparacionCierreDTO);
    }

    //Eliminar una orden de reparacion existente (no cerrada)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPropietario(@PathVariable Long id) {

        return ordenReparacionService.deleteById(id);
    }
}
