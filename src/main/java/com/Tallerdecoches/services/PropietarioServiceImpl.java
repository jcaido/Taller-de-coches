package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.services.validacionesUnique.PropietarioModificacionCambiosService;
import com.Tallerdecoches.services.validacionesUnique.PropietarioValidacionesUniqueService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class PropietarioServiceImpl implements PropietarioService{

    private final PropietarioRepository propietarioRepository;
    private final CodigoPostalRepository codigoPostalRepository;
    private final VehiculoService vehiculoService;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final PropietarioValidacionesUniqueService propietarioValidacionesUniqueService;
    private final PropietarioModificacionCambiosService propietarioModificacionCambiosService;

    public PropietarioServiceImpl(PropietarioRepository propietarioRepository, CodigoPostalRepository codigoPostalRepository, VehiculoService vehiculoService, ModelMapper modelMapper, EntityManager entityManager, PropietarioValidacionesUniqueService propietarioValidacionesUniqueService, PropietarioModificacionCambiosService propietarioModificacionCambiosService) {
        this.propietarioRepository = propietarioRepository;
        this.codigoPostalRepository = codigoPostalRepository;
        this.vehiculoService = vehiculoService;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.propietarioValidacionesUniqueService = propietarioValidacionesUniqueService;
        this.propietarioModificacionCambiosService = propietarioModificacionCambiosService;
    }

    @Override
    public ResponseEntity<PropietarioDTO> crearPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal) {

        if (propietarioDTO.getId() != null)
            throw new BadRequestCreacionException("Propietario", "id");

        if (propietarioRepository.existsByDni(propietarioDTO. getDni()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI del propietario ya existe");

        if (!propietarioModificacionCambiosService.validacionCodigoPostal(id_codigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo Postal asociado al propietario no existe");

        Propietario propietario = mapearEntidad(propietarioDTO);
        CodigoPostal codigoPostal = codigoPostalRepository.findById(id_codigoPostal).get();
        propietario.setCodigoPostal(codigoPostal);
        propietarioRepository.save(propietario);

        return new ResponseEntity<>(mapearDTO(propietario), HttpStatus.CREATED);
    }

    //metodo para convertir una entidad Propietario a PropietarioDTO
    private PropietarioDTO mapearDTO(Propietario propietario) {
        PropietarioDTO propietarioDTO = modelMapper.map(propietario, PropietarioDTO.class);

        return propietarioDTO;
    }

    //metodo para convertir un propietarioDTO a una entidad Propietario
    private Propietario mapearEntidad(PropietarioDTO propietarioDTO) {
        Propietario propietario = modelMapper.map(propietarioDTO, Propietario.class);

        return propietario;
    }

    @Override
    public List<PropietarioDTO> findAll() {
        List<Propietario> propietarios = propietarioRepository.findAll();

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public ResponseEntity<PropietarioDTO> findById(Long id) {
        Optional<Propietario> propietario = propietarioRepository.findById(id);

        if (!propietario.isPresent())
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(id));

        return new ResponseEntity<>(mapearDTO(propietario.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PropietarioDTO> findByDni(String dni) {
        Optional<Propietario> propietario = propietarioRepository.findByDni(dni);

        if (!propietario.isPresent())
            throw new ResourceNotFoundException("Propietario", "dni", dni);

        return new ResponseEntity<>(mapearDTO(propietario.get()), HttpStatus.OK);
    }

    @Override
    public List<PropietarioDTO> findByNombre(String nombre) {
        List<Propietario> propietarios = propietarioRepository.findByNombre(nombre);

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public List<PropietarioDTO> findByPrimerApellido(String primerApellido) {
        List<Propietario> propietarios = propietarioRepository.findByPrimerApellido(primerApellido);

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public List<PropietarioDTO> findByNombreAndPrimerApellidoAndSegundoApellido(String nombre, String primerApellido, String segundoApellido) {
        List<Propietario> propietarios = propietarioRepository.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, primerApellido, segundoApellido);

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public List<PropietarioDTO> ObtenerPropietariosPorCodigoPostal(Long id) {
        Optional<CodigoPostal> codigo = codigoPostalRepository.findById(id);

        if (!codigo.isPresent())
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(id));

        List<Propietario> propietarios = codigo.get().getPropietarios();
        return propietarios.stream().map(propietario -> mapearDTO(propietario)).toList();
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!propietarioRepository.existsById(id))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(id));

        if (vehiculoService.obtenerVehiculosPorPropietarioHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen vehiculos relacionado con ese propietario");

        propietarioRepository.deleteById(id);

        return new ResponseEntity<>("Propietario eliminado con exito", HttpStatus.OK);
    }

    @Override
    public List<PropietarioDTO> obtenerPropietariosPorCodigoPostalSQL(Long id_codigoPostal) {

        Query query = entityManager.createNativeQuery(
                "SELECT * FROM propietarios WHERE codigo_postal_id = :id", Propietario.class);
        query.setParameter("id", id_codigoPostal);
        List<Propietario> propietarios = query.getResultList();

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public List<PropietarioDTO> obtenerPropietariosPorCodigoPostalHQL(Long id_codigoPostal) {

        Query query = entityManager.createQuery("FROM Propietario p WHERE p.codigoPostal.id = :id" );
        query.setParameter("id", id_codigoPostal);
        List<Propietario> propietarios = query.getResultList();

        return propietarios.stream().map(propietario-> mapearDTO(propietario)).toList();
    }

    @Override
    public ResponseEntity<PropietarioDTO> modificarPropietario(PropietarioDTO propietarioDTO, Long id_codigoPostal) {

        if (propietarioDTO.getId() == null)
            throw new BadRequestModificacionException("Propietario", "id");

        if (!propietarioRepository.existsById(propietarioDTO.getId()))
            throw new ResourceNotFoundException("Propietario", "id", String.valueOf(propietarioDTO.getId()));

        if (!propietarioValidacionesUniqueService.validacionUniqueDni(propietarioDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El DNI ya existe");

        if (!propietarioModificacionCambiosService.validacionCodigoPostal(id_codigoPostal))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El codigo postal asociado al propietario no existe");

        Propietario propietario = propietarioRepository.findById(propietarioDTO.getId()).get();
        CodigoPostal codigoPostal = codigoPostalRepository.findById(id_codigoPostal).get();
        propietario.setNombre(propietarioDTO.getNombre());
        propietario.setPrimerApellido(propietarioDTO.getPrimerApellido());
        propietario.setSegundoApellido(propietarioDTO.getSegundoApellido());
        propietario.setDni(propietarioDTO.getDni());
        propietario.setDomicilio(propietarioDTO.getDomicilio());
        propietario.setCodigoPostal(codigoPostal);

        Propietario propietarioModificado = propietarioRepository.save(propietario);

        return new ResponseEntity<>(mapearDTO(propietarioModificado), HttpStatus.OK);
    }
}
