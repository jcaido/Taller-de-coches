package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.DTOs.albaranProveedor.*;
import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.exceptions.BadRequestModificacionException;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.AlbaranProveedorRepository;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import com.Tallerdecoches.services.entradaPieza.EntradaPiezaService;
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
public class AlbaranProveedorServiceImpl implements  AlbaranProveedorService{

    private final AlbaranProveedorRepository albaranProveedorRepository;
    private final AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService;
    private final EntradaPiezaService entradaPiezaService;
    private final ModelMapper modelMapper;
    private final ProveedorRepository proveedorRepository;
    private final FacturaProveedorRepository facturaProveedorRepository;
    private final EntityManager entityManager;


    public AlbaranProveedorServiceImpl(AlbaranProveedorRepository albaranProveedorRepository, AlbaranProveedorModificacionCambiosService albaranProveedorModificacionCambiosService, EntradaPiezaService entradaPiezaService, ModelMapper modelMapper, ProveedorRepository proveedorRepository, FacturaProveedorRepository facturaProveedorRepository, EntityManager entityManager) {
        this.albaranProveedorRepository = albaranProveedorRepository;
        this.albaranProveedorModificacionCambiosService = albaranProveedorModificacionCambiosService;
        this.entradaPiezaService = entradaPiezaService;
        this.modelMapper = modelMapper;
        this.proveedorRepository = proveedorRepository;
        this.facturaProveedorRepository = facturaProveedorRepository;
        this.entityManager = entityManager;
    }

    @Override
    public AlbaranProveedorDTO crearAlbaranProveedor(AlbaranProveedorCrearDTO albaranProveedorCrearDTO, Long idProveedor) {

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado al albarán no existe");

        if (!albaranProveedorModificacionCambiosService.validacionNumeroAlbaran(albaranProveedorCrearDTO.getNumeroAlbaran(), idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de albaran ya existe para ese proveedor");

        AlbaranProveedor albaranProveedor = modelMapper.map(albaranProveedorCrearDTO, AlbaranProveedor.class);
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();
        albaranProveedor.setProveedor(proveedor);
        albaranProveedorRepository.save(albaranProveedor);
        AlbaranProveedorDTO albaranProveedorRespuesta = modelMapper.map(albaranProveedor, AlbaranProveedorDTO.class);

        return albaranProveedorRespuesta;
    }

    @Override
    public List<AlbaranProveedorBusquedasDTO> findAll() {
        List<AlbaranProveedor> ordenes = albaranProveedorRepository.findAll();

        return ordenes.stream().map(orden-> modelMapper.map(orden, AlbaranProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public List<AlbaranProveedorBusquedasParcialDTO> findAllParcial() {
        List<AlbaranProveedor> ordenes = albaranProveedorRepository.findAll();

        return ordenes.stream().map(orden-> modelMapper.map(orden, AlbaranProveedorBusquedasParcialDTO.class)).toList();
    }

    @Override
    public AlbaranProveedorBusquedasDTO findById(Long id) {
        Optional<AlbaranProveedor> albaranProveedor = albaranProveedorRepository.findById(id);

        if (!albaranProveedor.isPresent())
            throw new ResourceNotFoundException("Albaran de proveedor", "id", String.valueOf(id));

        AlbaranProveedorBusquedasDTO albaranProveedorEncontrado = modelMapper.map(albaranProveedor, AlbaranProveedorBusquedasDTO.class);

        return albaranProveedorEncontrado;
    }

    @Override
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorPorProveedorHQL(Long idProveedor) {
        Query query = entityManager.createQuery("FROM AlbaranProveedor a WHERE a.proveedor.id = :idProveedor" );
        query.setParameter("idProveedor", idProveedor);
        List<AlbaranProveedor> albaranesProveedor = query.getResultList();

        return albaranesProveedor.stream().map(albaranProveedor-> modelMapper.map(albaranProveedor, AlbaranProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesPtesFacturarPorProveedorHQL(Long idProveedor) {
        Query query = entityManager.createQuery("FROM AlbaranProveedor a WHERE a.proveedor.id = :idProveedor AND a.facturado = :facturado" );
        query.setParameter("idProveedor", idProveedor);
        query.setParameter("facturado", false);
        List<AlbaranProveedor> albaranesProveedor = query.getResultList();

        return albaranesProveedor.stream().map(albaranProveedor-> modelMapper.map(albaranProveedor, AlbaranProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public List<AlbaranProveedorBusquedasDTO> obtenerAlbaranesProveedorPorFacturaProveedorHQL(Long idFactura) {
        Query query = entityManager.createQuery("FROM AlbaranProveedor a WHERE a.facturaProveedor.id = :idFactura");
        query.setParameter("idFactura", idFactura);
        List<AlbaranProveedor> albaranesProveedor = query.getResultList();

        return albaranesProveedor.stream().map(albaranProveedor-> modelMapper.map(albaranProveedor, AlbaranProveedorBusquedasDTO.class)).toList();
    }

    @Override
    public AlbaranProveedorDTO modificarAlbaranProveedor(AlbaranProveedorDTO albaranProveedorDTO, Long idProveedor) {

        if (albaranProveedorDTO.getId() == null)
            throw new BadRequestModificacionException("Albarán de proveedor", "id");

        if (!albaranProveedorRepository.existsById(albaranProveedorDTO.getId()))
            throw new ResourceNotFoundException("Albarán de proveedor", "id", String.valueOf(albaranProveedorDTO.getId()));

        if (!albaranProveedorModificacionCambiosService.validacionProveedor(idProveedor))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El proveedor asociado al albarán no existe");

        if (albaranProveedorModificacionCambiosService.numeroAlbaranHaCambiado(albaranProveedorDTO)) {
            if (!albaranProveedorModificacionCambiosService.validacionNumeroAlbaran(albaranProveedorDTO.getNumeroAlbaran(), idProveedor))
                throw new ResponseStatusException(HttpStatus.CONFLICT, "El numero de albaran ya existe para ese proveedor");
        }

        AlbaranProveedor albaranProveedor = albaranProveedorRepository.findById(albaranProveedorDTO.getId()).get();
        Proveedor proveedor = proveedorRepository.findById(idProveedor).get();

        albaranProveedor.setProveedor(proveedor);
        albaranProveedor.setFechaAlbaran(albaranProveedorDTO.getFechaAlbaran());
        albaranProveedor.setNumeroAlbaran(albaranProveedorDTO.getNumeroAlbaran());
        albaranProveedor.setFacturado(albaranProveedorDTO.getFacturado());

        AlbaranProveedor albaranProveedorModificado = albaranProveedorRepository.save(albaranProveedor);
        AlbaranProveedorDTO albaranProveedorModificadoDTO = modelMapper.map(albaranProveedorModificado, AlbaranProveedorDTO.class);

        return albaranProveedorModificadoDTO;
    }

    @Override
    public AlbaranProveedorDTO facturarAlbaranProveedor(Long idAlbaran, Long idFactura) {

        if (!albaranProveedorRepository.existsById(idAlbaran))
            throw new ResourceNotFoundException("Albarán de proveedor", "id", String.valueOf(idAlbaran));

        if (!facturaProveedorRepository.existsById(idFactura))
            throw new ResourceNotFoundException("Factura de proveedor", "id", String.valueOf(idFactura));

        AlbaranProveedor albaran = albaranProveedorRepository.findById(idAlbaran).get();
        FacturaProveedor factura = facturaProveedorRepository.findById(idFactura).get();

        albaran.setFacturaProveedor(factura);
        albaran.setFacturado(true);

        AlbaranProveedor albaranProveedorFacturado = albaranProveedorRepository.save(albaran);
        AlbaranProveedorDTO albaranProveedorFacturadoDTO = modelMapper.map(albaranProveedorFacturado, AlbaranProveedorDTO.class);

        return albaranProveedorFacturadoDTO;
    }

    @Override
    public ResponseEntity<AlbaranProveedorDTO> noFacturarAlbaranProveedorFacturado(Long idAlbaran) {

        if (!albaranProveedorRepository.existsById(idAlbaran))
            throw new ResourceNotFoundException("Albarán de proveedor", "id", String.valueOf(idAlbaran));

        AlbaranProveedor albaran = albaranProveedorRepository.findById(idAlbaran).get();

        albaran.setFacturaProveedor(null);
        albaran.setFacturado(false);

        albaranProveedorRepository.save(albaran);

        return new ResponseEntity<>(modelMapper.map(albaran, AlbaranProveedorDTO.class), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        AlbaranProveedor albaranProveedor = albaranProveedorRepository.findById(id).get();

        if (entradaPiezaService.obtenerEntradasPiezasPorAlbaranProveedorHQL(id).size() > 0)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Existen entradas de piezas asociadas con ese albaran de proveedor");

        if (albaranProveedor.getFacturaProveedor() != null)
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El albarán ya está asociado a una factura");

        albaranProveedorRepository.deleteById(id);

        return new ResponseEntity<>("Albaran de proveedor eliminado con exito", HttpStatus.OK);
    }
}
