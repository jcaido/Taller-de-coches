package com.Tallerdecoches;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionDTO;
import com.Tallerdecoches.DTOs.pieza.PiezaDTO;
import com.Tallerdecoches.DTOs.piezasReparacion.PiezasReparacionDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.DTOs.proveedor.ProveedorDTO;
import com.Tallerdecoches.DTOs.vehiculo.VehiculoDTO;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TallerDeCochesApplication {

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Bean
	public CodigoPostalDTO codigoPostalDTO() {
		return new CodigoPostalDTO();
	}
	@Bean
	public PropietarioDTO propietarioDTO() {
		return new PropietarioDTO();
	}
	@Bean
	public VehiculoDTO vehiculoDTO() {
		return new VehiculoDTO();
	}
	@Bean
	public PiezaDTO piezaDTO() {
		return new PiezaDTO();
	}
	@Bean
	public OrdenReparacionDTO ordenReparacionDTO() {
		return new OrdenReparacionDTO();
	}
	@Bean
	public PiezasReparacionDTO piezasReparacionDTO() {
		return new PiezasReparacionDTO();
	}
	@Bean
	public ProveedorDTO proveedorDTO() {
		return new ProveedorDTO();
	}

	public static void main(String[] args) {

		ApplicationContext context = SpringApplication.run(TallerDeCochesApplication.class, args);

	}
}
