package com.Tallerdecoches.services.codigoPostal;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.services.propietario.PropietarioService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class CodigoPostalServiceImpl implements CodigoPostalService {

    private final CodigoPostalRepository codigoPostalRepository;
    private final PropietarioService propietarioService;
    private final CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService;
    private  final ModelMapper modelMapper;

    public CodigoPostalServiceImpl(CodigoPostalRepository codigoPostalRepository, PropietarioService propietarioService, PropietarioService propietarioService1, CodigoPostalValidacionesUniqueService codigoPostalValidacionesUniqueService, ModelMapper modelMapper) {
        this.codigoPostalRepository = codigoPostalRepository;
        this.propietarioService = propietarioService1;
        this.codigoPostalValidacionesUniqueService = codigoPostalValidacionesUniqueService;
        this.modelMapper = modelMapper;
    }

    @Override
    public CodigoPostalDTO crearCodigoPostal(CodigoPostalCrearDTO codigoPostalCrearDTO) {

        if (codigoPostalRepository.existsByCodigo(codigoPostalCrearDTO.getCodigo()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero del codigo postal ya existe");

        if (codigoPostalRepository.existsByLocalidad(codigoPostalCrearDTO.getLocalidad()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la localidad del codigo postal ya existe");

        CodigoPostal codigoPostal = modelMapper.map(codigoPostalCrearDTO, CodigoPostal.class);
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);
        CodigoPostalDTO codigoPostalRespuesta = modelMapper.map(nuevoCodigoPostal, CodigoPostalDTO.class);
        return codigoPostalRespuesta;
    }

    @Override
    public List<CodigoPostalDTO> findAll() {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findAll();

        return  codigosPostales.stream().map(codigoPostal-> modelMapper.map(codigoPostal, CodigoPostalDTO.class)).toList();
    }

    @Override
    public CodigoPostalDTO findById(Long id) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(id);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public CodigoPostalDTO findByCodigo(String codigo) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByCodigo(codigo);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "codigo", codigo);

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public List<CodigoPostalDTO> findByProvincia(String provincia) {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findByProvincia(provincia);

        return codigosPostales.stream().map(codigoPostal-> modelMapper.map(codigoPostal, CodigoPostalDTO.class)).toList();
    }

    @Override
    public CodigoPostalDTO findByLocalidad(String localidad) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByLocalidad(localidad);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "localidad", localidad);

        CodigoPostalDTO codigoPostalEncontrado = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalEncontrado;
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!codigoPostalRepository.existsById(id))
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        if (propietarioService.obtenerPropietariosPorCodigoPostalHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen propietarios relacionados con ese codigo postal");

        codigoPostalRepository.deleteById(id);

        return new ResponseEntity<>("Codigo Postal eliminado con exito", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CodigoPostalDTO> modificarCodigoPostal(CodigoPostalDTO codigoPostalDTO) {

        if (codigoPostalDTO.getId() == null)
            throw new BadRequestModificacionException("Codigo Postal", "id");

        if (!codigoPostalRepository.existsById(codigoPostalDTO.getId()))
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(codigoPostalDTO.getId()));

        if (!codigoPostalValidacionesUniqueService.validacionUniqueLocalidad(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La localidad ya existe");

        if (!codigoPostalValidacionesUniqueService.validacionUniqueCodigo(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo del Codigo Postal ya existe");

        CodigoPostal codigoPostal = codigoPostalRepository.findById(codigoPostalDTO.getId()).get();
        codigoPostal.setCodigo(codigoPostalDTO.getCodigo());
        codigoPostal.setLocalidad(codigoPostalDTO.getLocalidad());
        codigoPostal.setProvincia(codigoPostalDTO.getProvincia());

        CodigoPostal codigoPostalModificado = codigoPostalRepository.save(codigoPostal);

        return new ResponseEntity<>(modelMapper.map(codigoPostal, CodigoPostalDTO.class), HttpStatus.OK);
    }
}

