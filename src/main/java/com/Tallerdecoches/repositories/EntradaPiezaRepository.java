package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.DTOs.almacen.MovimientoPiezaDTO;
import com.Tallerdecoches.entities.EntradaPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EntradaPiezaRepository extends JpaRepository<EntradaPieza, Long> {
    @Query("SELECT new com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO(" +
            " e.pieza.referencia, e.pieza.nombre, SUM(e.cantidad))" +
            " FROM EntradaPieza AS e" +
            " WHERE e.albaranProveedor.fechaAlbaran <= :fecha" +
            " GROUP BY e.pieza.referencia, e.pieza.nombre")
    List<MovimientoAlmacenDTO> obtenerTotalEntradasFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT new com.Tallerdecoches.DTOs.almacen.MovimientoPiezaDTO(" +
            " e.albaranProveedor.fechaAlbaran, e.albaranProveedor.proveedor.nombre, e.cantidad)" +
            " FROM EntradaPieza AS e" +
            " WHERE e.pieza.referencia =:referencia")
    List<MovimientoPiezaDTO> obtenerEntradasPorPieza(@Param("referencia") String referencia);

}
