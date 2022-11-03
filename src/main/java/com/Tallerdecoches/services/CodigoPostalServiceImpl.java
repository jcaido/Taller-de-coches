package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class CodigoPostalServiceImpl implements CodigoPostalService{

    private final CodigoPostalRepository codigoPostalRepository;
    private final PropietarioService propietarioService;
    private  final ModelMapper modelMapper;

    public CodigoPostalServiceImpl(CodigoPostalRepository codigoPostalRepository, PropietarioService propietarioService, PropietarioService propietarioService1, ModelMapper modelMapper) {
        this.codigoPostalRepository = codigoPostalRepository;
        this.propietarioService = propietarioService1;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<CodigoPostalDTO> crearCodigoPostal(CodigoPostalDTO codigoPostalDTO) {

        if (codigoPostalDTO.getId() != null)
            throw new BadRequestCreacionException("Codigo Postal", "id");

        if (existsByCodigo(codigoPostalDTO.getCodigo()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero del codigo postal ya existe");

        if (existsByLocalidad(codigoPostalDTO.getLocalidad()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la localidad del codigo postal ya existe");

        CodigoPostal codigoPostal = mapearEntidad(codigoPostalDTO);
        CodigoPostal nuevoCodigoPostal = codigoPostalRepository.save(codigoPostal);
        CodigoPostalDTO codigoPostalRespuesta = mapearDTO(nuevoCodigoPostal);

        return new ResponseEntity<>(codigoPostalRespuesta, HttpStatus.CREATED);
    }

    @Override
    public boolean existsByCodigo(String codigo) {
        return codigoPostalRepository.existsByCodigo(codigo);
    }

    @Override
    public boolean existsByLocalidad(String localidad) {
        return codigoPostalRepository.existsByLocalidad(localidad);
    }

    //metodo para convertir una entidad CodigoPostal a codigoPostalDT
    private CodigoPostalDTO mapearDTO(CodigoPostal codigoPostal) {
        CodigoPostalDTO codigoPostalDTO = modelMapper.map(codigoPostal, CodigoPostalDTO.class);

        return codigoPostalDTO;
    }

    //metodo para convertir un codigoPostalDTO a una entidad CodigoPostal
    private CodigoPostal mapearEntidad(CodigoPostalDTO codigoPostalDTO) {
        CodigoPostal codigoPostal = modelMapper.map(codigoPostalDTO, CodigoPostal.class);

        return codigoPostal;
    }

    @Override
    public List<CodigoPostalDTO> findAll() {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findAll();

        return codigosPostales.stream().map(publicacion-> mapearDTO(publicacion)).toList();
    }

    @Override
    public ResponseEntity<CodigoPostalDTO> findById(Long id) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(id);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "id", String.valueOf(id));

        return new ResponseEntity<>(mapearDTO(codigoPostal.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CodigoPostalDTO> findByCodigo(String codigo) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByCodigo(codigo);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "codigo", codigo);

        return new ResponseEntity<>(mapearDTO(codigoPostal.get()), HttpStatus.OK);
    }

    @Override
    public List<CodigoPostalDTO> findByProvincia(String provincia) {
        List<CodigoPostal> codigosPostales = codigoPostalRepository.findByProvincia(provincia);

        return codigosPostales.stream().map(publicacion-> mapearDTO(publicacion)).toList();
    }

    @Override
    public ResponseEntity<CodigoPostalDTO> findByLocalidad(String localidad) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findByLocalidad(localidad);

        if (!codigoPostal.isPresent())
            throw new ResourceNotFoundException("Codigo Postal", "localidad", localidad);

        return new ResponseEntity<>(mapearDTO(codigoPostal.get()), HttpStatus.OK);
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

        if (!validacionUniqueLocalidad(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La localidad ya existe");

        if (!validacionUniqueCodigo(codigoPostalDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero del codigo postal ya existe");

        CodigoPostal codigoPostal = codigoPostalRepository.findById(codigoPostalDTO.getId()).get();
        codigoPostal.setCodigo(codigoPostalDTO.getCodigo());
        codigoPostal.setLocalidad(codigoPostal.getLocalidad());
        codigoPostal.setProvincia(codigoPostalDTO.getProvincia());

        CodigoPostal codigoPostalModificado = codigoPostalRepository.save(codigoPostal);

        return new ResponseEntity<>(mapearDTO(codigoPostalModificado), HttpStatus.OK);
    }
    private boolean validacionUniqueLocalidad(CodigoPostalDTO codigoPostalDTO) {
        if (!isCodigoHaCambiado(codigoPostalDTO)
                && isLocalidadHaCambiado(codigoPostalDTO)
                && existsByLocalidad(codigoPostalDTO.getLocalidad()) ||
                isCodigoHaCambiado(codigoPostalDTO)
                        && !existsByCodigo(codigoPostalDTO.getCodigo())
                        && isLocalidadHaCambiado(codigoPostalDTO)
                        && existsByLocalidad(codigoPostalDTO.getLocalidad()))
            return false;

        return true;
    }

    private boolean isCodigoHaCambiado(CodigoPostalDTO codigoPostalDTO) {
        String codigo = obtenerCodigoPostal(codigoPostalDTO).get().getCodigo();

        if (codigo.equals(codigoPostalDTO.getCodigo()))
            return false;

        return true;
    }

    private Optional<CodigoPostal> obtenerCodigoPostal(CodigoPostalDTO codigoPostalDTO) {

        Long codigoPostal_id = codigoPostalDTO.getId();

        return codigoPostalRepository.findById(codigoPostal_id);
    }

    private boolean isLocalidadHaCambiado(CodigoPostalDTO codigoPostalDTO) {

        String localidad = obtenerCodigoPostal(codigoPostalDTO).get().getLocalidad();

        if (localidad.equals(codigoPostalDTO.getLocalidad()))
            return false;

        return true;
    }
    private boolean validacionUniqueCodigo(CodigoPostalDTO codigoPostalDTO) {
        if (isCodigoHaCambiado(codigoPostalDTO) && existsByCodigo(codigoPostalDTO.getCodigo()))
            return false;

        return true;
    }
}

