package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.services.codigoPostal.CodigoPostalService;
import com.Tallerdecoches.services.propietario.PropietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/codigosPostales")
public class CodigoPostalController {

    private final CodigoPostalService codigoPostalService;
    private final PropietarioService propietarioService;

    public CodigoPostalController(CodigoPostalService codigoPostalService, PropietarioService propietarioService) {
        this.codigoPostalService = codigoPostalService;
        this.propietarioService = propietarioService;
    }

    @Operation(summary = "Crear un nuevo Código Postal", description = "Crear un nuevo Código Postal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "código postal creado correctamente",
                    content = {
                        @Content(mediaType = "application/json",
                                schema = @Schema(implementation = CodigoPostalDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping()
    public ResponseEntity<CodigoPostalDTO> crearCodigoPostal(@Valid @RequestBody CodigoPostalCrearDTO codigoPostalCrearDTO) {

        return new ResponseEntity<>(codigoPostalService.crearCodigoPostal(codigoPostalCrearDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los Códigos Postales", description = "Obtener todos los Códigos Postales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigos Postales obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    })
    })
    @GetMapping()
    public List<CodigoPostalDTO> obtenerTodosLosCodigosPostales() {

        return codigoPostalService.findAll();
    }

    @Operation(summary = "Obtener Código Postal por id", description = "Obtener Código Postal por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigo Postal obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "codigo postal no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorId(@Parameter(description = "id del código postal a buscar",
            required = true) @PathVariable Long id) {

        return new ResponseEntity<>(codigoPostalService.findById(id), HttpStatus.OK);
    }

    //TODO: pasar esto al controlador PropietarioController
    @GetMapping("/{id}/propietarios")
    public List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostal(@PathVariable Long id) {

        return propietarioService.ObtenerPropietariosPorCodigoPostal(id);
    }

    @Operation(summary = "Obtener Código Postal por código", description = "Obtener Código Postal por código")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigo Postal obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "codigo postal no encontrado",
                    content = @Content)
    })
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorCodigo(@Parameter(description = "número del código postal a buscar",
            required = true) @PathVariable String codigo) {

        return codigoPostalService.findByCodigo(codigo);
    }

    @Operation(summary = "Obtener Códigos Postales por provincia", description = "Obtener Códigos Postales por provincia")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigo Postal obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    })
    })
    @GetMapping("/provincia/{provincia}")
    public List<CodigoPostalDTO> obtenerCodigosPostalesPorProvincia(@Parameter(description = "provincia del código postal a buscar",
            required = true) @PathVariable String provincia) {

        return codigoPostalService.findByProvincia(provincia);
    }

    @Operation(summary = "Obtener Código Postal por localidad", description = "Obtener Código Postal por localidad")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigo Postal obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "codigo postal no encontrado",
                    content = @Content)
    })
    @GetMapping("/localidad/{localidad}")
    public ResponseEntity<CodigoPostalDTO> obtenerCodigoPostalPorLocalidad(@Parameter(description = "localidad del código postal a buscar",
            required = true) @PathVariable String localidad) {

        return codigoPostalService.findByLocalidad(localidad);
    }

    @Operation(summary = "Modificar un Código Postal", description = "Modificar un Código Postal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Codigo Postal modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CodigoPostalDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping()
    public ResponseEntity<CodigoPostalDTO> modificarCodigoPostal(@Valid @RequestBody CodigoPostalDTO codigoPostalDTO) {

        return codigoPostalService.modificarCodigoPostal(codigoPostalDTO);
    }

    @Operation(summary = "Eliminar un Código Postal", description = "Eliminar un Código Postal")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Código Postal eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Código Postal no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCodigoPostal(@Parameter(description = "id del código postal a eliminar",
            required = true) @PathVariable Long id) {

        return codigoPostalService.deleteById(id);
    }

}
