package com.Tallerdecoches.services.validacionesUnique;

import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.repositories.PropietarioRepository;
import org.springframework.stereotype.Service;

@Service
public class PropietarioValidacionesUniqueService {

    private final PropietarioDTO propietarioDTO;
    private final PropietarioRepository propietarioRepository;
    private final PropietarioModificacionCambiosService propietarioModificacionCambiosService;

    public PropietarioValidacionesUniqueService(PropietarioDTO propietarioDTO, PropietarioRepository propietarioRepository, PropietarioModificacionCambiosService propietarioModificacionCambiosService) {
        this.propietarioDTO = propietarioDTO;
        this.propietarioRepository = propietarioRepository;
        this.propietarioModificacionCambiosService = propietarioModificacionCambiosService;
    }

    public boolean validacionUniqueDni(PropietarioDTO propietarioDTO) {

        if (propietarioModificacionCambiosService.isDniHaCambiado(propietarioDTO) && propietarioRepository.existsByDni(propietarioDTO.getDni()))
            return false;

        return true;
    }
}
