package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.entities.PiezasReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PiezasReparacionRepository extends JpaRepository<PiezasReparacion, Long> {

    @Query("SELECT new com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO(" +
        " p.pieza.referencia, p.pieza.nombre, SUM(p.cantidad))" +
            " FROM PiezasReparacion AS p" +
            " GROUP BY p.pieza.referencia, p.pieza.nombre")
    List<MovimientoAlmacenDTO> obtenerTotalPiezasReparacion();

    @Query("SELECT new com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO(" +
            " p.pieza.referencia, p.pieza.nombre, SUM(p.cantidad))" +
            " FROM PiezasReparacion AS p" +
            " WHERE p.ordenReparacion.fechaCierre <= :fecha" +
            " GROUP BY p.pieza.referencia, p.pieza.nombre")
    List<MovimientoAlmacenDTO> obtenerTotalPiezasReparacionFecha(@Param("fecha") LocalDate fecha);
}
