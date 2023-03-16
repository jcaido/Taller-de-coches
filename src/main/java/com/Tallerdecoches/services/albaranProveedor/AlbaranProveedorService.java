package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AlbaranProveedorService {
    ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(AlbaranProveedorCrearDTO albaranProveedorCrearDTO, Long idProveedor);
    List<AlbaranProveedorBusquedasDTO> findAll();
    List<AlbaranProveedorBusquedasParcialDTO> findAllParcial();
    ResponseEntity<AlbaranProveedorBusquedasDTO> findById(Long id);
    List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorPorProveedorHQL(Long idProveedor);
    List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesPtesFacturarPorProveedorHQL(Long idProveedor);
    List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorPorFacturaProveedorHQL(Long idFactura);
    ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor);
    ResponseEntity<AlbaranProveedorDTO> facturarAlbaranProveedor(Long idAlbaran, Long idFactura);
    ResponseEntity<AlbaranProveedorDTO> noFacturarAlbaranProveedorFacturado(Long idAlbaran);
    ResponseEntity<String> deleteById(Long  id);
}
