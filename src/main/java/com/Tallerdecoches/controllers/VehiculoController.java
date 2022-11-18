package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.VehiculoDTO;
import com.Tallerdecoches.entities.Vehiculo;
import com.Tallerdecoches.repositories.PropietarioRepository;
import com.Tallerdecoches.services.PropietarioService;
import com.Tallerdecoches.services.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {

    private final VehiculoService vehiculoService;
    private final PropietarioService propietarioService;

    public VehiculoController(VehiculoService vehiculoService, PropietarioService propietarioService) {
        this.vehiculoService = vehiculoService;
        this.propietarioService = propietarioService;
    }

    //Crear un nuevo vehiculo
    @PostMapping("/{id_propietario}")
    public ResponseEntity<VehiculoDTO> crearVehiculo(@Valid @RequestBody VehiculoDTO vehiculoDTO, @PathVariable Long id_propietario) {

        return vehiculoService.crearVehiculo(vehiculoDTO, id_propietario);
    }

    //Obtener una lista con todos los vehiculos
    @GetMapping
    public List<VehiculoDTO> obtenerTodosLosVehiculos() {

        return vehiculoService.findAll();
    }

    //Obtener un vehiculo por su id
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoDTO> obtenerVehiculoPorId (@PathVariable Long id) {

        return vehiculoService.findById(id);
    }

    //Obtener un vehiculo por matricula
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<VehiculoDTO> obtenerVehiculoPorMatricula(@PathVariable String matricula) {

        return vehiculoService.findByMatricula(matricula);
    }

    //Obtener vehiculos por marca
    @GetMapping("/marca/{marca}")
    public List<VehiculoDTO> obtenerVehiculosPorMarca(@PathVariable String marca) {

        return vehiculoService.findByMarca(marca);
    }

    //Obtener vehiculos por marca y modelo
    @GetMapping("/marca-modelo/{marca}-{modelo}")
    public List<VehiculoDTO> obtenerVehiculosPorMarcaYModelo(@PathVariable String marca, @PathVariable String modelo) {

        return vehiculoService.findByMarcaAndModelo(marca, modelo);
    }

    //Obtener vehiculos por propietario
    @GetMapping("/propietario/{id_propietario}")
    public List<VehiculoDTO> obtenerVehiculosPorPropietario(@PathVariable Long id_propietario) {

        //return vehiculoService.obtenerVehiculosPorPropietarioSQL(id_propietario);
        return vehiculoService.obtenerVehiculosPorPropietarioHQL(id_propietario);
    }

    //Modificar un vehiculo existente
    @PutMapping("/{id_propietario}")
    public ResponseEntity<VehiculoDTO> modificarVehiculo(@Valid @RequestBody VehiculoDTO vehiculoDTO, @PathVariable Long id_propietario) {

        return vehiculoService.modificarVehiculo(vehiculoDTO, id_propietario);
    }

    //TODO: Pendiente refactorizar, cuando se establezcan relaciones con otras entidades
    //Eliminar un vehiculo existente
    @DeleteMapping("/{id}")
    public ResponseEntity<Vehiculo> eliminarVehiculo(@PathVariable Long id) {

        //if (!vehiculoService.existsById(id))
          //  return ResponseEntity.notFound().build();

        vehiculoService.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
