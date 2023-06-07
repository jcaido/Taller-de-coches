package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FacturaClienteService {

    ResponseEntity<FacturaClienteDTO> crearFacturaCliente(FacturaClienteCrearDTO facturaClienteCrearDTO, Long idPropietario, Long idOrdenReparacion);
    List<FacturaClientesBusquedasDTO> findAll();
    ResponseEntity<FacturaClientesBusquedasDTO> findById(Long id);
    ResponseEntity<FacturaClientesBusquedasDTO> obtenerUltimaFacturaCliente();
}
