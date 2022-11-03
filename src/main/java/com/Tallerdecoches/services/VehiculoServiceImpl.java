package com.Tallerdecoches.services;

import com.Tallerdecoches.DTOs.VehiculoDTO;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.repositories.VehiculoRepository;
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
public class VehiculoServiceImpl implements VehiculoService{

    private final VehiculoRepository vehiculoRepository;
    private final PropietarioRepository propietarioRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, PropietarioRepository propietarioRepository, EntityManager entityManager, ModelMapper modelMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.propietarioRepository = propietarioRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<VehiculoDTO> crearVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario) {

        if (vehiculoDTO.getId() != null)
            throw new BadRequestCreacionException("Vehiculo", "id");

        if (vehiculoRepository.existsByMatricula(vehiculoDTO.getMatricula()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = mapearEntidad(vehiculoDTO);
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setPropietario(propietario);
        vehiculoRepository.save(vehiculo);

        return new ResponseEntity<>(mapearDTO(vehiculo), HttpStatus.CREATED);
    }

    //metodo para convertir una entidad Vehiculo a VehiculoDTO
    private VehiculoDTO mapearDTO(Vehiculo vehiculo) {
        VehiculoDTO vehiculoDTO = modelMapper.map(vehiculo, VehiculoDTO.class);

        return vehiculoDTO;
    }

    //metodo para convertir un vehiculoDTO a una entidad Vehiculo
    private Vehiculo mapearEntidad(VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = modelMapper.map(vehiculoDTO, Vehiculo.class);

        return vehiculo;
    }

    @Override
    public List<VehiculoDTO> findAll() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(vehiculo-> mapearDTO(vehiculo)).toList();
    }

    @Override
    public ResponseEntity<VehiculoDTO> findById(Long id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(id));

        return new ResponseEntity<>(mapearDTO(vehiculo.get()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehiculoDTO> findByMatricula(String matricula) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByMatricula(matricula);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "matricula", matricula);

        return new ResponseEntity<>(mapearDTO(vehiculo.get()), HttpStatus.OK);
    }

    @Override
    public List<VehiculoDTO> findByMarca(String marca) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarca(marca);

        return vehiculos.stream().map(vehiculo-> mapearDTO(vehiculo)).toList();
    }

    @Override
    public List<VehiculoDTO> findByMarcaAndModelo(String marca, String modelo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(marca, modelo);

        return vehiculos.stream().map(vehiculo-> mapearDTO(vehiculo)).toList();
    }

    @Override
    public void deleteById(Long id) {
        vehiculoRepository.deleteById(id);
    }

    @Override
    public List<VehiculoDTO> obtenerVehiculosPorPropietarioSQL(Long id_propietario) {

        Query query = entityManager.createNativeQuery("SELECT * FROM vehiculos WHERE propietario_id = :id", Vehiculo.class);
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> mapearDTO(vehiculo)).toList();
    }

    @Override
    public List<VehiculoDTO> obtenerVehiculosPorPropietarioHQL(Long id_propietario) {

        Query query = entityManager.createQuery("FROM Vehiculo v WHERE v.propietario.id = :id");
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> mapearDTO(vehiculo)).toList();
    }

    @Override
    public ResponseEntity<VehiculoDTO> modificarVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario) {

        if (vehiculoDTO.getId() == null)
            throw new BadRequestModificacionException("Vehiculo", "id");

        if (!vehiculoRepository.existsById(vehiculoDTO.getId()))
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(vehiculoDTO.getId()));

        if (!validacionUniqueMatricula(vehiculoDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoDTO.getId()).get();
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setMatricula(vehiculoDTO.getMatricula());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setPropietario(propietario);

        Vehiculo vehiculoModificado = vehiculoRepository.save(vehiculo);

        return new ResponseEntity<>(mapearDTO(vehiculoModificado), HttpStatus.OK);
    }

    private boolean validacionUniqueMatricula(VehiculoDTO vehiculoDTO) {

        if (isMatriculaHaCambiado(vehiculoDTO) && vehiculoRepository.existsByMatricula(vehiculoDTO.getMatricula()))
            return false;

        return true;
    }

    private boolean isMatriculaHaCambiado(VehiculoDTO vehiculoDTO) {

        String matricula = obtenerVehiculo(vehiculoDTO).get().getMatricula();

        if (matricula.equals(vehiculoDTO.getMatricula()))
            return false;

        return true;
    }

    private Optional<Vehiculo> obtenerVehiculo(VehiculoDTO vehiculoDTO) {

        Long vehiculo_id = vehiculoDTO.getId();

        return vehiculoRepository.findById(vehiculo_id);
    }

    private boolean validacionPropietario(Long id_propietario) {

        Optional<Propietario> propietario = propietarioRepository.findById(id_propietario);

        if (propietario.isPresent())
            return true;

        return false;
    }
}
