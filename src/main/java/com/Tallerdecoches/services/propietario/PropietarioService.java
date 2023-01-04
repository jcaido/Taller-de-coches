package com.Tallerdecoches.services.propietario;

import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface PropietarioService {
    ResponseEntity<PropietarioDTO> crearPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal);
    List<PropietarioBusquedasDTO> findAll();
    List<PropietarioBusquedasParcialDTO> findAllPartial();
    ResponseEntity<PropietarioBusquedasDTO> findById(Long id);
    ResponseEntity<PropietarioBusquedasDTO> findByDni(String dni);
    List<PropietarioBusquedasDTO> findByNombre(String nombre);
    List<PropietarioBusquedasDTO> findByPrimerApellido(String primerApellido);
    List<PropietarioBusquedasParcialDTO> findByPrimerApellidoPartial(String primerApellido);
    List<PropietarioBusquedasDTO> findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido);
    List<PropietarioBusquedasDTO> ObtenerPropietariosPorCodigoPostal(Long id);
    List<PropietarioBusquedasParcialDTO> obtenerPropietariosPorCodigoPostalParcial(Long id);
    ResponseEntity<String> deleteById(Long  id);
    List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostalSQL(Long id_codigo_postal);
    List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostalHQL(Long id_codigo_postal);
    ResponseEntity<PropietarioDTO> modificarPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal);

}
