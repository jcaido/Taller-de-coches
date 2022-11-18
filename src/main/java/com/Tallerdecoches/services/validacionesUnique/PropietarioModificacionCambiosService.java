package com.Tallerdecoches.services.validacionesUnique;

import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import com.Tallerdecoches.repositories.PropietarioRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropietarioModificacionCambiosService {

    private final PropietarioDTO propietarioDTO;
    private final PropietarioRepository propietarioRepository;
    private final CodigoPostalRepository codigoPostalRepository;

    public PropietarioModificacionCambiosService(PropietarioDTO propietarioDTO, PropietarioRepository propietarioRepository, CodigoPostalRepository codigoPostalRepository) {
        this.propietarioDTO = propietarioDTO;
        this.propietarioRepository = propietarioRepository;
        this.codigoPostalRepository = codigoPostalRepository;
    }

    public boolean isDniHaCambiado(PropietarioDTO propietarioDTO) {

        String dni = propietarioRepository.findById(propietarioDTO.getId()).get().getDni();

        if (dni.equals(propietarioDTO.getDni()))
            return false;

        return true;
    }

    public boolean validacionCodigoPostal(Long id_codigoPostal) {
        Optional<CodigoPostal> codigoPostal = codigoPostalRepository.findById(id_codigoPostal);

        if (codigoPostal.isPresent())
            return true;

        return false;
    }
}
