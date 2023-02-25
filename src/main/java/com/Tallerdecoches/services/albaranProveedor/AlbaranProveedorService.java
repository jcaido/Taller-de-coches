package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbaranProveedorService {
    ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(AlbaranProveedorCrearDTO albaranProveedorCrearDTO, Long idProveedor);
    List<AlbaranProveedorBusquedasDTO> findAll();
    ResponseEntity<AlbaranProveedorBusquedasDTO> findById(Long id);
    ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor);
    ResponseEntity<String> deleteById(Long  id);
}
