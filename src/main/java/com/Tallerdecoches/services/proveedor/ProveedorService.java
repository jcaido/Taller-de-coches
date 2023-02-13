package com.Tallerdecoches.services.proveedor;

import com.Tallerdecoches.DTOs.proveedor.ProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorCrearDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ProveedorService {

    ResponseEntity<ProveedorDTO> crearProveedor(ProveedorCrearDTO proveedorCrearDTO, Long idCodigoPostal);
    List<ProveedorBusquedasDTO> findAll();
    ResponseEntity<ProveedorBusquedasDTO> findById(Long id);
    ResponseEntity<ProveedorBusquedasDTO> findByDniCif(String dniCif);
    ResponseEntity<ProveedorDTO> modificarProveedor(ProveedorDTO proveedorDTO, Long idCodigoPostal);
    ResponseEntity<String> deleteById(Long  id);
}