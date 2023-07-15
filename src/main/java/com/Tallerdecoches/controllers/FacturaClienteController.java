package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.services.facturaCliente.FacturaClienteService;
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
@RequestMapping("/api/facturaCliente")
public class FacturaClienteController {

    private final FacturaClienteService facturaClienteService;

    public FacturaClienteController(FacturaClienteService facturaClienteService) {
        this.facturaClienteService = facturaClienteService;
    }
    @Operation(summary = "Crear una nueva factura de cliente", description = "Crear una nueva factura de cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "factura de cliente creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaClienteDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "404",
                    description = "[Propietario no encontrado], [Orden de reparación no encontrada]",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT [La orden de reparación no pertenece al propietario], " +
                            "[La orden de reparación no está cerrada], " +
                            "[La orden de reparación ya está facturada], " +
                            "[La fecha factura no puede ser inferior a la última factura de la serie]",
                    content = @Content),
    })
    @PostMapping("/nuevaFactura/{idPropietario}/{idOrdenReparacion}")
    public ResponseEntity<FacturaClienteDTO> crearFacturaCliente(@Valid @RequestBody FacturaClienteCrearDTO facturaClienteCrearDTO,
                                                                 @Parameter(description = "id del propietario", required = true)
                                                                 @PathVariable Long idPropietario,
                                                                 @Parameter(description = "id de la orden de reparación", required = true)
                                                                 @PathVariable Long idOrdenReparacion) {

        return new ResponseEntity<>(facturaClienteService.crearFacturaCliente(facturaClienteCrearDTO, idPropietario, idOrdenReparacion), HttpStatus.CREATED);
    }
    @Operation(summary = "Obtener una lista con todas las facturas de clientes", description = "Obtener una lista con todas las facturas de clientes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de cliente obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaClientesBusquedasDTO.class))
                    })
    })
    @GetMapping()
    public List<FacturaClientesBusquedasDTO> obtenerTodasLasFacturasCliente() {

        return facturaClienteService.findAll();
    }
    @Operation(summary = "Obtener una lista con todas las facturas de clientes entre fechas", description = "Obtener una lista con todas las facturas de clientes entre fechas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de cliente obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaClientesBusquedasDTO.class))
                    })
    })
    @GetMapping("/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaClientesBusquedasDTO> obtenerFacturasClientesEntreFechas(
            @Parameter(description = "fecha inicial, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @Parameter(description = "fecha final, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaClienteService.obtenerFacturasClientesEntreFechas(fechaFacturaInicial, fechaFacturaFinal);
    }
    @Operation(summary = "Obtener una lista con todas las facturas de clientes entre fechas por cliente", description = "Obtener una lista con todas las facturas de clientes entre fechas por cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Facturas de cliente obtenidas correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaClientesBusquedasDTO.class))
                    })
    })
    @GetMapping("/{idCliente}/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaClientesBusquedasDTO> obtenerFacturasPorClienteEntreFechas(
            @Parameter(description = "id del propietario", required = true)
            @PathVariable Long idCliente,
            @Parameter(description = "fecha inicial, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @Parameter(description = "fecha final, formato aaaa-mm-dd", required = true)
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

            return facturaClienteService.obtenerFacturasPorClienteEntreFechas(idCliente, fechaFacturaInicial, fechaFacturaFinal);
    }
    @Operation(summary = "Obtener una factura de cliente por su id", description = "Obtener una factura de cliente por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Factura de cliente obtenida correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = FacturaClientesBusquedasDTO.class))
                    })
    })
    @GetMapping("/{id}")
    public ResponseEntity<FacturaClientesBusquedasDTO> obtenerFacturaClientePorId(
            @Parameter(description = "id de la factura", required = true)
            @PathVariable Long id) {

        return new ResponseEntity<>(facturaClienteService.findById(id), HttpStatus.OK);
    }

    //Obtener la ultima factura de cliente
    @GetMapping("/ultima-factura")
    public ResponseEntity<FacturaClientesBusquedasDTO> obtenerUltimaFactura() {

        return new ResponseEntity<>(facturaClienteService.obtenerUltimaFacturaCliente(), HttpStatus.OK);
    }

    //Modificar una factura de cliente
    @PutMapping("/modificarFactura/{idOrdenReparacion}")
    public ResponseEntity<FacturaClienteDTO> modificarFacturaCliente(@Valid @RequestBody FacturaClienteDTO facturaClienteDTO,
                                                                     @PathVariable Long idOrdenReparacion) {
        return new ResponseEntity<>(facturaClienteService.modificarFacturaCliente(facturaClienteDTO, idOrdenReparacion), HttpStatus.OK);
    }

    //Modificar una factura de cliente sin cambiar la asignacion de la orden de reparacion
    @PutMapping("/modificarFactura")
    public ResponseEntity<FacturaClienteDTO> modificarFacturaCliente(@Valid @RequestBody FacturaClienteDTO facturaClienteDTO) {

        return new ResponseEntity<>(facturaClienteService.modificarFacturaClienteNoOR(facturaClienteDTO), HttpStatus.OK);
    }

    //Eliminar una factura de cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFacturaCliente(@PathVariable Long id) {

        return new ResponseEntity<>(facturaClienteService.eliminarFacturaCliente(id), HttpStatus.OK);
    }
}
