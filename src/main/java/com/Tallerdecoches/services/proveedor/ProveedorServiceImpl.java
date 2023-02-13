package com.Tallerdecoches.services.proveedor;

import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorBusquedasDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorCrearDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class ProveedorServiceImpl implements ProveedorService{
    private final ProveedorRepository proveedorRepository;
    private final CodigoPostalRepository codigoPostalRepository;
    private final ModelMapper modelMapper;
    private final ProveedorModificacionCambiosService proveedorModificacionCambiosService;
    private final ProveedorValidacionesUniqueService proveedorValidacionesUniqueService;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository, CodigoPostalRepository codigoPostalRepository, ModelMapper modelMapper, ProveedorModificacionCambiosService proveedorModificacionCambiosService, ProveedorValidacionesUniqueService proveedorValidacionesUniqueService) {
        this.proveedorRepository = proveedorRepository;
        this.codigoPostalRepository = codigoPostalRepository;
        this.modelMapper = modelMapper;
        this.proveedorModificacionCambiosService = proveedorModificacionCambiosService;
        this.proveedorValidacionesUniqueService = proveedorValidacionesUniqueService;
    }

    @Override
    public ResponseEntity<ProveedorDTO> crearProveedor(ProveedorCrearDTO proveedorCrearDTO, Long idCodigoPostal) {

        if (proveedorRepository.existsByDniCif(proveedorCrearDTO.getDniCif()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI/CIF del proveedor ya existe");

        if (!proveedorModificacionCambiosService.validacionCodigoPostal(idCodigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo Postal asociado al provedor no existe");

        Proveedor proveedor = modelMapper.map(proveedorCrearDTO, Proveedor.class);
        CodigoPostal codigoPostal = codigoPostalRepository.findById(idCodigoPostal).get();
        proveedor.setCodigoPostal(codigoPostal);
        proveedorRepository.save(proveedor);

        return new ResponseEntity<>(modelMapper.map(proveedor, ProveedorDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<ProveedorBusquedasDTO> findAll() {
        List<Proveedor> proveedores = proveedorRepository.findAll();

        return proveedores.stream().map(proveedor-> modelMapper.map(proveedor, ProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<ProveedorBusquedasDTO> findById(Long id) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(id);

        if (!proveedor.isPresent())
            throw new ResourceNotFoundException("Proveedor", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(proveedor, ProveedorBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProveedorBusquedasDTO> findByDniCif(String dniCif) {
        Optional<Proveedor> proveedor = proveedorRepository.findByDniCif(dniCif);

        if (!proveedor.isPresent())
            throw new ResourceNotFoundException("Proveedor", "dni", dniCif);

        return new ResponseEntity<>(modelMapper.map(proveedor, ProveedorBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProveedorDTO> modificarProveedor(ProveedorDTO proveedorDTO, Long idCodigoPostal) {

        if (proveedorDTO.getId() == null)
            throw new BadRequestModificacionException("Proveedor", "id");

        if (!proveedorRepository.existsById(proveedorDTO.getId()))
            throw new ResourceNotFoundException("Proveedor", "id", String.valueOf(proveedorDTO.getId()));

        if (!proveedorValidacionesUniqueService.validacionUniqueDniCif(proveedorDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI/CIF ya existe");

        if (!proveedorModificacionCambiosService.validacionCodigoPostal(idCodigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo postal asociado al proveedor no existe");

        Proveedor proveedor = proveedorRepository.findById(proveedorDTO.getId()).get();
        CodigoPostal codigoPostal = codigoPostalRepository.findById(idCodigoPostal).get();
        proveedor.setNombre(proveedorDTO.getNombre());
        proveedor.setDniCif(proveedorDTO.getDniCif());
        proveedor.setDomicilio(proveedorDTO.getDomicilio());
        proveedor.setCodigoPostal(codigoPostal);

        Proveedor proveedorModificado = proveedorRepository.save(proveedor);

        return new ResponseEntity<>(modelMapper.map(proveedor, ProveedorDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!proveedorRepository.existsById(id))
            throw new ResourceNotFoundException("Proveedor", "id", String.valueOf(id));

        proveedorRepository.deleteById(id);

        return new ResponseEntity<>("Proveedor eliminado con exito", HttpStatus.OK);
    }
}