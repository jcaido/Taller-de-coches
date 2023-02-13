package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.proveedor.ProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorCrearDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import com.Tallerdecoches.services.proveedor.ProveedorService;
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
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @Operation(summary = "Crear un nuevo Proveedor", description = "Crear un nuevo Proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "proveedor creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping("/{id_codigoPostal}")
    public ResponseEntity<ProveedorDTO> crearProveedor(@Valid @RequestBody ProveedorCrearDTO proveedorCrearDTO
            , @Parameter(description = "id del código postal del proveedor", required = true) @PathVariable Long id_codigoPostal) {

        return proveedorService.crearProveedor(proveedorCrearDTO, id_codigoPostal);
    }

    @Operation(summary = "Obtener todos los Proveedores", description = "Obtener todos los Proveedores")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedores obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProveedorBusquedasDTO.class))
                    })
    })
    @GetMapping()
    public List<ProveedorBusquedasDTO> obtenerTodosLosProveedores() {

        return proveedorService.findAll();
    }

    @Operation(summary = "Obtener Poveedor por id", description = "Obtener Proveedor por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProveedorBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "proveedor no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProveedorBusquedasDTO> obtenerProveedorPorId(@Parameter(description = "id del proveedor a buscar",
            required = true) @PathVariable Long id) {

        return proveedorService.findById(id);
    }

    @Operation(summary = "Obtener Proveedor por dni/cif", description = "Obtener Proveedor por dni/cif")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProveedorBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "proveedor no encontrado",
                    content = @Content)
    })
    @GetMapping("/dni-cif/{dniCif}")
    public ResponseEntity<ProveedorBusquedasDTO> obtenerProveedorPorDniCif(@Parameter(description = "dni/cif del proveedor a buscar",
            required = true) @PathVariable String dniCif) {

        return proveedorService.findByDniCif(dniCif);
    }

    @Operation(summary = "Modificar un Proveedor", description = "Modificar un Proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProveedorDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping("/{id_codigoPostal}")
    public ResponseEntity<ProveedorDTO> modificarProveedor(@Valid @RequestBody ProveedorDTO proveedorDTO
            , @Parameter(description = "id del codigo postal", required = true) @PathVariable Long id_codigoPostal) {

        return proveedorService.modificarProveedor(proveedorDTO, id_codigoPostal);
    }

    @Operation(summary = "Eliminar un Proveedor", description = "Eliminar un Proveedor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Proveedor eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Proveedor no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarProveedor(@Parameter(description = "id del proveedor", required = true) @PathVariable Long id) {

        return proveedorService.deleteById(id);
    }
}
