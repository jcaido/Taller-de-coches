package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.repositories.AlbaranProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class AlbaranProveedorServiceImpl implements  AlbaranProveedorService{

    private final AlbaranProveedorRepository albaranProveedorRepository;
    private final AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService;
    private final ModelMapper modelMapper;
    private final ProveedorRepository proveedorRepository;


    public AlbaranProveedorServiceImpl(AlbaranProveedorRepository albaranProveedorRepository, AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService, ModelMapper modelMapper, ProveedorRepository proveedorRepository) {
        this.albaranProveedorRepository = albaranProveedorRepository;
        this.albaranProveedorModificacionCambiosService = albaranProveedorModificacionCambiosService;
        this.modelMapper = modelMapper;
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public ResponseEntity<AlbaranProveedorDTO> crearAlbaranProveedor(AlbaranProveedorCrearDTO albaranProveedorCrearDTO, Long idProveedor) {

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado al albarán no existe");

        if (!albaranProveedorModificacionCambiosService.validacionNumeroAlbaran(albaranProveedorCrearDTO.getNumeroAlbaran()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de albaran ya existe");

        AlbaranProveedor albaranProveedor = modelMapper.map(albaranProveedorCrearDTO, AlbaranProveedor.class);
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        albaranProveedor.setProveedor(proveedor);
        albaranProveedorRepository.save(albaranProveedor);

        return new ResponseEntity<>(modelMapper.map(albaranProveedor, AlbaranProveedorDTO.class), HttpStatus.CREATED);
    }
}
