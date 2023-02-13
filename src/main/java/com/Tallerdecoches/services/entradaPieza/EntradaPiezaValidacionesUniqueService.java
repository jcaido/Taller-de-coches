package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.DTOs.entradaPieza.EntradaPiezaDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import com.Tallerdecoches.repositories.EntradaPiezaRepository;
import org.springframework.stereotype.Service;

@Service
public class EntradaPiezaValidacionesUniqueService {

    private final EntradaPiezaDTO entradaPiezaDTO;
    private final EntradaPiezaRepository entradaPiezaRepository;
    private final EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService;

    public EntradaPiezaValidacionesUniqueService(EntradaPiezaDTO entradaPiezaDTO, EntradaPiezaRepository entradaPiezaRepository, EntradaPiezaModificacionCambiosService entradaPiezaModificacionCambiosService) {
        this.entradaPiezaDTO = entradaPiezaDTO;
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.entradaPiezaModificacionCambiosService = entradaPiezaModificacionCambiosService;
    }

    public boolean validacionUniqueNumeroAlbaran(EntradaPiezaDTO entradaPiezaDTO) {

        if (entradaPiezaModificacionCambiosService.isNumeroAlbaranHaCambiado(entradaPiezaDTO) && entradaPiezaRepository.existsByNumeroAlbaran(entradaPiezaDTO.getNumeroAlbaran()))
            return false;

        return true;
    }
}
