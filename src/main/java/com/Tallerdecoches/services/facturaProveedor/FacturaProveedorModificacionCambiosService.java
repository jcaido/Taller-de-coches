package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class FacturaProveedorModificacionCambiosService {

    private final EntityManager entityManager;

    private final FacturaProveedorRepository facturaProveedorRepository;

    public FacturaProveedorModificacionCambiosService(EntityManager entityManager, FacturaProveedorRepository facturaProveedorRepository) {
        this.entityManager = entityManager;
        this.facturaProveedorRepository = facturaProveedorRepository;
    }

    public boolean validacionNumeroFactura(String numeroFactura, Long idProveedor) {
        //List<FacturaProveedor> facturasProveedor = facturaProveedorRepository.findByNumeroFactura(numeroFactura);

        Query query = entityManager.createQuery("FROM FacturaProveedor f WHERE f.numeroFactura = :numeroFactura" +
                " AND f.proveedor.id = :idProveedor");
        query.setParameter("numeroFactura", numeroFactura);
        query.setParameter("idProveedor", idProveedor);
        List<FacturaProveedor> facturasProveedor = query.getResultList();

        if (facturasProveedor.size() > 0)
            return false;

        return true;
    }
}
