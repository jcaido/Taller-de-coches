package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EntradaPiezaService {

    ResponseEntity<EntradaPiezaDTO> crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idPieza);
    List<EntradaPiezaBusquedasDTO> findAll();
    ResponseEntity<EntradaPiezaBusquedasDTO> findById(Long id);
    List<EntradaPiezaBusquedasDTO> obtenerEntradasPorPiezaHQL(Long id_pieza);
    List<EntradaPiezaBusquedasDTO> obtenerEntradasPiezasPorAlbaranProveedorHQL(Long id_pieza);
    ResponseEntity<EntradaPiezaDTO> modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idPieza);
    ResponseEntity<String> deleteById(Long id);
}
