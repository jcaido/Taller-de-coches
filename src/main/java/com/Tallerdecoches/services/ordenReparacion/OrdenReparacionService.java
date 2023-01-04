package com.Tallerdecoches.services.ordenReparacion;

import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrdenReparacionService {

    ResponseEntity<OrdenReparacionDTO> crearOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long idVehiculo);
    List<OrdenReparacionBusquedasDTO> findAll();
    ResponseEntity<OrdenReparacionBusquedasDTO> findById(Long id);
    List<OrdenReparacionBusquedasDTO> findByFechaApertura(LocalDate fechaApertura);
    List<OrdenReparacionBusquedasDTO> findByFechaCierre(LocalDate fechaCierre);
    List<OrdenReparacionBusquedasDTO> findByCerrada(Boolean cerrada);
    List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(Long id_vehiculo);
}
