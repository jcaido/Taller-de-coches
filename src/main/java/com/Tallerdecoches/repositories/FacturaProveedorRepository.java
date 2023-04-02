package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.FacturaProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, Long> {

    FacturaProveedor findByNumeroFactura(String numeroFactura);
    boolean existsById(Long id);
}
