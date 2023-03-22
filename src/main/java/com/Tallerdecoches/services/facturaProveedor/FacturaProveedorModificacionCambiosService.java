package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.entities.FacturaProveedor;
import org.springframework.stereotype.Service;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class FacturaProveedorModificacionCambiosService {

    private final EntityManager entityManager;

    public FacturaProveedorModificacionCambiosService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean validacionNumeroFactura(String numeroFactura, Long idProveedor) {

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
