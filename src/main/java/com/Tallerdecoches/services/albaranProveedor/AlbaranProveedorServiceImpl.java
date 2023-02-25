package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorCrearDTO;
import com.Tallerdecoches.DTOs.albaranProveedor.AlbaranProveedorDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.AlbaranProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<AlbaranProveedorBusquedasDTO> findAll() {
        List<AlbaranProveedor> ordenes = albaranProveedorRepository.findAll();

        return ordenes.stream().map(orden-> modelMapper.map(orden, AlbaranProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<AlbaranProveedorBusquedasDTO> findById(Long id) {
        Optional<AlbaranProveedor> albaranProveedor = albaranProveedorRepository.findById(id);

        if (!albaranProveedor.isPresent())
            throw new ResourceNotFoundException("Albaran de proveedor", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(albaranProveedor, AlbaranProveedorBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AlbaranProveedorDTO> modificarAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor) {

        if (albaranProveedorDTO.getId() == null)
            throw new BadRequestModificacionException("Albarán de proveedor", "id");

        if (!albaranProveedorRepository.existsById(albaranProveedorDTO.getId()))
            throw new ResourceNotFoundException("Albarán de proveedor", "id", String.valueOf(albaranProveedorDTO.getId()));

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado al albarán no existe");

        if (!albaranProveedorModificacionCambiosService.validacionNumeroAlbaran(albaranProveedorDTO.getNumeroAlbaran()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de albaran ya existe");

        AlbaranProveedor albaranProveedor = albaranProveedorRepository.findById(albaranProveedorDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();

        albaranProveedor.setProveedor(proveedor);
        albaranProveedor.setFechaAlbaran(albaranProveedorDTO.getFechaAlbaran());
        albaranProveedor.setNumeroAlbaran(albaranProveedorDTO.getNumeroAlbaran());
        albaranProveedor.setFacturado(albaranProveedorDTO.getFacturado());

        albaranProveedorRepository.save(albaranProveedor);

        return new ResponseEntity<>(modelMapper.map(albaranProveedor, AlbaranProveedorDTO.class), HttpStatus.OK);
    }
}
