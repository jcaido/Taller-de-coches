package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface CodigoPostalService {

    ResponseEntity<CodigoPostalDTO> crearCodigoPostal(CodigoPostalDTO codigoPostalDTO);
    boolean existsByCodigo(String codigo);
    boolean existsByLocalidad(String localidad);
    List<CodigoPostalDTO> findAll();
    ResponseEntity<CodigoPostalDTO> findById(Long id);
    ResponseEntity<CodigoPostalDTO> findByCodigo(String codigo);
    List<CodigoPostalDTO> findByProvincia (String provincia);
    ResponseEntity<CodigoPostalDTO> findByLocalidad(String localidad);
    ResponseEntity<String> deleteById(Long id);
    ResponseEntity<CodigoPostalDTO> modificarCodigoPostal(CodigoPostalDTO codigoPostalDTO);

}
