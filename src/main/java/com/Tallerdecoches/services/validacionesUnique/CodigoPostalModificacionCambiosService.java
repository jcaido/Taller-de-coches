package com.Tallerdecoches.services.validacionesUnique;

import com.Tallerdecoches.DTOs.CodigoPostalDTO;
import com.Tallerdecoches.repositories.CodigoPostalRepository;
import org.springframework.stereotype.Service;

@Service
public class CodigoPostalModificacionCambiosService {

    private final CodigoPostalDTO codigoPostalDTO;
    private final CodigoPostalRepository codigoPostalRepository;


    public CodigoPostalModificacionCambiosService(CodigoPostalDTO codigoPostalDTO, CodigoPostalRepository codigoPostalRepository) {
        this.codigoPostalDTO = codigoPostalDTO;
        this.codigoPostalRepository = codigoPostalRepository;
    }

    public boolean isCodigoHaCambiado(CodigoPostalDTO codigoPostalDTO) {

        String codigo = codigoPostalRepository.findById(codigoPostalDTO.getId()).get().getCodigo();

        if (codigo.equals(codigoPostalDTO.getCodigo()))
            return false;

        return true;

    }

    public boolean isLocalidadHaCambiado(CodigoPostalDTO codigoPostalDTO) {

        String localidad = codigoPostalRepository.findById(codigoPostalDTO.getId()).get().getLocalidad();

        if (localidad.equals(codigoPostalDTO.getLocalidad()))
            return false;

        return true;
    }
}
