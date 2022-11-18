package com.Tallerdecoches.services.validacionesUnique;

import com.Tallerdecoches.DTOs.VehiculoDTO;
import com.Tallerdecoches.entities.Propietario;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.repositories.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VehiculoModificacionCambiosService {

    private final VehiculoDTO vehiculoDTO;
    private final VehiculoRepository vehiculoRepository;
    private final PropietarioRepository propietarioRepository;

    public VehiculoModificacionCambiosService(VehiculoDTO vehiculoDTO, VehiculoRepository vehiculoRepository, PropietarioRepository propietarioRepository) {
        this.vehiculoDTO = vehiculoDTO;
        this.vehiculoRepository = vehiculoRepository;
        this.propietarioRepository = propietarioRepository;
    }

    public boolean isMatriculaHaCambiado(VehiculoDTO vehiculoDTO) {

        String matricula = vehiculoRepository.findById(vehiculoDTO.getId()).get().getMatricula();

        if (matricula.equals(vehiculoDTO.getMatricula()))
            return false;

        return true;
    }

    public boolean validacionPropietario(Long id_propietario) {

        Optional<Propietario> propietario = propietarioRepository.findById(id_propietario);

        if (propietario.isPresent())
            return true;

        return false;
    }
}
