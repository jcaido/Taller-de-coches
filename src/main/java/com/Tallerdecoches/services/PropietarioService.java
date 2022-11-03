package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.PropietarioDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PropietarioService {
    ResponseEntity<PropietarioDTO> crearPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal);
    List<PropietarioDTO> findAll();
    ResponseEntity<PropietarioDTO> findById(Long id);
    ResponseEntity<PropietarioDTO> findByDni(String dni);
    List<PropietarioDTO> findByNombre(String nombre);
    List<PropietarioDTO> findByPrimerApellido(String primerApellido);
    List<PropietarioDTO> findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido);
    List<PropietarioDTO> ObtenerPropietariosPorCodigoPostal(Long id);
    ResponseEntity<String> deleteById(Long  id);
    List<PropietarioDTO> obtenerPropietariosPorCodigoPostalSQL(Long id_codigo_postal);
    List<PropietarioDTO> obtenerPropietariosPorCodigoPostalHQL(Long id_codigo_postal);
    ResponseEntity<PropietarioDTO> modificarPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal);

}
