package com.Tallerdecoches.services.codigoPostal;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.springframework.stereotype.Service;

@Service
public class CodigoPostalValidacionesUniqueService {
    private final CodigoPostalDTO codigoPostalDTO;
    private final CodigoPostalRepository codigoPostalRepository;
    private final CodigoPostalModificacionCambiosService codigoPostalModificacionCambiosService;

    public CodigoPostalValidacionesUniqueService(CodigoPostalDTO codigoPostalDTO, CodigoPostalRepository codigoPostalRepository, CodigoPostalModificacionCambiosService codigoPostalModificacionCambiosService) {
        this.codigoPostalDTO = codigoPostalDTO;
        this.codigoPostalRepository = codigoPostalRepository;
        this.codigoPostalModificacionCambiosService = codigoPostalModificacionCambiosService;
    }
    public boolean validacionUniqueLocalidad(CodigoPostalDTO codigoPostalDTO) {
        if ((!codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)
                && codigoPostalModificacionCambiosService.isLocalidadHaCambiado(codigoPostalDTO)
                && codigoPostalRepository.existsByLocalidad(codigoPostalDTO.getLocalidad())) ||
                (codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO)
                        && !codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo())
                        && codigoPostalModificacionCambiosService.isLocalidadHaCambiado(codigoPostalDTO)
                        && codigoPostalRepository.existsByLocalidad(codigoPostalDTO.getLocalidad())))
            return false;
        return true;
    }
    public boolean validacionUniqueCodigo(CodigoPostalDTO codigoPostalDTO) {
        if (codigoPostalModificacionCambiosService.isCodigoHaCambiado(codigoPostalDTO) && codigoPostalRepository.existsByCodigo(codigoPostalDTO.getCodigo()))
            return false;
        return true;
    }
}
