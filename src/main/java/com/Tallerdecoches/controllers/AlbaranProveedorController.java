package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.albaranProveedor.*;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/albaranProveedor")
public class AlbaranProveedorController {

    private final AlbaranProveedorService albaranProveedorService;

    public AlbaranProveedorController(AlbaranProveedorService albaranProveedorService) {
        this.albaranProveedorService = albaranProveedorService;
    }

    //crear una nuevo Albarán de Proveedor
    @PostMapping("/{idProveedor}")
    public ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(@Valid @RequestBody AlbaranProveedorCrearDTO albaranProveedorCrearDTO
            , @PathVariable Long idProveedor) {

        return albaranProveedorService.crearAlbaranProveedor(albaranProveedorCrearDTO, idProveedor);
    }

    //Obtener una lista con todos los albaranes de proveedor
    @GetMapping
    public List<AlbaranProveedorBusquedasDTO> obtenerTodosLosAlbaranesProveedor() {

        return albaranProveedorService.findAll();
    }

    //Obtener una lista con todos los albaranes de proveedor, campos id, fecha, numero y nombre del proveedor
    @GetMapping("/parcial")
    public List<AlbaranProveedorBusquedasParcialDTO> obtenerTodosLosAlbaranesProveedorParcial() {

        return albaranProveedorService.findAllParcial();
    }

    //Obtener un albarán de proveedor por su id
    @GetMapping("/{id}")
    public ResponseEntity<AlbaranProveedorBusquedasDTO> obtenerAlbaranProveedorPorId(@PathVariable Long id) {

        return albaranProveedorService.findById(id);
    }

    //Obtener albaranes no facturados de un proveedor
    @GetMapping("/no-facturados/{idProveedor}")
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorNoFacturados(@PathVariable Long idProveedor) {

        return albaranProveedorService.obtenerAlbaranesPtesFacturarPorProveedorHQL(idProveedor);
    }

    //Obtener albaranes asignados a una factura de proveedor
    @GetMapping("/factura/{idFactura}")
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesPorFacturaProveedor(@PathVariable Long idFactura) {

        return albaranProveedorService.obtenerAlbaranesProveedorPorFacturaProveedorHQL(idFactura);
    }

    //Modificar un albarán de proveedor
    @PutMapping("/{idProveedor}")
    public ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(@Valid @RequestBody AlbaranProveedorDTO albaranProveedorDTO, @PathVariable Long idProveedor) {

        return albaranProveedorService.modificarAlbaranProveedor(albaranProveedorDTO, idProveedor);
    }

    //Facturar un albaran de proveedor
    @PutMapping("/facturarAlbaran/{idAlbaran}/{idFactura}")
    public ResponseEntity<AlbaranProveedorDTO> facturarAlbaranProveedor(@PathVariable Long idAlbaran, @PathVariable Long idFactura) {

        return albaranProveedorService.facturarAlbaranProveedor(idAlbaran, idFactura);
    }

    //Eliminar un albarán de proveedor
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarAlbaranProveedor(@PathVariable Long id) {

        return albaranProveedorService.deleteById(id);
    }
}
