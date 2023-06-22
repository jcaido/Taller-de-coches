package com.Tallerdecoches.services.pieza;

import com.Tallerdecoches.DTOs.pieza.PiezaCrearDTO;
import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.PiezaRepository;
import com.Tallerdecoches.services.entradaPieza.EntradaPiezaService;
import com.Tallerdecoches.services.piezasReparacion.PiezasReparacionService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class PiezaServiceImpl implements PiezaService {

    private final PiezaRepository piezaRepository;
    private  final ModelMapper modelMapper;
    private final PiezaValidacionesUniqueService piezaValidacionesUniqueService;
    private final PiezaModificacionCambiosService piezaModificacionCambiosService;
    private final PiezasReparacionService piezasReparacionService;
    private final EntradaPiezaService entradaPiezasService;

    public PiezaServiceImpl(PiezaRepository piezaRepository, ModelMapper modelMapper, PiezaValidacionesUniqueService piezaValidacionesUniqueService, PiezaModificacionCambiosService piezaModificacionCambiosService, PiezasReparacionService piezasReparacionService, EntradaPiezaService entradaPiezasService) {
        this.piezaRepository = piezaRepository;
        this.modelMapper = modelMapper;
        this.piezaValidacionesUniqueService = piezaValidacionesUniqueService;
        this.piezaModificacionCambiosService = piezaModificacionCambiosService;
        this.piezasReparacionService = piezasReparacionService;
        this.entradaPiezasService = entradaPiezasService;
    }

    @Override
    public PiezaDTO crearPieza(PiezaCrearDTO piezaCrearDTO) {

        if (piezaRepository.existsByReferencia(piezaCrearDTO.getReferencia()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La referencia ya existe");

        Pieza pieza = modelMapper.map(piezaCrearDTO, Pieza.class);
        Pieza nuevaPieza = piezaRepository.save(pieza);
        PiezaDTO piezaRespuesta = modelMapper.map(pieza, PiezaDTO.class);

        return piezaRespuesta;
    }

    @Override
    public List<PiezaDTO> findAll() {
        List<Pieza> piezas = piezaRepository.findAll();

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezaDTO.class)).toList();
    }

    @Override
    public PiezaDTO findById(Long id) {
        Optional<Pieza> pieza = piezaRepository.findById(id);

        if (!pieza.isPresent())
            throw new ResourceNotFoundException("Pieza", "id", String.valueOf(id));

        PiezaDTO piezaEncontrada = modelMapper.map(pieza, PiezaDTO.class);

        return piezaEncontrada;
    }

    @Override
    public ResponseEntity<PiezaDTO> findByReferencia(String referencia) {
        Optional<Pieza> pieza = piezaRepository.findByReferencia(referencia);

        if (!pieza.isPresent())
            throw new ResourceNotFoundException("Pieza", "referencia", referencia);

        return new ResponseEntity<>(modelMapper.map(pieza, PiezaDTO.class), HttpStatus.OK);
    }

    @Override
    public List<PiezaDTO> findByNombre(String nombre) {
        List<Pieza> piezas = piezaRepository.findByNombre(nombre);

        return piezas.stream().map(pieza-> modelMapper.map(pieza, PiezaDTO.class)).toList();
    }

    @Override
    public ResponseEntity<PiezaDTO> modificarPieza(PiezaDTO piezaDTO) {
        if (piezaDTO.getId() == null)
            throw new BadRequestModificacionException("Pieza", "id");

        if (!piezaRepository.existsById(piezaDTO.getId()))
            throw new ResourceNotFoundException("Pieza", "id", String.valueOf(piezaDTO.getId()));

        if (!piezaValidacionesUniqueService.validacionUniqueReferencia(piezaDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La referencia ya existe");

        Pieza pieza = piezaRepository.findById(piezaDTO.getId()).get();
        pieza.setReferencia(piezaDTO.getReferencia());
        pieza.setNombre(piezaDTO.getNombre());
        pieza.setPrecio(piezaDTO.getPrecio());

        Pieza piezaModificada = piezaRepository.save(pieza);

        return new ResponseEntity<>(modelMapper.map(piezaModificada, PiezaDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!piezaRepository.existsById(id))
            throw new ResourceNotFoundException("Pieza", "id", String.valueOf(id));

        if (piezasReparacionService.obtenerPiezasReparacionPorPiezaHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza está imputada");

        if (entradaPiezasService.obtenerEntradasPorPiezaHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza tiene entradas asociadas");

        piezaRepository.deleteById(id);

        return new ResponseEntity<>("Pieza eliminada con exito", HttpStatus.OK);
    }
}
