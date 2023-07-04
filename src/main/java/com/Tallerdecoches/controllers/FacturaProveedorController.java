package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.Tallerdecoches.services.facturaProveedor.FacturaProveedorService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/facturaProveedor")
public class FacturaProveedorController {

    private final FacturaProveedorService facturaProveedorService;

    public FacturaProveedorController(FacturaProveedorService facturaProveedorService) {
        this.facturaProveedorService = facturaProveedorService;
    }

    //crear una nueva Factura de Proveedor
    @PostMapping("/{idProveedor}")
    public ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(@Valid @RequestBody FacturaProveedorCrearDTO facturaProveedorCrearDTO
            , @PathVariable Long idProveedor) {

        return new ResponseEntity<>(facturaProveedorService.crearFacturaProveedor(facturaProveedorCrearDTO, idProveedor), HttpStatus.CREATED);
    }

    //obtener una lista con todas las facturas
    @GetMapping()
    public List<FacturaProveedorBusquedasDTO> obtenerTodasLasFacturasProveedor() {

        return facturaProveedorService.findAll();
    }

    //obtener una factura de proveedor por su id
    @GetMapping("/{id}")
    public ResponseEntity<FacturaProveedorBusquedasDTO> obtenerFacturaProveedorPorId(@PathVariable Long id) {

        return new ResponseEntity<>(facturaProveedorService.findById(id), HttpStatus.OK);
    }

    //Obtener facturas de proveedores entre fechas
    @GetMapping("/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedorEntreFechas(
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaProveedorService.obtenerFacturasProveedoresEntreFechas(fechaFacturaInicial, fechaFacturaFinal);
    }

    //Obtener facturas por proveedor entre fechas
    @GetMapping("/{idProveedor}/{fechaFacturaInicial}/{fechaFacturaFinal}")
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasPorProveedorEntreFechas(
            @PathVariable Long idProveedor,
            @PathVariable(name="fechaFacturaInicial")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaInicial,
            @PathVariable(name="fechaFacturaFinal")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFacturaFinal) {

        return facturaProveedorService.obtenerFacturasPorProveedorEntreFechas(idProveedor, fechaFacturaInicial, fechaFacturaFinal);

    }

    //Modificar una factura de proveedor
    @PutMapping("/{idProveedor}")
    public ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(@Valid @RequestBody FacturaProveedorDTO facturaProveedorDTO, @PathVariable Long idProveedor) {

        return new ResponseEntity<>(facturaProveedorService.modificarFacturaProveedor(facturaProveedorDTO, idProveedor), HttpStatus.OK);
    }

    //Eliminar una factura existente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarFacturaProveedor(@PathVariable Long id) {

        return new ResponseEntity<>(facturaProveedorService.deleteById(id), HttpStatus.OK);
    }
}
