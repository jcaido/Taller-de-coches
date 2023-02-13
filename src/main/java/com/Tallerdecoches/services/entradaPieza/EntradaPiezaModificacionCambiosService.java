package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.repositories.EntradaPiezaRepository;
import com.Tallerdecoches.repositories.PiezaRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EntradaPiezaModificacionCambiosService {
    private final EntradaPiezaDTO entradaPiezaDTO;
    private final EntradaPiezaRepository entradaPiezaRepository;
    private final ProveedorRepository proveedorRepository;
    private final PiezaRepository piezaRepository;

    public EntradaPiezaModificacionCambiosService(EntradaPiezaDTO entradaPiezaDTO, EntradaPiezaRepository entradaPiezaRepository, ProveedorRepository proveedorRepository, PiezaRepository piezaRepository) {
        this.entradaPiezaDTO = entradaPiezaDTO;
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.proveedorRepository = proveedorRepository;
        this.piezaRepository = piezaRepository;
    }

    public boolean isNumeroAlbaranHaCambiado(EntradaPiezaDTO entradaPiezaDTO) {

        String numeroAlbaran = entradaPiezaRepository.findById(entradaPiezaDTO.getId()).get().getNumeroAlbaran();

        if (numeroAlbaran.equals(entradaPiezaDTO.getNumeroAlbaran()))
            return false;

        return true;
    }

    public boolean validacionProveedor(Long idProveedor) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(idProveedor);

        if (proveedor.isPresent())
            return true;

        return false;
    }

    public boolean validacionPieza(Long idPieza) {
        Optional<Pieza> pieza = piezaRepository.findById(idPieza);

        if (pieza.isPresent())
            return true;

        return false;
    }
}
