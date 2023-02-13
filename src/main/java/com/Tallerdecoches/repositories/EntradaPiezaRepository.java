package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.EntradaPieza;
import com.Tallerdecoches.entities.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface EntradaPiezaRepository extends JpaRepository<EntradaPieza, Long> {

    @Transactional(readOnly = true)
    boolean existsByNumeroAlbaran(String numeroAlbaran);
}
