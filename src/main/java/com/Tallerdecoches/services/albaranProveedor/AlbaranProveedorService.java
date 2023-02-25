package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorBusquedasDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbaranProveedorService {
    ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(AlbaranProveedorCrearDTO albaranProveedorCrearDTO, Long idProveedor);
    List<AlbaranProveedorBusquedasDTO> findAll();
    ResponseEntity<AlbaranProveedorBusquedasDTO> findById(Long id);
    List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorPorProveedorHQL(Long idProveedor);
    ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor);
    ResponseEntity<String> deleteById(Long  id);
}
