package com.Tallerdecoches.services.pieza;

import com.Tallerdecoches.DTOs.pieza.PiezaCrearDTO;
import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PiezaService {

    PiezaDTO crearPieza(PiezaCrearDTO piezaCrearDTO);
    List<PiezaDTO> findAll();
    PiezaDTO findById(Long id);
    PiezaDTO findByReferencia(String referencia);
    List<PiezaDTO> findByNombre (String nombre);
    PiezaDTO modificarPieza(PiezaDTO piezaDTO);
    ResponseEntity<String> deleteById(Long id);
}
