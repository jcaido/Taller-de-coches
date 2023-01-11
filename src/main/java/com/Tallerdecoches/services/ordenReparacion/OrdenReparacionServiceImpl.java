package com.Tallerdecoches.services.ordenReparacion;

import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.exceptions.BadRequestCreacionException;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import com.Tallerdecoches.repositories.PiezaRepository;
import com.Tallerdecoches.repositories.PiezasReparacionRepository;
import com.Tallerdecoches.repositories.VehiculoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrdenReparacionServiceImpl implements OrdenReparacionService {

    private final OrdenReparacionRepository ordenReparacionRepository;
    private final VehiculoRepository vehiculoRepository;
    private final PiezaRepository piezaRepository;
    private final PiezasReparacionRepository piezasReparacionRepository;
    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final OrdenReparacionModificacionCambiosService ordenReparacionModificacionCambiosService;

    public OrdenReparacionServiceImpl(OrdenReparacionRepository ordenReparacionRepository, VehiculoRepository vehiculoRepository, PiezaRepository piezaRepository, PiezasReparacionRepository piezasReparacionRepository, EntityManager entityManager, ModelMapper modelMapper, OrdenReparacionModificacionCambiosService ordenReparacionModificacionCambiosService) {
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.piezaRepository = piezaRepository;
        this.piezasReparacionRepository = piezasReparacionRepository;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.ordenReparacionModificacionCambiosService = ordenReparacionModificacionCambiosService;
    }


    @Override
    public ResponseEntity<OrdenReparacionDTO> crearOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long idVehiculo) {

        if (ordenReparacionDTO.getId() != null)
            throw new BadRequestCreacionException("Orden de reparacion", "id");

        if (!ordenReparacionModificacionCambiosService.validacionVehiculo(idVehiculo))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehiculo asociado a la orden de reparacion no existe");

        OrdenReparacion ordenReparacion = modelMapper.map(ordenReparacionDTO, OrdenReparacion.class);
        Vehiculo vehiculo = vehiculoRepository.findById(idVehiculo).get();
        ordenReparacion.setVehiculo(vehiculo);
        ordenReparacionRepository.save(ordenReparacion);

        return new ResponseEntity<>(modelMapper.map(ordenReparacion, OrdenReparacionDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findAll() {
        List<OrdenReparacion> ordenes = ordenReparacionRepository.findAll();

        return ordenes.stream().map(orden-> modelMapper.map(orden, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<OrdenReparacionBusquedasDTO> findById(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrdenReparacionBusquedasParcialDTO> findByIdParcial(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class), HttpStatus.OK);
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByFechaApertura(LocalDate fechaApertura) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByFechaApertura(fechaApertura);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByFechaCierre(LocalDate fechaCierre) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByFechaCierre(fechaCierre);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> findByCerrada(Boolean cerrada) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByCerrada(cerrada);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcial(Boolean cerrada) {
        List<OrdenReparacion> ordenesReparacion = ordenReparacionRepository.findByCerrada(cerrada);

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorFechaApertura(Boolean cerrada, LocalDate fechaApertura) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada AND o.fechaApertura = :fechaApertura");
        query.setParameter("cerrada", cerrada);
        query.setParameter("fechaApertura", fechaApertura);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasParcialDTO> findByCerradaParcialPorVehiculo(Boolean cerrada, Long id_vehiculo) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.cerrada = :cerrada AND o.vehiculo.id = :id");
        query.setParameter("cerrada", cerrada);
        query.setParameter("id", id_vehiculo);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<OrdenReparacionBusquedasDTO> obtenerOrdenesReparacionPorVehiculo(Long id_vehiculo) {
        Query query = entityManager.createQuery("FROM OrdenReparacion o WHERE o.vehiculo.id = :id");
        query.setParameter("id", id_vehiculo);
        List<OrdenReparacion> ordenesReparacion = query.getResultList();

        return ordenesReparacion.stream().map(ordenReparacion-> modelMapper.map(ordenReparacion, OrdenReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<OrdenReparacionDTO> modificarOrdenReparacion(OrdenReparacionDTO ordenReparacionDTO, Long id_vehiculo) {

        if (ordenReparacionDTO.getId() == null)
            throw new BadRequestModificacionException("Orden de reparacion", "id");

        if (!ordenReparacionRepository.existsById(ordenReparacionDTO.getId()))
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(ordenReparacionDTO.getId()));

        if (!ordenReparacionModificacionCambiosService.validacionVehiculo(id_vehiculo))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El vehiculo asociado a la orden de reparacion no existe");

        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(ordenReparacionDTO.getId()).get();
        Vehiculo vehiculo = vehiculoRepository.findById(id_vehiculo).get();

        ordenReparacion.setFechaApertura(ordenReparacionDTO.getFechaApertura());
        ordenReparacion.setDescripcion(ordenReparacionDTO.getDescripcion());
        ordenReparacion.setKilometros(ordenReparacionDTO.getKilometros());
        ordenReparacion.setVehiculo(vehiculo);

        OrdenReparacion ordenReparacionModificada = ordenReparacionRepository.save(ordenReparacion);

        return new ResponseEntity<>(modelMapper.map(ordenReparacion, OrdenReparacionDTO.class), HttpStatus.OK);
    }

}
