package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaClienteConsultasService {

    private final EntityManager entityManager;

    public FacturaClienteConsultasService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<FacturaCliente> obtenerFacturasClientesEntreFechas(FacturaClienteCrearDTO facturaDTO) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal");
        query.setParameter("fechaInicial", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> facturasYear = query.getResultList();

        return facturasYear;
    }

    public List<FacturaCliente> obtenerFacturaMaximoNumeroFacturaEntreFechas(FacturaClienteCrearDTO facturaDTO) {

        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal " +
                "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
        query.setParameter("fechaInicial", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 01, 01));
        query.setParameter("fechaFinal", LocalDate.of(facturaDTO.getFechaFactura().getYear(), 12, 31));
        List<FacturaCliente> factura = query.getResultList();

        return factura;
    }
}
