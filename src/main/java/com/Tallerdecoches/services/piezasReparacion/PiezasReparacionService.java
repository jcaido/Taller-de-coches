package com.Tallerdecoches.services.piezasReparacion;

import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionCrearDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PiezasReparacionService {

    ResponseEntity<PiezasReparacionDTO> crearPiezasReparacion(PiezasReparacionCrearDTO piezasReparacionCrearDTO, Long id_ordenReparacion, Long id_pieza);
    List<PiezasReparacionBusquedasDTO> findAll();
    ResponseEntity<PiezasReparacionBusquedasDTO> findById(Long id);
    List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(Long id);
    ResponseEntity<String> deleteById(Long id);
}
