package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.FacturaCliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaClienteRepository extends JpaRepository<FacturaCliente, Long> {
}
