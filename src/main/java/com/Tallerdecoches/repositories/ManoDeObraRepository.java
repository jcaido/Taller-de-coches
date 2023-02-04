package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.ManoDeObra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ManoDeObraRepository extends JpaRepository<ManoDeObra, Long> {

    @Transactional(readOnly = true)
    boolean existsByprecioHoraClienteTaller(Double precio);
}
