package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.EntradaPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EntradaPiezaRepository extends JpaRepository<EntradaPieza, Long> {

}
