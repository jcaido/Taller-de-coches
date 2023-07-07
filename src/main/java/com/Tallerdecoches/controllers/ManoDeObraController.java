package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraDTO;
import com.Tallerdecoches.entities.ManoDeObra;
import com.Tallerdecoches.repositories.ManoDeObraRepository;
import com.Tallerdecoches.services.manoDeObra.ManoDeObraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
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

    @Operation(summary = "Crear nueva información de mano de obra", description = "Crear nueva información de mano de obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "información de mano de obra creada correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ManoDeObraDTO.class))
                    }),
            @ApiResponse(responseCode = "400",
                    description = "BAD REQUEST",
                    content = @Content),
            @ApiResponse(responseCode = "409",
                    description = "CONFLICT [\"El precio horario ya existe\"]",
                    content = @Content),
    })
    @PostMapping()
    public ResponseEntity<ManoDeObraDTO> crearManoDeObra(@Valid @RequestBody ManoDeObraCrearDTO manoDeObraCrearDTO) {

        return new ResponseEntity<>(manoDeObraService.crearManoDeObra(manoDeObraCrearDTO), HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener el histórico de precios de mano de obra", description = "Obtener el histórico de precios de mano de obra")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico de precios de mano de obra obtenido correctamente",
                    content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ManoDeObraDTO.class))
                    })
    })
    @GetMapping()
    public List<ManoDeObraDTO> obtenerTodosLosPreciosManoDeObra() {

        return manoDeObraService.findAll();
    }

    @GetMapping("/precio-actual/{precioActual}")
    public ResponseEntity<ManoDeObraDTO> obtenerPrecioActualManoDeObra(@PathVariable Boolean precioActual) {

        return new ResponseEntity<>(manoDeObraService.findByPrecioHoraClienteTallerActual(precioActual), HttpStatus.OK);
    }
}
