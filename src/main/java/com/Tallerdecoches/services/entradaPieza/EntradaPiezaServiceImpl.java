package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.entities.EntradaPieza;
import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.EntradaPiezaRepository;
import com.Tallerdecoches.repositories.PiezaRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
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
public class EntradaPiezaServiceImpl implements EntradaPiezaService{
    private final EntradaPiezaRepository entradaPiezaRepository;
    private final ProveedorRepository proveedorRepository;
    private final PiezaRepository piezaRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService;
    private final EntradaPiezaValidacionesUniqueService entradaPiezaValidacionesUniqueService;

    public EntradaPiezaServiceImpl(EntradaPiezaRepository entradaPiezaRepository, ProveedorRepository proveedorRepository, PiezaRepository piezaRepository, ModelMapper modelMapper, EntityManager entityManager, EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService, EntradaPiezaValidacionesUniqueService entradaPiezaValidacionesUniqueService) {
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.proveedorRepository = proveedorRepository;
        this.piezaRepository = piezaRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.entradaPiezaModificacionCambiosService = entradaPiezaModificacionCambiosService;
        this.entradaPiezaValidacionesUniqueService = entradaPiezaValidacionesUniqueService;
    }


    @Override
    public ResponseEntity<EntradaPiezaDTO> crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idProveedor, Long idPieza) {

        if (entradaPiezaRepository.existsByNumeroAlbaran(entradaPiezaCrearDTO.getNumeroAlbaran()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de albarán ya existe");

        if (!entradaPiezaModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la entrada no existe");

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = modelMapper.map(entradaPiezaCrearDTO, EntradaPieza.class);
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        Pieza pieza = piezaRepository.findById(idPieza).get();
        entradaPieza.setProveedor(proveedor);
        entradaPieza.setPieza(pieza);
        entradaPiezaRepository.save(entradaPieza);

        return new ResponseEntity<>(modelMapper.map(entradaPieza, EntradaPiezaDTO.class), HttpStatus.CREATED);
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> findAll() {

        List<EntradaPieza> entradaPieza = entradaPiezaRepository.findAll();

        return entradaPieza.stream().map(entrada-> modelMapper.map(entrada, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<EntradaPiezaBusquedasDTO> findById(Long id) {
        Optional<EntradaPieza> entradaPieza = entradaPiezaRepository.findById(id);

        if (!entradaPieza.isPresent())
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(id));

        return new ResponseEntity<>(modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class), HttpStatus.OK);
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> obtenerEntradasPorProveedorHQL(Long id_proveedor) {
        Query query = entityManager.createQuery("FROM EntradaPieza e WHERE e.proveedor.id = :id" );
        query.setParameter("id", id_proveedor);
        List<EntradaPieza> entradasPiezas = query.getResultList();

        return entradasPiezas.stream().map(entradaPieza-> modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<EntradaPiezaDTO> modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idProveedor, Long idPieza) {

        if (entradaPiezaDTO.getId() == null)
            throw new BadRequestModificacionException("Entrada", "id");

        if (!entradaPiezaRepository.existsById(entradaPiezaDTO.getId()))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(entradaPiezaDTO.getId()));

        if (!entradaPiezaValidacionesUniqueService.validacionUniqueNumeroAlbaran(entradaPiezaDTO))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El número de albarán ya existe");

        if (!entradaPiezaModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado a la entrada no existe");

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = entradaPiezaRepository.findById(entradaPiezaDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        Pieza pieza = piezaRepository.findById(idPieza).get();
        entradaPieza.setFechaEntrada(entradaPiezaDTO.getFechaEntrada());
        entradaPieza.setPieza(pieza);
        entradaPieza.setProveedor(proveedor);
        entradaPieza.setNumeroAlbaran(entradaPiezaDTO.getNumeroAlbaran());
        entradaPieza.setCantidad(entradaPiezaDTO.getCantidad());
        entradaPieza.setPrecioEntrada(entradaPiezaDTO.getPrecioEntrada());

        EntradaPieza entradaPiezaModificada = entradaPiezaRepository.save(entradaPieza);

        return new ResponseEntity<>(modelMapper.map(entradaPieza, EntradaPiezaDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!entradaPiezaRepository.existsById(id))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(id));

        entradaPiezaRepository.deleteById(id);

        return new ResponseEntity<>("Entrada eliminada con exito", HttpStatus.OK);
    }
}
