package com.Tallerdecoches.services.pieza;

import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import com.Tallerdecoches.repositories.PiezaRepository;
import org.springframework.stereotype.Service;

@Service
public class PiezaValidacionesUniqueService {

    private final PiezaDTO piezaDTO;
    private final PiezaRepository piezaRepository;
    private final PiezaModificacionCambiosService piezaModificacionCambiosService;

    public PiezaValidacionesUniqueService(PiezaDTO piezaDTO, PiezaRepository piezaRepository, PiezaModificacionCambiosService piezaModificacionCambiosService) {
        this.piezaDTO = piezaDTO;
        this.piezaRepository = piezaRepository;
        this.piezaModificacionCambiosService = piezaModificacionCambiosService;
    }

    public boolean validacionUniqueReferencia(PiezaDTO piezaDTO) {

        if (piezaModificacionCambiosService.isReferenciaHaCambiado(piezaDTO) && piezaRepository.existsByReferencia(piezaDTO.getReferencia()))
            return false;

        return true;
    }
}
