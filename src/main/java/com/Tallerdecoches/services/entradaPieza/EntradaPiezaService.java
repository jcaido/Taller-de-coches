package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntradaPiezaService {

    ResponseEntity<EntradaPiezaDTO> crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idProveedor, Long idPieza);
    List<EntradaPiezaBusquedasDTO> findAll();
    ResponseEntity<EntradaPiezaBusquedasDTO> findById(Long id);
    List<EntradaPiezaBusquedasDTO> obtenerEntradasPorProveedorHQL(Long id_proveedor);
    ResponseEntity<EntradaPiezaDTO> modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idProveedor, Long idPieza);
    ResponseEntity<String> deleteById(Long  id);
}
