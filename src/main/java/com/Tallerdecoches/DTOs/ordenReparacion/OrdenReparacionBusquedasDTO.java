package com.Tallerdecoches.DTOs.ordenReparacion;

import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionBusquedasParcialDTO;
import com.Tallerdecoches.entities.ManoDeObra;
import com.Tallerdecoches.entities.Vehiculo;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrdenReparacionBusquedasDTO {
    private Long id;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaApertura;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCierre;
    @NotBlank(message = "debe introducir la descripcion")
    private String descripcion;
    private Long kilometros;
    private Double horas;
    private Boolean cerrada;
    private Vehiculo vehiculo;
    private ManoDeObra manoDeObra;
    private List<PiezasReparacionBusquedasParcialDTO> piezasReparacion;
}
