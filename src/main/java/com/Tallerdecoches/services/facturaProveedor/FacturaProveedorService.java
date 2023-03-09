package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import org.springframework.http.ResponseEntity;

public interface FacturaProveedorService {

    ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor);
}
