package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.FacturaProveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacturaProveedorRepository extends JpaRepository<FacturaProveedor, Long> {

    List<FacturaProveedor> findByNumeroFactura(String numeroFactura);
}