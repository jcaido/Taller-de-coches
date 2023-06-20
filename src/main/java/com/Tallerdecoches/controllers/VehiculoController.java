package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoCrearDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import com.Tallerdecoches.services.propietario.PropietarioService;
import com.Tallerdecoches.services.vehiculo.VehiculoService;
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
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final PropietarioService propietarioService;

    public VehiculoController(VehiculoService vehiculoService, PropietarioService propietarioService) {
        this.vehiculoService = vehiculoService;
        this.propietarioService = propietarioService;
    }

    @Operation(summary = "Crear un nuevo Vehiculo", description = "Crear un nuevo Vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "vehiculo creado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "Dato/s invalidos",
                    content = @Content),
    })
    @PostMapping("/{id_propietario}")
    public ResponseEntity<VehiculoDTO> crearVehiculo(@Valid @RequestBody VehiculoCrearDTO vehiculoCrearDTO, @Parameter(
            description = "id del propietario del vehiculo", required = true) @PathVariable Long id_propietario) {

        return new ResponseEntity<>(vehiculoService.crearVehiculo(vehiculoCrearDTO, id_propietario), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener todos los Vehiculos", description = "Obtener todos los Vehiculos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    })
    })
    @GetMapping
    public List<VehiculoBusquedasDTO> obtenerTodosLosVehiculos() {

        return vehiculoService.findAll();
    }

    @Operation(summary = "Obtener todos los Vehiculos", description = "Obtener todos los Vehiculos sin incluir todos los campos " +
            "solo incluimos los campos id, matricula, marca, modelo y propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/parcial")
    public List<VehiculoBusquedasParcialDTO> obtenerTodosLosVehiculosParcial() {

        return vehiculoService.findAllPartial();
    }

    @Operation(summary = "Obtener Vehiculo por id", description = "Obtener Vehiculo por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "vehiculo no encontrado",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoBusquedasDTO> obtenerVehiculoPorId (@Parameter(description = "id del vehiculo a buscar",
            required = true) @PathVariable Long id) {

        return vehiculoService.findById(id);
    }

    @Operation(summary = "Obtener Vehiculo por matricula", description = "Obtener Vehiculo por matricula")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    }),
            @ApiResponse(responseCode = "404", description = "vehiculo no encontrado",
                    content = @Content)
    })
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<VehiculoBusquedasDTO> obtenerVehiculoPorMatricula(@Parameter(description = "matricula del vehiculo a buscar",
            required = true) @PathVariable String matricula) {

        return vehiculoService.findByMatricula(matricula);
    }

    @Operation(summary = "Obtener Vehiculos por marca", description = "Obtener Vehiculos por marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    })
    })
    @GetMapping("/marca/{marca}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorMarca(@Parameter(description = "marca del vehiculo a buscar",
            required = true) @PathVariable String marca) {

        return vehiculoService.findByMarca(marca);
    }

    @Operation(summary = "Obtener Vehiculos por marca mas modelo", description = "Obtener Vehiculos por marca mas modelo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    })
    })
    @GetMapping("/marca-modelo/{marca}-{modelo}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorMarcaYModelo(
            @Parameter(description = "marca del vehiculo a buscar", required = true)
            @PathVariable String marca,
            @Parameter(description = "modelo del vehiculo a buscar", required = true)
            @PathVariable String modelo) {

        return vehiculoService.findByMarcaAndModelo(marca, modelo);
    }

    @Operation(summary = "Obtener Vehiculos por marca mas modelo", description = "Obtener Vehiculos por marca mas modelo " +
            "sin incluir todos lo campos, solo id, matricula, marca, modelo y propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/marca-modelo/parcial/{marca}-{modelo}")
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorMarcaYModeloParcial(
            @Parameter(description = "marca del vehiculo a buscar", required = true)
            @PathVariable String marca,
            @Parameter(description = "modelo del vehiculo a buscar", required = true)
            @PathVariable String modelo) {

        return vehiculoService.findByMarcaModeloPartial(marca, modelo);
    }

    @Operation(summary = "Obtener Vehiculos por propietario", description = "Obtener Vehiculos por propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasDTO.class))
                    })
    })
    @GetMapping("/propietario/{id_propietario}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioParcial(@Parameter(description = "id del propietario del vehiculo a buscar",
            required = true) @PathVariable Long id_propietario) {

        //return vehiculoService.obtenerVehiculosPorPropietarioSQL(id_propietario);
        return vehiculoService.obtenerVehiculosPorPropietarioHQL(id_propietario);
    }

    @Operation(summary = "Obtener Vehiculos por propietario", description = "Obtener Vehiculos por propietario " +
            "sin incluir todos lo campos, solo id, matricula, marca, modelo y propietario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculos obtenidos correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoBusquedasParcialDTO.class))
                    })
    })
    @GetMapping("/propietario/parcial/{id_propietario}")
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorPropietario(@Parameter(description = "id del propietario del vehiculo a buscar",
            required = true) @PathVariable Long id_propietario) {

        //return vehiculoService.obtenerVehiculosPorPropietarioSQL(id_propietario);
        return vehiculoService.obtenerVehiculosPorPropietarioHQLParcial(id_propietario);
    }

    @Operation(summary = "Modificar un Vehiculo", description = "Modificar un Vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo modificado correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = VehiculoDTO.class))
                    }),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content)
    })
    @PutMapping("/{id_propietario}")
    public ResponseEntity<VehiculoDTO> modificarVehiculo(@Valid @RequestBody VehiculoDTO vehiculoDTO, @Parameter(
            description = "id del propietario del vehiculo a modificar", required = true) @PathVariable Long id_propietario) {

        return vehiculoService.modificarVehiculo(vehiculoDTO, id_propietario);
    }

    @Operation(summary = "Eliminar un Vehiculo", description = "Eliminar un Vehiculo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Vehiculo eliminado correctamente",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Vehiculo no encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "Dato/s invalidos",
                    content = @Content),
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@Parameter(description = "id del vehiculo a eliminar",
            required = true) @PathVariable Long id) {

        return vehiculoService.deleteById(id);
    }
}
