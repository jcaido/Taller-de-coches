package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorModificacionCambiosService;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FacturaProveedorServiceImpl implements FacturaProveedorService{

    private final AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService;
    private final FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService;
    private final ModelMapper modelMapper;
    private final ProveedorRepository proveedorRepository;
    private final FacturaProveedorRepository facturaProveedorRepository;
    private final AlbaranProveedorService albaranProveedorService;
    private final EntityManager entityManager;

    public FacturaProveedorServiceImpl(AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService, FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService, ModelMapper modelMapper, ProveedorRepository proveedorRepository, FacturaProveedorRepository facturaProveedorRepository, AlbaranProveedorService albaranProveedorService, EntityManager entityManager) {
        this.albaranProveedorModificacionCambiosService = albaranProveedorModificacionCambiosService;
        this.facturaProveedorModificacionCambiosService = facturaProveedorModificacionCambiosService;
        this.modelMapper = modelMapper;
        this.proveedorRepository = proveedorRepository;
        this.facturaProveedorRepository = facturaProveedorRepository;
        this.albaranProveedorService = albaranProveedorService;
        this.entityManager = entityManager;
    }

    @Override
    public ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor) {

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResourceNotFoundException("Proveedor", "id", String.valueOf(idProveedor));

        if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorCrearDTO.getNumeroFactura(), idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe para ese proveedor");

        FacturaProveedor facturaProveedor = modelMapper.map(facturaProveedorCrearDTO, FacturaProveedor.class);
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        facturaProveedor.setProveedor(proveedor);
        facturaProveedorRepository.save(facturaProveedor);

        return new ResponseEntity<>(modelMapper.map(facturaProveedor, FacturaProveedorDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<FacturaProveedorBusquedasDTO> findAll() {
        List<FacturaProveedor> facturas = facturaProveedorRepository.findAll();

        return facturas.stream().map(factura -> modelMapper.map(factura, FacturaProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<FacturaProveedorBusquedasDTO> findById(Long id) {

        Optional<FacturaProveedor> facturasProveedor = facturaProveedorRepository.findById(id);

        if (!facturasProveedor.isPresent())
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(facturasProveedor, FacturaProveedorBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public List<FacturaProveedorBusquedasDTO> obtenerFacturasProveedorEntreFechas(LocalDate fechaFacturaInicial, LocalDate fechaFacturaFinal) {
        Query query = entityManager.createQuery("FROM FacturaProveedor f " +
                "WHERE f.fechaFactura >= :fechaFacturaInicial AND f.fechaFactura <= :fechaFacturaFinal " +
                "ORDER BY f.fechaFactura asc");
        query.setParameter("fechaFacturaInicial", fechaFacturaInicial);
        query.setParameter("fechaFacturaFinal", fechaFacturaFinal);

        List<FacturaProveedor> facturasProveedor = query.getResultList();

        return facturasProveedor.stream().map(facturaProveedor-> modelMapper.map(facturaProveedor, FacturaProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor) {

        if (facturaProveedorDTO.getId() ==  null)
            throw new BadRequestModificacionException("Factura de proveedor", "id");

        if (!facturaProveedorRepository.existsById(facturaProveedorDTO.getId()))
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(facturaProveedorDTO.getId()));

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la factura no existe");

        if (facturaProveedorModificacionCambiosService.numeroFacturaHaCambiado(facturaProveedorDTO)) {
            if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorDTO.getNumeroFactura(), idProveedor))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe para ese proveedor");
        }

        if (!facturaProveedorModificacionCambiosService.proveedorHaCambiado(facturaProveedorDTO, idProveedor)) {
            if (!facturaProveedorModificacionCambiosService.validacionAlbaranes(facturaProveedorDTO.getId()))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "La factura tiene albaranes asociados");
        }

        FacturaProveedor facturaProveedor = facturaProveedorRepository.findById(facturaProveedorDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();

        facturaProveedor.setProveedor(proveedor);
        facturaProveedor.setFechaFactura(facturaProveedorDTO.getFechaFactura());
        facturaProveedor.setNumeroFactura(facturaProveedorDTO.getNumeroFactura());
        facturaProveedor.setTipoIVA(facturaProveedorDTO.getTipoIVA());
        facturaProveedor.setContabilizada(facturaProveedorDTO.getContabilizada());

        facturaProveedorRepository.save(facturaProveedor);

        return new ResponseEntity<>(modelMapper.map(facturaProveedor, FacturaProveedorDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!facturaProveedorRepository.existsById(id))
            throw new ResourceNotFoundException("Factura", "id", String.valueOf(id));

        if (albaranProveedorService.obtenerAlbaranesProveedorPorFacturaProveedorHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen albaranes asociados con esa factura de proveedor");

        //TODO: Validad que la factura no esté contabilizada

        facturaProveedorRepository.deleteById(id);

        return new ResponseEntity<>("Factura de proveedor eliminada con exito", HttpStatus.OK);
    }
}
