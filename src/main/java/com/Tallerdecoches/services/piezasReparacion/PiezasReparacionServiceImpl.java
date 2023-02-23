package com.Tallerdecoches.services.piezasReparacion;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionCrearDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionDTO;
import com.Tallerdecoches.entities.*;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.OrdenReparacionRepository;
import com.Tallerdecoches.repositories.PiezaRepository;
import com.Tallerdecoches.repositories.PiezasReparacionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Service
public class PiezasReparacionServiceImpl implements PiezasReparacionService {

    private final PiezasReparacionRepository piezasReparacionRepository;
    private final OrdenReparacionRepository ordenReparacionRepository;
    private final PiezaRepository piezaRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    public PiezasReparacionServiceImpl(PiezasReparacionRepository piezasReparacionRepository, OrdenReparacionRepository ordenReparacionRepository, PiezaRepository piezaRepository, ModelMapper modelMapper, EntityManager entityManager) {
        this.piezasReparacionRepository = piezasReparacionRepository;
        this.ordenReparacionRepository = ordenReparacionRepository;
        this.piezaRepository = piezaRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
    }

    @Override
    public ResponseEntity<PiezasReparacionDTO> crearPiezasReparacion(PiezasReparacionCrearDTO piezasReparacionCrearDTO, Long id_ordenReparacion, Long id_pieza) {

        if (!ordenReparacionRepository.existsById(id_ordenReparacion))
            throw new ResourceNotFoundException("orden de reparación", "id", String.valueOf(id_ordenReparacion));

        if (!piezaRepository.existsById(id_pieza))
            throw new ResourceNotFoundException("pieza", "id", String.valueOf(id_ordenReparacion));

        PiezasReparacion piezasReparacion = modelMapper.map(piezasReparacionCrearDTO, PiezasReparacion.class);
        OrdenReparacion ordenReparacion = ordenReparacionRepository.findById(id_ordenReparacion).get();
        Pieza pieza = piezaRepository.findById(id_pieza).get();
        piezasReparacion.setOrdenReparacion(ordenReparacion);
        piezasReparacion.setPieza(pieza);
        piezasReparacionRepository.save(piezasReparacion);

        return new ResponseEntity<>(modelMapper.map(piezasReparacion, PiezasReparacionDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> findAll() {
        List<PiezasReparacion> piezasReparacion = piezasReparacionRepository.findAll();

        return  piezasReparacion.stream().map(piezaReparacion-> modelMapper.map(piezaReparacion, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<PiezasReparacionBusquedasDTO> findById(Long id) {
        Optional<PiezasReparacion> piezaReparacion = piezasReparacionRepository.findById(id);

        if (!piezaReparacion.isPresent())
            throw new ResourceNotFoundException("Pieza-reparacion", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(piezaReparacion, PiezasReparacionBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public List<PiezasReparacionBusquedasParcialDTO> obtenerPiezasReparacionPorOrdenReparacion(Long id) {
        Optional<OrdenReparacion> ordenReparacion = ordenReparacionRepository.findById(id);

        if (!ordenReparacion.isPresent())
            throw new ResourceNotFoundException("Orden de reparacion", "id", String.valueOf(id));

        List<PiezasReparacion> piezasReparacion = ordenReparacion.get().getPiezasReparacion();

        return piezasReparacion.stream().map(piezaReparacion -> modelMapper.map(piezaReparacion, PiezasReparacionBusquedasParcialDTO.class)).toList();
    }

    @Override
    public List<PiezasReparacionBusquedasDTO> obtenerPiezasReparacionPorPiezaHQL(Long id_pieza) {
        Query query = entityManager.createQuery("FROM PiezasReparacion p WHERE p.pieza.id = :id" );
        query.setParameter("id", id_pieza);
        List<PiezasReparacion> piezas = query.getResultList();

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezasReparacionBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!piezasReparacionRepository.existsById(id))
            throw new ResourceNotFoundException("Imputacion de pieza", "id", String.valueOf(id));

        piezasReparacionRepository.deleteById(id);

        return new ResponseEntity<>("Imputacion de pieza eliminada con exito", HttpStatus.OK);
    }
}
