package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.services.propietario.PropietarioService;
import com.Tallerdecoches.services.vehiculo.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RequestMapping("/api/propietarios")
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
    public List<PropietarioBusquedasDTO> obtenerTodosLosPropietarios() {

        return propietarioService.findAll();
    }

    //Obtener una lista con todos los propietarios (campos id, nombre, 1apellido, 2apellido, dni)
    @GetMapping("/parcial")
    public List<PropietarioBusquedasParcialDTO> obtenerTodosLosPropietariosParcial() {

        return propietarioService.findAllPartial();
    }

    //Obtener un propietario por su id
    @GetMapping("/{id}")
    public ResponseEntity<PropietarioBusquedasDTO> obtenerPropietarioPorId(@PathVariable Long id) {

        return propietarioService.findById(id);
    }

    //Obtener un propietario por dni
    @GetMapping("/dni/{dni}")
    public ResponseEntity<PropietarioBusquedasDTO> obtenerPropietarioPorDni(@PathVariable String dni) {

        return propietarioService.findByDni(dni);
    }

    //Obtener propietarios por nombre
    @GetMapping("/nombre/{nombre}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorNombre(@PathVariable String nombre) {

        return propietarioService.findByNombre(nombre);
    }

    //Obtener propietarios por primer apellido
    @GetMapping("/primer-apellido/{primerApellido}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorPrimerApellido(@PathVariable String primerApellido) {

        return propietarioService.findByPrimerApellido(primerApellido);
    }

    //Obtener una lista con todos los propietarios por primer apellido (campos id, nombre, 1apellido, 2apellido, dni)
    @GetMapping("/primer-apellido/parcial/{primerApellido}")
    public List<PropietarioBusquedasParcialDTO> obtenerTodosLosPropietariosPorPrimrApellidoParcial(@PathVariable String primerApellido) {

        return propietarioService.findByPrimerApellidoPartial(primerApellido);
    }

    //Obtener propietarios por nombre mas apellidos
    @GetMapping("/nombre-apellidos/{nombre}-{primerApellido}-{segundoApellido}")
    public List<PropietarioBusquedasDTO> obtenerPropietarioPorNombreMasApellidos(
            @PathVariable String nombre,
            @PathVariable String primerApellido,
            @PathVariable String segundoApellido)
    {

        return propietarioService.findByNombreAndPrimerApellidoAndSegundoApellido(nombre, primerApellido, segundoApellido);
    }

    //Obtener propietarios por codigo postal
    @GetMapping("/codigo_postal/{id}")
    public List<PropietarioBusquedasDTO> obtenerPropietariosPorCodigoPostal(@PathVariable Long id) {

        return propietarioService.ObtenerPropietariosPorCodigoPostal(id);

    }

    //Obtener propietarios por codigo postal (campos id, nombre, 1apellido, 2apellido, dni)
    @GetMapping("/codigo_postal/parcial/{id}")
    public List<PropietarioBusquedasParcialDTO> obtenerPropietariosPorCodigoPostalParcial(@PathVariable Long id) {

        return propietarioService.obtenerPropietariosPorCodigoPostalParcial(id);

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
