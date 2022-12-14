package com.Tallerdecoches.services.vehiculo;

import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.repositories.VehiculoRepository;
import com.Tallerdecoches.services.ordenReparacion.OrdenReparacionService;
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
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final PropietarioRepository propietarioRepository;
    private final EntityManager entityManager;
    private final VehiculoValidacionesUniqueService vehiculoValidacionesUniqueService;
    private final VehiculoModificacionCambiosService vehiculoModificacionCambiosService;
    private final OrdenReparacionService ordenReparacionService;
    private final ModelMapper modelMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, PropietarioRepository propietarioRepository, EntityManager entityManager, VehiculoValidacionesUniqueService vehiculoValidacionesUniqueService, VehiculoModificacionCambiosService vehiculoModificacionCambiosService, OrdenReparacionService ordenReparacionService, ModelMapper modelMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.propietarioRepository = propietarioRepository;
        this.entityManager = entityManager;
        this.vehiculoValidacionesUniqueService = vehiculoValidacionesUniqueService;
        this.vehiculoModificacionCambiosService = vehiculoModificacionCambiosService;
        this.ordenReparacionService = ordenReparacionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<VehiculoDTO> crearVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario) {

        if (vehiculoDTO.getId() != null)
            throw new BadRequestCreacionException("Vehiculo", "id");

        if (vehiculoRepository.existsByMatricula(vehiculoDTO.getMatricula()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!vehiculoModificacionCambiosService.validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = modelMapper.map(vehiculoDTO, Vehiculo.class);
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setPropietario(propietario);
        vehiculoRepository.save(vehiculo);

        return new ResponseEntity<>(modelMapper.map(vehiculo, VehiculoDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<VehiculoBusquedasDTO> findAll() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> findAllPartial() {
        List<Vehiculo> vehiculos = vehiculoRepository.findAll();

        return vehiculos.stream().map(vehiculo -> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public ResponseEntity<VehiculoBusquedasDTO> findById(Long id) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findById(id);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(vehiculo, VehiculoBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<VehiculoBusquedasDTO> findByMatricula(String matricula) {
        Optional<Vehiculo> vehiculo = vehiculoRepository.findByMatricula(matricula);

        if (!vehiculo.isPresent())
            throw new ResourceNotFoundException("Vehiculo", "matricula", matricula);

        return new ResponseEntity<>(modelMapper.map(vehiculo, VehiculoBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public List<VehiculoBusquedasDTO> findByMarca(String marca) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarca(marca);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasDTO> findByMarcaAndModelo(String marca, String modelo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(marca, modelo);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> findByMarcaModeloPartial(String marca, String modelo) {
        List<Vehiculo> vehiculos = vehiculoRepository.findByMarcaAndModelo(marca, modelo);

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!vehiculoRepository.existsById(id))
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(id));

        if (ordenReparacionService.obtenerOrdenesReparacionPorVehiculo(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen ordenes de reparacion relacionadas con ese vehiculo");

        vehiculoRepository.deleteById(id);

        return new ResponseEntity<>("Vehiculo eliminado con exito", HttpStatus.OK);
    }

    @Override
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioSQL(Long id_propietario) {

        Query query = entityManager.createNativeQuery("SELECT * FROM vehiculos WHERE propietario_id = :id", Vehiculo.class);
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioHQL(Long id_propietario) {

        Query query = entityManager.createQuery("FROM Vehiculo v WHERE v.propietario.id = :id");
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasDTO.class)).toList();
    }

    @Override
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorPropietarioHQLParcial(Long id_propietario) {

        Query query = entityManager.createQuery("FROM Vehiculo v WHERE v.propietario.id = :id");
        query.setParameter("id", id_propietario);
        List<Vehiculo> vehiculos = query.getResultList();

        return vehiculos.stream().map(vehiculo-> modelMapper.map(vehiculo, VehiculoBusquedasParcialDTO.class)).toList();
    }

    @Override
    public ResponseEntity<VehiculoDTO> modificarVehiculo(VehiculoDTO vehiculoDTO, Long id_propietario) {

        if (vehiculoDTO.getId() == null)
            throw new BadRequestModificacionException("Vehiculo", "id");

        if (!vehiculoRepository.existsById(vehiculoDTO.getId()))
            throw new ResourceNotFoundException("Vehiculo", "id", String.valueOf(vehiculoDTO.getId()));

        if (!vehiculoValidacionesUniqueService.validacionUniqueMatricula(vehiculoDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "la matricula ya existe");

        if (!vehiculoModificacionCambiosService.validacionPropietario(id_propietario))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El propietario asociado al vehiculo no existe");

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoDTO.getId()).get();
        Propietario propietario = propietarioRepository.findById(id_propietario).get();
        vehiculo.setMatricula(vehiculoDTO.getMatricula());
        vehiculo.setMarca(vehiculoDTO.getMarca());
        vehiculo.setModelo(vehiculoDTO.getModelo());
        vehiculo.setColor(vehiculoDTO.getColor());
        vehiculo.setPropietario(propietario);

        Vehiculo vehiculoModificado = vehiculoRepository.save(vehiculo);

        return new ResponseEntity<>(modelMapper.map(vehiculo, VehiculoDTO.class), HttpStatus.OK);
    }
}
