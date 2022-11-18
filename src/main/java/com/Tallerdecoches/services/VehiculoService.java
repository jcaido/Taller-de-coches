package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.VehiculoDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface VehiculoService {
    ResponseEntity<VehiculoDTO> crearVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario);
    List<VehiculoDTO> findAll();
    //Optional<Vehiculo> findById(Long id);
    ResponseEntity<VehiculoDTO> findById(Long id);
    ResponseEntity<VehiculoDTO> findByMatricula(String matricula);
    List<VehiculoDTO> findByMarca(String marca);
    List<VehiculoDTO> findByMarcaAndModelo(String marca, String modelo);
    void deleteById(Long id);
    List<VehiculoDTO> obtenerVehiculosPorPropietarioSQL(Long id_propietario);
    List<VehiculoDTO> obtenerVehiculosPorPropietarioHQL(Long id_propietario);
    ResponseEntity<VehiculoDTO> modificarVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario);
}
