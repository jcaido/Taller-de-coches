package com.Tallerdecoches.services.ordenReparacion;

import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;

public interface OrdenReparacionService {

    ResponseEntity<OrdenReparacionDTO> crearOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long idVehiculo);
    List<OrdenReparacionBusquedasDTO> findAll();
    ResponseEntity<OrdenReparacionBusquedasDTO> findById(Long id);
    ResponseEntity<OrdenReparacionBusquedasParcialDTO> findByIdParcial(Long id);
    List<OrdenReparacionBusquedasDTO> findByFechaApertura(LocalDate fechaApertura);
    List<OrdenReparacionBusquedasDTO> findByFechaCierre(LocalDate fechaCierre);
    List<OrdenReparacionBusquedasDTO> findByCerrada(Boolean cerrada);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcial(Boolean cerrada);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorFechaApertura(Boolean cerrada, LocalDate fechaApertura);
    List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorVehiculo(Boolean cerrada, Long id_vehiculo);
    List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(Long id_vehiculo);
    ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long id_vehiculo);
}
