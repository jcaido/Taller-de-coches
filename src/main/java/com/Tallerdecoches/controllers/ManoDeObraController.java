package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraDTO;
import com.Tallerdecoches.entities.ManoDeObra;
import com.Tallerdecoches.repositories.ManoDeObraRepository;
import com.Tallerdecoches.services.manoDeObra.ManoDeObraService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/mano-de-obra")
public class ManoDeObraController {

    private final ManoDeObraService manoDeObraService;
    private final ManoDeObraRepository manoDeObraRepository;

    public ManoDeObraController(ManoDeObraService manoDeObraService, ManoDeObraRepository manoDeObraRepository) {
        this.manoDeObraService = manoDeObraService;
        this.manoDeObraRepository = manoDeObraRepository;
    }


    @PostMapping()
    public ResponseEntity<ManoDeObraDTO> crearManoDeObra(@Valid @RequestBody ManoDeObraCrearDTO manoDeObraCrearDTO) {

        return manoDeObraService.crearManoDeObra(manoDeObraCrearDTO);
    }

    @GetMapping()
    public List<ManoDeObra> obtenerTodosLosPreciosManoDeObra() {

        return manoDeObraRepository.findAll();
    }
}
