package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.services.CodigoPostalService;
import com.Tallerdecoches.services.PropietarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/codigosPostales")
public class CodigoPostalController {

    private final CodigoPostalService codigoPostalService;
    private final PropietarioService propietarioService;

    public CodigoPostalController(CodigoPostalService codigoPostalService, PropietarioService propietarioService, CodigoPostalRepository codigoPostalRepository) {
        this.codigoPostalService = codigoPostalService;
        this.propietarioService = propietarioService;
    }

    //crear un nuevo Codigo Postal
    @PostMapping
    public ResponseEntity<CodigoPostalDTO> crearCodigoPostal(@Valid @RequestBody CodigoPostalDTO codigoPostalDTO) {

        return codigoPostalService.crearCodigoPostal(codigoPostalDTO);
    }

    //Obtener una lista con todos los codigos postales
    @GetMapping
    public List<CodigoPostalDTO> obtenerTodosLosCodigosPostales() {

        return codigoPostalService.findAll();
    }


    //Obtener un codigo postal por su id
    @GetMapping("/{id}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorId(@PathVariable Long id) {

        return codigoPostalService.findById(id);
    }

    @GetMapping("/{id}/propietarios")
    public List<PropietarioDTO> obtenerPropietariosPorCodigoPostal(@PathVariable Long id) {

        return propietarioService.ObtenerPropietariosPorCodigoPostal(id);
    }


    //Obtener un codigo postal por codigo
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorCodigo(@PathVariable String codigo) {

        return codigoPostalService.findByCodigo(codigo);
    }


    //Obtener lista de codigos postales filtrada por provincia
    @GetMapping("/provincia/{provincia}")
    public List<CodigoPostalDTO> obtenerCodigosPostalesPorProvincia(@PathVariable String provincia) {

        return codigoPostalService.findByProvincia(provincia);
    }

    //Obtener un codigo postal por localidad
    @GetMapping("/localidad/{localidad}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorLocalidad(@PathVariable String localidad) {

        return codigoPostalService.findByLocalidad(localidad);
    }


    //Modificar un codigo postal existente
    @PutMapping
    public ResponseEntity<CodigoPostalDTO> modificarCodigoPostal(@Valid @RequestBody CodigoPostalDTO codigoPostalDTO) {

        return codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
    }

    //Eliminar un codigo postal existente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCodigoPostal(@PathVariable Long id) {

        return codigoPostalService.deleteById(id);
    }

}
