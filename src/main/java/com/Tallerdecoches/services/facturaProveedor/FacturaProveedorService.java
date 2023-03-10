package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacturaProveedorService {

    ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor);
    List<FacturaProveedorBusquedasDTO> findAll();
    ResponseEntity<FacturaProveedorBusquedasDTO> findById(Long id);
    ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor);
}
