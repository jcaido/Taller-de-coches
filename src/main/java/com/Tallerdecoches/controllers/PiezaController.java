package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import com.Tallerdecoches.services.pieza.PiezaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/piezas")
public class PiezaController {

    private final PiezaService piezaService;
    public PiezaController(PiezaService piezaService) {
        this.piezaService = piezaService;
    }

    //crear una nueva Pieza
    @PostMapping
    public ResponseEntity<PiezaDTO> crearPieza(@Valid @RequestBody PiezaDTO piezaDTO) {

        return piezaService.crearPieza(piezaDTO);
    }

    //Obtener una lista con todas las piezas
    @GetMapping
    public List<PiezaDTO> obtenerTodasLasPiezas() {

        return piezaService.findAll();
    }

    //Obtener una pieza por su id
    @GetMapping("/{id}")
    public ResponseEntity<PiezaDTO> obtenerPiezaPorId(@PathVariable Long id) {

        return piezaService.findById(id);
    }

    //Obtener una pieza por referencia
    @GetMapping("/referencia/{referencia}")
    public ResponseEntity<PiezaDTO> obtenerPiezaPorReferencia(@PathVariable String referencia) {

        return piezaService.findByReferencia(referencia);
    }

    //Obtener lista de piezas filtrada por nombre
    @GetMapping("/nombre/{nombre}")
    public List<PiezaDTO> obtenerPiezasPorNombre(@PathVariable String nombre) {

        return piezaService.findByNombre(nombre);
    }

    //Modificar una pieza existente
    @PutMapping
    public ResponseEntity<PiezaDTO> modificarPieza(@Valid @RequestBody PiezaDTO piezaDTO) {

        return piezaService.modificarPieza(piezaDTO);
    }
}
