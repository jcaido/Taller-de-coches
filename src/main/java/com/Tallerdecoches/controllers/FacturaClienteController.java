package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.services.facturaCliente.FacturaClienteService;
import org.springframework.format.annotation.DateTimeFormat;
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

    //Crear una factura cliente
    @PostMapping("/nuevaFactura/{idPropietario}/{idOrdenReparacion}")
    public ResponseEntity<FacturaClienteDTO> crearFacturaCliente(@Valid @RequestBody FacturaClienteCrearDTO facturaClienteCrearDTO,
                                                                 @PathVariable Long idPropietario,
                                                                 @PathVariable Long idOrdenReparacion) {

        return facturaClienteService.crearFacturaCliente(facturaClienteCrearDTO, idPropietario, idOrdenReparacion);
    }

    //Obtener todas las facturas cliente
    @GetMapping()
    public List<FacturaClientesBusquedasDTO> obtenerTodasLasFacturasCliente() {

        return facturaClienteService.findAll();
    }

    //Obtener facturas de clientes entre fechas
    @GetMapping("/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaClientesBusquedasDTO> obtenerFacturasClientesEntreFechas(
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaClienteService.obtenerFacturasClientesEntreFechas(fechaFacturaInicial, fechaFacturaFinal);
    }

    //Obtener una factura de cliente por su id
    @GetMapping("/{id}")
    public ResponseEntity<FacturaClientesBusquedasDTO> obtenerFacturaClientePorId(@PathVariable Long id) {

        return facturaClienteService.findById(id);
    }

    //Obtener la ultima factura de cliente
    @GetMapping("/ultima-factura")
    public ResponseEntity<FacturaClientesBusquedasDTO> obtenerUltimaFactura() {

        return facturaClienteService.obtenerUltimaFacturaCliente();
    }

    //Modificar una factura de cliente
    @PutMapping("/modificarFactura/{idOrdenReparacion}")
    public ResponseEntity<FacturaClienteDTO> modificarFacturaCliente(@Valid @RequestBody FacturaClienteDTO facturaClienteDTO,
                                                                     @PathVariable Long idOrdenReparacion) {
        return facturaClienteService.modificarFacturaCliente(facturaClienteDTO, idOrdenReparacion);
    }

    //Modificar una factura de cliente sin cambiar la asignacion de la orden de reparacion
    @PutMapping("/modificarFactura")
    public ResponseEntity<FacturaClienteDTO> modificarFacturaCliente(@Valid @RequestBody FacturaClienteDTO facturaClienteDTO) {
        return facturaClienteService.modificarFacturaClienteNoOR(facturaClienteDTO);
    }

    //Eliminar una factura de cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFacturaCliente(@PathVariable Long id) {
        return facturaClienteService.eliminarFacturaCliente(id);
    }
}
