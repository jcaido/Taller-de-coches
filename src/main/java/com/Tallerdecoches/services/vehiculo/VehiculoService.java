package com.Tallerdecoches.services.vehiculo;

import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoCrearDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface VehiculoService {
    VehiculoDTO crearVehiculo(VehiculoCrearDTO vehiculoCrearDTO, Long id_propietario);
    List<VehiculoBusquedasDTO> findAll();
    List<VehiculoBusquedasParcialDTO> findAllPartial();
    VehiculoBusquedasDTO findById(Long id);
    ResponseEntity<VehiculoBusquedasDTO> findByMatricula(String matricula);
    List<VehiculoBusquedasDTO> findByMarca(String marca);
    List<VehiculoBusquedasDTO> findByMarcaAndModelo(String marca, String modelo);
    List<VehiculoBusquedasParcialDTO> findByMarcaModeloPartial(String marca, String modelo);
    ResponseEntity<String> deleteById(Long  id);
    List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioSQL(Long id_propietario);
    List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioHQL(Long id_propietario);
    List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorPropietarioHQLParcial(Long id_propietario);
    ResponseEntity<VehiculoDTO> modificarVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario);
}
