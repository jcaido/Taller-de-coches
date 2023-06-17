package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import com.Tallerdecoches.repositories.FacturaClienteRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaClienteConsultasService {

    private final EntityManager entityManager;
    private final FacturaClienteRepository facturaClienteRepository;

    public FacturaClienteConsultasService(EntityManager entityManager, FacturaClienteRepository facturaClienteRepository) {
        this.entityManager = entityManager;
        this.facturaClienteRepository = facturaClienteRepository;
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

    public void asignarNumeroFacturaCrearFactura(FacturaCliente facturaCliente, FacturaClienteCrearDTO facturaClienteCrearDTO) {
        if (obtenerFacturasClientesEntreFechas(facturaClienteCrearDTO).isEmpty()) {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(1L);
            facturaClienteRepository.save(facturaCliente);
        } else {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).get(0).getNumeroFactura() + 1);
            facturaClienteRepository.save(facturaCliente);
        }
    }

    public List<FacturaCliente> ObtenerUltimaFacturaEntreFechas(LocalDate fechaIncial, LocalDate fechaFinal) {
        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal " +
                "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
        query.setParameter("fechaInicial", fechaIncial);
        query.setParameter("fechaFinal", fechaFinal);
        List<FacturaCliente> factura = query.getResultList();

        return factura;
    }


}
