package com.Tallerdecoches.services.manoDeObra;

import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraDTO;
import org.springframework.http.ResponseEntity;

public interface ManoDeObraService {
    ResponseEntity<ManoDeObraDTO> crearManoDeObra(ManoDeObraCrearDTO manoDeObraCrearDTO);
}
