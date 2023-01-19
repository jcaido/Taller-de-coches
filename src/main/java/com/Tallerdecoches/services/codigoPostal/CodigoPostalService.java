package com.Tallerdecoches.services.codigoPostal;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;
public interface CodigoPostalService {

    ResponseEntity<CodigoPostalDTO> crearCodigoPostal(CodigoPostalCrearDTO codigoPostalCrearDTO);
    List<CodigoPostalDTO> findAll();
    ResponseEntity<CodigoPostalDTO> findById(Long id);
    ResponseEntity<CodigoPostalDTO> findByCodigo(String codigo);
    List<CodigoPostalDTO> findByProvincia (String provincia);
    ResponseEntity<CodigoPostalDTO> findByLocalidad(String localidad);
    ResponseEntity<String> deleteById(Long id);
    ResponseEntity<CodigoPostalDTO> modificarCodigoPostal(CodigoPostalDTO codigoPostalDTO);

}
