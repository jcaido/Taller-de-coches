package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface FacturaProveedorService {

    ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor);
    List<FacturaProveedorBusquedasDTO> findAll();
    ResponseEntity<FacturaProveedorBusquedasDTO> findById(Long id);
    List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedorEntreFechas(LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal);
    ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor);
    ResponseEntity<String> deleteById(Long id);
}
