package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class FacturaClienteValidacionesService {

    private final EntityManager entityManager;
    private final OrdenReparacionRepository ordenReparacionRepository;

    public FacturaClienteValidacionesService(EntityManager entityManager, OrdenReparacionRepository ordenReparacionRepository) {
        this.entityManager = entityManager;
        this.ordenReparacionRepository = ordenReparacionRepository;
    }

    public boolean validacionPropietario(Long idPropietario) {
        Query query = entityManager.createQuery("FROM Propietario p WHERE p.id = :idPropietario");
        query.setParameter("idPropietario", idPropietario);
        List<Propietario> propietarios = query.getResultList();

        if (propietarios.size() > 0)
            return true;

        return false;
    }

    public boolean validacionOrdenReparacion(Long idOrdenReparacion) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.id = :idOrdenReparacion");
        query.setParameter("idOrdenReparacion", idOrdenReparacion);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        if (ordenesReparacion.size() > 0)
            return true;

        return false;
    }

    public boolean validacionOrdenReparacionPerteneceAPropietario(Long idPropietario, Long idOrdenReparacion) {
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();

        if (!idPropietario.equals(ordenReparacion.getVehiculo().getPropietario().getId()))
            return false;

        return true;
    }

    public boolean validacionOrdenReparacionFacturada(Long idOrdenReparacion) {
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();

        if (ordenReparacion.getFacturada().equals(true))
            return false;

        return true;
    }
}
