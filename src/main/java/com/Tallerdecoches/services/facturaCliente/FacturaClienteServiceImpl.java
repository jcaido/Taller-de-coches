package com.Tallerdecoches.services.facturaCliente;

import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteCrearDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClienteDTO;
import com.Tallerdecoches.DTOs.facturaCliente.FacturaClientesBusquedasDTO;
import com.Tallerdecoches.entities.FacturaCliente;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
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
import java.util.Optional;

@Service
public class FacturaClienteServiceImpl implements FacturaClienteService{

    private final PropietarioRepository propietarioRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final FacturaClienteRepository facturaClienteRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    private final FacturaClienteConsultasService facturaClienteConsultasService;

    public FacturaClienteServiceImpl(PropietarioRepository propietarioRepository, OrdenReparacionRepository ordenReparacionRepository, FacturaClienteRepository facturaClienteRepository, EntityManager entityManager, ModelMapper modelMapper, FacturaClienteConsultasService facturaClienteConsultasService) {
        this.propietarioRepository = propietarioRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.facturaClienteRepository = facturaClienteRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.facturaClienteConsultasService = facturaClienteConsultasService;
    }

    @Override
    @Transactional
    public ResponseEntity<FacturaClienteDTO> crearFacturaCliente(FacturaClienteCrearDTO facturaClienteCrearDTO, Long idPropietario, Long idOrdenReparacion) {

        Propietario propietario = propietarioRepository.findById(idPropietario).get();
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(idOrdenReparacion).get();
        FacturaCliente facturaCliente = modelMapper.map(facturaClienteCrearDTO, FacturaCliente.class);
        facturaCliente.setPropietario(propietario);
        facturaCliente.setOrdenReparacion(ordenReparacion);

        if (facturaClienteConsultasService.obtenerFacturasClientesEntreFechas(facturaClienteCrearDTO).isEmpty()) {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(1L);
            facturaClienteRepository.save(facturaCliente);
        } else {
            facturaClienteRepository.save(facturaCliente);
            facturaCliente.setNumeroFactura(facturaClienteConsultasService.obtenerFacturaMaximoNumeroFacturaEntreFechas(facturaClienteCrearDTO).getNumeroFactura() + 1);
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
}
