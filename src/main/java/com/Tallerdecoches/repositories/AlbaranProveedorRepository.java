package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.CodigoPostal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface AlbaranProveedorRepository extends JpaRepository<AlbaranProveedor, Long> {

    @Transactional(readOnly = true)
    List<AlbaranProveedor> findByNumeroAlbaran(String numeroAlbaran);
    @Transactional(readOnly = true)
    boolean existsById(Long id);
}
