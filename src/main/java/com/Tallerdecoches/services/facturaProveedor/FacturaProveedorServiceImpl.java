package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorModificacionCambiosService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaProveedorServiceImpl implements FacturaProveedorService{

    private final AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService;
    private final FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService;
    private final ModelMapper modelMapper;
    private final ProveedorRepository proveedorRepository;
    private final FacturaProveedorRepository facturaProveedorRepository;

    public FacturaProveedorServiceImpl(AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService, FacturaProveedorModificacionCambiosService facturaProveedorModificacionCambiosService, ModelMapper modelMapper, ProveedorRepository proveedorRepository, FacturaProveedorRepository facturaProveedorRepository) {
        this.albaranProveedorModificacionCambiosService = albaranProveedorModificacionCambiosService;
        this.facturaProveedorModificacionCambiosService = facturaProveedorModificacionCambiosService;
        this.modelMapper = modelMapper;
        this.proveedorRepository = proveedorRepository;
        this.facturaProveedorRepository = facturaProveedorRepository;
    }

    @Override
    public ResponseEntity<FacturaProveedorDTO> crearFacturaProveedor(FacturaProveedorCrearDTO facturaProveedorCrearDTO, Long idProveedor) {

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la factura no existe");

        if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorCrearDTO.getNumeroFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe");

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
    public ResponseEntity<FacturaProveedorDTO> modificarFacturaProveedor(FacturaProveedorDTO facturaProveedorDTO, Long idProveedor) {

        if (facturaProveedorDTO.getId() ==  null)
            throw new BadRequestModificacionException("Albarán de proveedor", "id");

        if (!facturaProveedorRepository.existsById(facturaProveedorDTO.getId()))
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(facturaProveedorDTO.getId()));

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la factura no existe");

        if (!facturaProveedorModificacionCambiosService.validacionNumeroFactura(facturaProveedorDTO.getNumeroFactura()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de factura ya existe");

        FacturaProveedor facturaProveedor = facturaProveedorRepository.findById(facturaProveedorDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();

        facturaProveedor.setProveedor(proveedor);
        facturaProveedor.setFechaFactura(facturaProveedorDTO.getFechaFactura());
        facturaProveedor.setNumeroFactura(facturaProveedorDTO.getNumeroFactura());
        facturaProveedor.setContabilizada(facturaProveedorDTO.getContabilizada());

        facturaProveedorRepository.save(facturaProveedor);

        return new ResponseEntity<>(modelMapper.map(facturaProveedor, FacturaProveedorDTO.class), HttpStatus.OK);
    }
}
