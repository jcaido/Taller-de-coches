package com.Tallerdecoches.services.pieza;

import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PiezaService {

    ResponseEntity<PiezaDTO> crearPieza(PiezaDTO piezaDTO);
    List<PiezaDTO> findAll();
    ResponseEntity<PiezaDTO> findById(Long id);
    ResponseEntity<PiezaDTO> findByReferencia(String referencia);
    List<PiezaDTO> findByNombre (String nombre);
    ResponseEntity<PiezaDTO> modificarPieza(PiezaDTO piezaDTO);
    ResponseEntity<String> deleteById(Long id);
}
