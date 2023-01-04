package com.Tallerdecoches.controllers;

import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import com.Tallerdecoches.services.propietario.PropietarioService;
import com.Tallerdecoches.services.vehiculo.VehiculoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods= {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
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
    public List<VehiculoBusquedasDTO> obtenerTodosLosVehiculos() {

        return vehiculoService.findAll();
    }

    //Obtener una lista con todos los vehiculos (campos id, matricula, marca, modelo, propietario)
    @GetMapping("/parcial")
    public List<VehiculoBusquedasParcialDTO> obtenerTodosLosVehiculosParcial() {

        return vehiculoService.findAllPartial();
    }

    //Obtener un vehiculo por su id
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoBusquedasDTO> obtenerVehiculoPorId (@PathVariable Long id) {

        return vehiculoService.findById(id);
    }

    //Obtener un vehiculo por matricula
    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<VehiculoBusquedasDTO> obtenerVehiculoPorMatricula(@PathVariable String matricula) {

        return vehiculoService.findByMatricula(matricula);
    }

    //Obtener vehiculos por marca
    @GetMapping("/marca/{marca}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorMarca(@PathVariable String marca) {

        return vehiculoService.findByMarca(marca);
    }

    //Obtener vehiculos por marca y modelo
    @GetMapping("/marca-modelo/{marca}-{modelo}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorMarcaYModelo(@PathVariable String marca, @PathVariable String modelo) {

        return vehiculoService.findByMarcaAndModelo(marca, modelo);
    }

    //Obtener vehiculos por marca y modelo(campos id, matricula, marca, modelo, propietario)
    @GetMapping("/marca-modelo/parcial/{marca}-{modelo}")
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorMarcaYModeloParcial(@PathVariable String marca, @PathVariable String modelo) {

        return vehiculoService.findByMarcaModeloPartial(marca, modelo);
    }

    //Obtener vehiculos por propietario
    @GetMapping("/propietario/{id_propietario}")
    public List<VehiculoBusquedasDTO> obtenerVehiculosPorPropietarioParcial(@PathVariable Long id_propietario) {

        //return vehiculoService.obtenerVehiculosPorPropietarioSQL(id_propietario);
        return vehiculoService.obtenerVehiculosPorPropietarioHQL(id_propietario);
    }

    //Obtener vehiculos por propietario (campos id, matricula, marca, modelo, propietario)
    @GetMapping("/propietario/parcial/{id_propietario}")
    public List<VehiculoBusquedasParcialDTO> obtenerVehiculosPorPropietario(@PathVariable Long id_propietario) {

        //return vehiculoService.obtenerVehiculosPorPropietarioSQL(id_propietario);
        return vehiculoService.obtenerVehiculosPorPropietarioHQLParcial(id_propietario);
    }

    //Modificar un vehiculo existente
    @PutMapping("/{id_propietario}")
    public ResponseEntity<VehiculoDTO> modificarVehiculo(@Valid @RequestBody VehiculoDTO vehiculoDTO, @PathVariable Long id_propietario) {

        return vehiculoService.modificarVehiculo(vehiculoDTO, id_propietario);
    }

    //Eliminar un vehiculo existente
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVehiculo(@PathVariable Long id) {

        return vehiculoService.deleteById(id);
    }
}
