package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraDTO;
import com.Tallerdecoches.services.manoDeObra.ManoDeObraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/mano-de-obra")
public class ManoDeObraController {

    private final ManoDeObraService manoDeObraService;

    public ManoDeObraController(ManoDeObraService manoDeObraService) {
        this.manoDeObraService = manoDeObraService;
    }

    @PostMapping()
    public ResponseEntity<ManoDeObraDTO> crearManoDeObra(@Valid @RequestBody ManoDeObraCrearDTO manoDeObraCrearDTO) {

        return manoDeObraService.crearManoDeObra(manoDeObraCrearDTO);
    }
}
