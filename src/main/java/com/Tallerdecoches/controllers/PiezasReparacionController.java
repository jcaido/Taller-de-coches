package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.services.piezasReparacion.PiezasReparacionService;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/piezas-reparacion")
public class PiezasReparacionController {

    private final PiezasReparacionService piezasReparacionService;

    public PiezasReparacionController(PiezasReparacionService piezasReparacionService) {
        this.piezasReparacionService = piezasReparacionService;
    }

    //Crear un nuevo piezas-reparacion
    @PostMapping("/{id_ordenReparacion}/{id_pieza}")
    public ResponseEntity<PiezasReparacionDTO> crearPiezasReparacion(@Valid @RequestBody PiezasReparacionDTO piezasReparacionDTO, @PathVariable Long id_ordenReparacion, @PathVariable Long id_pieza) {

        return piezasReparacionService.crearPiezasReparacion(piezasReparacionDTO, id_ordenReparacion, id_pieza);
    }

    @PostMapping()
    public ResponseEntity<PiezasReparacionDTO> crearPiezasReparacionRP(@Valid @RequestBody PiezasReparacionDTO piezasReparacionDTO, @RequestParam(value="ordenReparacion") Long id_ordenReparacion, @RequestParam(value="pieza") Long id_pieza) {

        return piezasReparacionService.crearPiezasReparacion(piezasReparacionDTO, id_ordenReparacion, id_pieza);
    }

    //Obtener una lista con todas las piezas-reparacion
    @GetMapping()
    public List<PiezasReparacionBusquedasDTO> obtenerTodasLasPiezasReparacion() {

        return piezasReparacionService.findAll();
    }

    //Obtener una pieza-reparacion por su id
    @GetMapping("/{id}")
    public ResponseEntity<PiezasReparacionBusquedasDTO> obtenerPiezaReparacionPorId(@PathVariable Long id) {

        return piezasReparacionService.findById(id);
    }

    //Obtener piezas-reparacion por orden de reparacion
    @GetMapping("/orden-reparacion/{id}")
    public List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(@PathVariable Long id) {

        return piezasReparacionService.obtenerPiezasReparacionPorOrdenReparacion(id);

    }
}
