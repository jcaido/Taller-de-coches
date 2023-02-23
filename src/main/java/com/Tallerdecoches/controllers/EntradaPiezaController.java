package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.services.entradaPieza.EntradaPiezaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/entrada-pieza")
public class EntradaPiezaController {
    private final EntradaPiezaService entradaPiezaService;

    public EntradaPiezaController(EntradaPiezaService entradaPiezaService) {
        this.entradaPiezaService = entradaPiezaService;
    }

    @Operation(summary = "crear una entrada de almacén de una pieza", description = "crear una entrada de almacén de una pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "entrada creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "proveedor y/o pieza no encontrados",
                    content = @Content)
    })
    @PostMapping("/{id_proveedor}/{id_pieza}")
    public ResponseEntity<EntradaPiezaDTO> crearEntradaPieza(
            @Valid @RequestBody EntradaPiezaCrearDTO entradaPiezaCrearDTO,
            @Parameter(description = "id del proveedor de la entrada", required = true)
            @PathVariable Long id_proveedor,
            @Parameter(description = "id de la pieza de la entrada", required = true)
            @PathVariable Long id_pieza) {

        return entradaPiezaService.crearEntradaPieza(entradaPiezaCrearDTO, id_proveedor, id_pieza);
    }

    @Operation(summary = "Obtener todas las entradas de piezas", description = "Obtener todas las entradas de piezas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entradas obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaBusquedasDTO.class))
                    })
    })
    @GetMapping()
    public List<EntradaPiezaBusquedasDTO> obtenerTodasLasEntradasPiezas() {

        return entradaPiezaService.findAll();
    }

    @Operation(summary = "Obtener Entrada por id", description = "Obtener Entrada por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "entrada no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<EntradaPiezaBusquedasDTO> obtenerEntradaPorId(@Parameter(description = "id de la entrada a buscar",
            required = true) @PathVariable Long id) {

        return entradaPiezaService.findById(id);
    }

    @Operation(summary = "Obtener Entradas de piezas por proveedor", description = "Obtener Entradas de piezas por proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entradas obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaBusquedasDTO.class))
                    })
    })
    @GetMapping("/proveedor/{id_proveedor}")
    public List<EntradaPiezaBusquedasDTO> obtenerEntradaPorProveedor(@Parameter(description = "id del proveedor",
            required = true) @PathVariable Long id_proveedor) {

        return entradaPiezaService.obtenerEntradasPorProveedorHQL(id_proveedor);
    }

    @Operation(summary = "Obtener Entradas de piezas por pieza", description = "Obtener Entradas de piezas por pieza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entradas obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaBusquedasDTO.class))
                    })
    })
    @GetMapping("/pieza/{id_pieza}")
    public List<EntradaPiezaBusquedasDTO> obtenerEntradaPorPieza(@Parameter(description = "id de la pieza",
            required = true) @PathVariable Long id_pieza) {

        return entradaPiezaService.obtenerEntradasPorPiezaHQL(id_pieza);
    }

    @Operation(summary = "Modificar una Entrada", description = "Modificar una Entrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entrada modificada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EntradaPiezaDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping("/{id_proveedor}/{id_pieza}")
    public ResponseEntity<EntradaPiezaDTO> modificarEntradaPieza(
            @Valid @RequestBody EntradaPiezaDTO entradaPiezaDTO,
            @Parameter(description = "id del proveedor", required = true)
            @PathVariable Long id_proveedor,
            @Parameter(description = "id de la pieza", required = true)
            @PathVariable Long id_pieza) {

        return entradaPiezaService.modificarEntradaPieza(entradaPiezaDTO, id_proveedor, id_pieza);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarEntradaPieza(@Parameter(description = "id de la pieza", required = true) @PathVariable Long id) {

        return entradaPiezaService.deleteById(id);
    }
}
