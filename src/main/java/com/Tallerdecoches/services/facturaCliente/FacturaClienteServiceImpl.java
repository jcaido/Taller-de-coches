package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.repositories.FacturaClienteRepository;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import com.Tallerdecoches.repositories.PropietarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;

@Service
public class FacturaClienteServiceImpl implements FacturaClienteService{

    private final PropietarioRepository propietarioRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final FacturaClienteRepository facturaClienteRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    public FacturaClienteServiceImpl(PropietarioRepository propietarioRepository, OrdenReparacionRepository ordenReparacionRepository, FacturaClienteRepository facturaClienteRepository, EntityManager entityManager, ModelMapper modelMapper) {
        this.propietarioRepository = propietarioRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.facturaClienteRepository = facturaClienteRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public ResponseEntity<FacturaClienteDTO> crearFacturaCliente(FacturaClienteCrearDTO facturaClienteCrearDTO, Long idPropietario, Long idOrdenReparacion) {

        Propietario propietario = propietarioRepository.findById(idPropietario).get();
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();

        int year = facturaClienteCrearDTO.getFechaFactura().getYear();
        LocalDate fechaInicial = LocalDate.of(year, 01, 01);
        LocalDate fechaFinal = LocalDate.of(year, 12, 31);

        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                "WHERE fecha_factura >= :fechaInicial " +
                "AND fecha_factura <= :fechaFinal");
        query.setParameter("fechaInicial", fechaInicial);
        query.setParameter("fechaFinal", fechaFinal);
        List<FacturaCliente> facturasYear = query.getResultList();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteCrearDTO, FacturaCliente.class);
        facturaCliente.setPropietario(propietario);
        facturaCliente.setOrdenReparacion(ordenReparacion);

        if (facturasYear.isEmpty()) {

            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(1L);
            facturaClienteRepository.save(facturaCliente);
        } else {
            Query queryMax = entityManager.createNativeQuery("SELECT * FROM facturas_clientes " +
                    "WHERE fecha_factura >= :fechaInicial " +
                    "AND fecha_factura <= :fechaFinal " +
                    "AND numero_factura = (SELECT MAX(numero_factura) FROM facturas_clientes WHERE " +
                    " fecha_factura >= :fechaInicial AND fecha_factura <= :fechaFinal)", FacturaCliente.class);
            queryMax.setParameter("fechaInicial", fechaInicial);
            queryMax.setParameter("fechaFinal", fechaFinal);
            Object facturaNumero = queryMax.getSingleResult();
            FacturaCliente factura = (FacturaCliente) facturaNumero;

            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(factura.getNumeroFactura() + 1);
            facturaClienteRepository.save(facturaCliente);
        }

        return new ResponseEntity<>(modelMapper.map(facturaCliente, FacturaClienteDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<FacturaClientesBusquedasDTO> findAll() {
        List<FacturaCliente> facturasCliente = facturaClienteRepository.findAll();

        return facturasCliente.stream().map(factura -> modelMapper.map(factura, FacturaClientesBusquedasDTO.class)).toList();
    }
}
