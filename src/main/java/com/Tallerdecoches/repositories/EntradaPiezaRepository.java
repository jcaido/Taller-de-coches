package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.entities.EntradaPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntradaPiezaRepository extends JpaRepository<EntradaPieza, Long> {

    @Query("SELECT new com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO(" +
        " e.pieza.referencia, e.pieza.nombre, SUM(e.cantidad))" +
            " FROM EntradaPieza AS e" +
            " GROUP BY e.pieza.referencia, e.pieza.nombre")
    List<MovimientoAlmacenDTO> obtenerTotalEntradas();

}
