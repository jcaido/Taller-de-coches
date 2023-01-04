package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.PiezasReparacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiezasReparacionRepository extends JpaRepository<PiezasReparacion, Long> {
}
