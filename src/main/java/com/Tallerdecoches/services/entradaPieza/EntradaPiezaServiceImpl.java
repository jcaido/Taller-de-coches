package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaBusquedasDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaCrearDTO;
import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.entities.EntradaPieza;
import com.Tallerdecoches.entities.Pieza;
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

    public EntradaPiezaServiceImpl(EntradaPiezaRepository entradaPiezaRepository, ProveedorRepository proveedorRepository, PiezaRepository piezaRepository, ModelMapper modelMapper, EntityManager entityManager, EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService) {
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.proveedorRepository = proveedorRepository;
        this.piezaRepository = piezaRepository;
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.entradaPiezaModificacionCambiosService = entradaPiezaModificacionCambiosService;
    }
    //private final AlbaranProveedorService albaranProveedorService;



    @Override
    public ResponseEntity<EntradaPiezaDTO> crearEntradaPieza(EntradaPiezaCrearDTO entradaPiezaCrearDTO, Long idPieza) {

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = modelMapper.map(entradaPiezaCrearDTO, EntradaPieza.class);
        Pieza pieza = piezaRepository.findById(idPieza).get();
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
    public List<EntradaPiezaBusquedasDTO> obtenerEntradasPorPiezaHQL(Long id_pieza) {
        Query query = entityManager.createQuery("FROM EntradaPieza e WHERE e.pieza.id = :id" );
        query.setParameter("id", id_pieza);
        List<EntradaPieza> entradasPiezas = query.getResultList();

        return entradasPiezas.stream().map(entradaPieza-> modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public List<EntradaPiezaBusquedasDTO> obtenerEntradasPiezasPorAlbaranProveedorHQL(Long idAlbaranProveedor) {
        Query query = entityManager.createQuery("FROM EntradaPieza e WHERE e.albaranProveedor.id = :idAlbaranProveedor" );
        query.setParameter("idAlbaranProveedor", idAlbaranProveedor);
        List<EntradaPieza> entradasPiezas = query.getResultList();

        return entradasPiezas.stream().map(entradaPieza-> modelMapper.map(entradaPieza, EntradaPiezaBusquedasDTO.class)).toList();
    }

    @Override
    public ResponseEntity<EntradaPiezaDTO> modificarEntradaPieza(EntradaPiezaDTO entradaPiezaDTO, Long idPieza) {

        if (entradaPiezaDTO.getId() == null)
            throw new BadRequestModificacionException("Entrada", "id");

        if (!entradaPiezaRepository.existsById(entradaPiezaDTO.getId()))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(entradaPiezaDTO.getId()));

        if (!entradaPiezaModificacionCambiosService.validacionPieza(idPieza))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "La pieza asociada a la entrada no existe");

        EntradaPieza entradaPieza = entradaPiezaRepository.findById(entradaPiezaDTO.getId()).get();
        Pieza pieza = piezaRepository.findById(idPieza).get();
        entradaPieza.setPieza(pieza);
        entradaPieza.setCantidad(entradaPiezaDTO.getCantidad());
        entradaPieza.setPrecioEntrada(entradaPiezaDTO.getPrecioEntrada());

        entradaPiezaRepository.save(entradaPieza);

        return new ResponseEntity<>(modelMapper.map(entradaPieza, EntradaPiezaDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {

        if (!entradaPiezaRepository.existsById(id))
            throw new ResourceNotFoundException("Entrada", "id", String.valueOf(id));

        if (entradaPiezaModificacionCambiosService.validaciionEntradaPiezaAlbaranFacturado(id))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen albaranes facturados asociados con ese entrada de piezas");

        entradaPiezaRepository.deleteById(id);

        return new ResponseEntity<>("Entrada eliminada con exito", HttpStatus.OK);
    }
}
