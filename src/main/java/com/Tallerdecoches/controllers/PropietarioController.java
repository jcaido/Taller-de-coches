package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.PropietarioDTO;
import com.Tallerdecoches.services.PropietarioService;
import com.Tallerdecoches.services.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/propietarios")
public class PropietarioController {

    private final PropietarioService propietarioService;
    private final VehiculoService vehiculoService;

    public PropietarioController(PropietarioService propietarioService, VehiculoService vehiculoService) {
        this.propietarioService = propietarioService;
        this.vehiculoService = vehiculoService;
    }

    //crear un nuevo Propietario
    @PostMapping("/{id_codigoPostal}")
    public ResponseEntity<PropietarioDTO> crearPropietario(@Valid @RequestBody PropietarioDTO propietarioDTO
            , @PathVariable Long id_codigoPostal) {

        return propietarioService.crearPropietario(propietarioDTO, id_codigoPostal);
    }

    //Obtener una lista con todos los propietarios
    @GetMapping
    public List<PropietarioDTO> obtenerTodosLosPropietarios() {

        return propietarioService.findAll();
    }

    //Obtener un propietario por su id
    @GetMapping("/{id}")
    public ResponseEntity<PropietarioDTO> obtenerPropietarioPorId(@PathVariable Long id) {

        return propietarioService.findById(id);
    }

    //Obtener un propietario por dni
    @GetMapping("/dni/{dni}")
    public ResponseEntity<PropietarioDTO> obtenerPropietarioPorDni(@PathVariable String dni) {

        return propietarioService.findByDni(dni);
    }

    //Obtener propietarios por nombre
    @GetMapping("/nombre/{nombre}")
    public List<PropietarioDTO> obtenerPropietarioPorNombre(@PathVariable String nombre) {

        return propietarioService.findByNombre(nombre);
    }

    //Obtener propietarios por primer apellido
    @GetMapping("/primer-apellido/{primerApellido}")
    public List<PropietarioDTO> obtenerPropietarioPorPrimerApellido(@PathVariable String primerApellido) {

        return propietarioService.findByPrimerApellido(primerApellido);
    }

    //Obtener propietarios por nombre mas apellidos
    @GetMapping("/nombre-apellidos/{nombre}-{primerApellido}-{segundoApellido}")
    public List<PropietarioDTO> obtenerPropietarioPorNombreMasApellidos(
            @PathVariable String nombre,
            @PathVariable String primerApellido,
            @PathVariable String segundoApellido)
    {

        return propietarioService.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, primerApellido, segundoApellido);
    }

    //Obtener propietarios por codigo postal
    @GetMapping("/codigo_postal/{id}")
    public List<PropietarioDTO> obtenerPropietariosPorCodigoPostal(@PathVariable Long id) {
        //return propietarioService.obtenerPropietariosPorCodigoPostalSQL(id);
        return propietarioService.obtenerPropietariosPorCodigoPostalHQL(id);
    }

    //Modificar un propietario existente
    @PutMapping("/{id_codigoPostal}")
    public ResponseEntity<PropietarioDTO> modificarPropietario(@Valid @RequestBody PropietarioDTO propietarioDTO
            , @PathVariable Long id_codigoPostal) {

        return propietarioService.modificarPropietario(propietarioDTO, id_codigoPostal);
    }

    //Eliminar un propietario existente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPropietario(@PathVariable Long id) {

        return propietarioService.deleteById(id);
    }

}
