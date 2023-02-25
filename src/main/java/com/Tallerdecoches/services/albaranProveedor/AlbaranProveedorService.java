package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import org.springframework.http.ResponseEntity;

public interface AlbaranProveedorService {
    ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor);
}
