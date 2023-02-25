package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
}
