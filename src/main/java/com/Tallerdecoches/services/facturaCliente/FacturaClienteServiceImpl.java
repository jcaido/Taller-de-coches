package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.FacturaClienteRepository;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import com.Tallerdecoches.repositories.PropietarioRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaClienteServiceImpl implements FacturaClienteService{

    private final PropietarioRepository propietarioRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final FacturaClienteRepository facturaClienteRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final FacturaClienteValidacionesService facturaClienteValidacionesService;
    private final FacturaClienteConsultasService facturaClienteConsultasService;

    public FacturaClienteServiceImpl(PropietarioRepository propietarioRepository, OrdenReparacionRepository ordenReparacionRepository, FacturaClienteRepository facturaClienteRepository, EntityManager entityManager, ModelMapper modelMapper, FacturaClienteValidacionesService facturaClienteValidacionesService, FacturaClienteConsultasService facturaClienteConsultasService) {
        this.propietarioRepository = propietarioRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.facturaClienteRepository = facturaClienteRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.facturaClienteValidacionesService = facturaClienteValidacionesService;
        this.facturaClienteConsultasService = facturaClienteConsultasService;
    }

    @Override
    @Transactional
    public ResponseEntity<FacturaClienteDTO> crearFacturaCliente(FacturaClienteCrearDTO facturaClienteCrearDTO, Long idPropietario, Long idOrdenReparacion) {

        if (!facturaClienteValidacionesService.validacionPropietario(idPropietario))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(idPropietario));

        if (!facturaClienteValidacionesService.validacionOrdenReparacion(idOrdenReparacion))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(idOrdenReparacion));

        if (!facturaClienteValidacionesService.validacionOrdenReparacionPerteneceAPropietario(idPropietario, idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación no pertenece al propietario");

        if (!facturaClienteValidacionesService.validacionOrdenReparacionFacturada(idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación ya está facturada");

        if (!facturaClienteConsultasService.obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).isEmpty()
                && facturaClienteCrearDTO.getFechaFactura()
                .isBefore(facturaClienteConsultasService
                        .obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).get(0).getFechaFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La fecha factura no puede ser inferior a la última factura de la serie");

        Propietario propietario = propietarioRepository.findById(idPropietario).get();
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteCrearDTO, FacturaCliente.class);
        facturaCliente.setPropietario(propietario);
        facturaCliente.setOrdenReparacion(ordenReparacion);
        facturaCliente.setSerie("T" + Integer.toString(facturaClienteCrearDTO.getFechaFactura().getYear()));
        facturaCliente.getOrdenReparacion().setFacturada(true);

        if (facturaClienteConsultasService.obtenerFacturasClientesEntreFechas(facturaClienteCrearDTO).isEmpty()) {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(1L);
            facturaClienteRepository.save(facturaCliente);
        } else {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(facturaClienteConsultasService.obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).get(0).getNumeroFactura() + 1);
            facturaClienteRepository.save(facturaCliente);
        }

        return new ResponseEntity<>(modelMapper.map(facturaCliente, FacturaClienteDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<FacturaClientesBusquedasDTO> findAll() {
        List<FacturaCliente> facturasCliente = facturaClienteRepository.findAll();

        return facturasCliente.stream().map(factura -> modelMapper.map(factura, FacturaClientesBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<FacturaClientesBusquedasDTO> findById(Long id) {

        Optional<FacturaCliente> facturaCliente = facturaClienteRepository.findById(id);

        if (!facturaCliente.isPresent())
            throw new ResourceNotFoundException("Factura de cliente", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(facturaCliente, FacturaClientesBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<FacturaClientesBusquedasDTO> obtenerUltimaFacturaCliente() {

        Query query = entityManager.createNativeQuery("SELECT * FROM facturas_clientes ORDER BY id DESC LIMIT 1", FacturaCliente.class);
        FacturaCliente ultimaFactura = (FacturaCliente) query.getSingleResult();

        return new ResponseEntity<>(modelMapper.map(ultimaFactura, FacturaClientesBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    @Transactional
    public ResponseEntity<FacturaClienteDTO> modificarFacturaCliente(FacturaClienteDTO facturaClienteDTO, Long idOrdenReparacion) {

        if (facturaClienteDTO.getId() ==  null)
            throw new BadRequestModificacionException("Factura de cliente", "id");

        if (!facturaClienteValidacionesService.validacionOrdenReparacion(idOrdenReparacion))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(idOrdenReparacion));

        if (!facturaClienteValidacionesService.validacionOrdenReparacionFacturada(idOrdenReparacion))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La orden de reparación ya está facturada");


        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteDTO, FacturaCliente.class);
        facturaCliente.setOrdenReparacion(ordenReparacion);
        facturaCliente.getOrdenReparacion().setFacturada(true);
        facturaCliente.setTipoIVA(facturaClienteDTO.getTipoIVA());


        //TODO: validar la modificacion de la fecha de la factura
        //TODO: cambiar el estado de la orden de reparacion anterior a no facturada

        return new ResponseEntity<>(modelMapper.map(facturaCliente, FacturaClienteDTO.class), HttpStatus.OK);
    }
}
