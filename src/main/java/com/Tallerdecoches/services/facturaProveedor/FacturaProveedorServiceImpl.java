package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorCrearDTO;
import com.Tallerdecoches.DTOs.facturaProveedor.FacturaProveedorDTO;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import com.Tallerdecoches.services.albaranProveedor.AlbaranProveedorModificacionCambiosService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}
