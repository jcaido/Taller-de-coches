package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.repositories.FacturaClienteRepository;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class FacturaClienteValidacionesService {

    private final EntityManager entityManager;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final FacturaClienteConsultasService facturaClienteConsultasService;
    private final FacturaClienteRepository facturaClienteRepository;

    public FacturaClienteValidacionesService(EntityManager entityManager, OrdenReparacionRepository ordenReparacionRepository, FacturaClienteConsultasService facturaClienteConsultasService, FacturaClienteRepository facturaClienteRepository) {
        this.entityManager = entityManager;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.facturaClienteConsultasService = facturaClienteConsultasService;
        this.facturaClienteRepository = facturaClienteRepository;
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

    public boolean validacionOrdenReparacionCerrada(Long idOrdenReparacion) {
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();

        if (ordenReparacion.getCerrada().equals(true))
            return false;

        return true;
    }

    public boolean validacionFechaFacturaClienteCrear(FacturaClienteCrearDTO facturaClienteCrearDTO) {
        if (!facturaClienteConsultasService.obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).isEmpty()
            && facturaClienteCrearDTO.getFechaFactura()
                .isBefore(facturaClienteConsultasService
                        .obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).get(0).getFechaFactura()))
            return false;

        return true;
    }
    public boolean facturaAnterior(FacturaClienteDTO facturaClienteDTO) {
        if (facturaClienteConsultasService.obtenerFacturaAnterior(facturaClienteDTO) != null)
            return true;

        return false;
    }

    public boolean facturaPosterior(FacturaClienteDTO facturaClienteDTO) {
        if (facturaClienteConsultasService.obtenerFacturaPosterior(facturaClienteDTO) != null)
            return true;

        return false;
    }

    public boolean validacionOrdenReparacionFacturadaModificar(Long idOrdenReparacion, FacturaClienteDTO facturaClienteDTO) {
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaClienteAModificar = facturaClienteRepository.findById(facturaClienteDTO.getId()).get();

        if (ordenReparacion.getFacturada().equals(true) && facturaClienteAModificar.getOrdenReparacion().getId() != idOrdenReparacion)
            return false;

        return true;
    }
}
