package com.Tallerdecoches.services.manoDeObra;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraCrearDTO;
import com.Tallerdecoches.DTOs.manoDeObra.ManoDeObraDTO;
import com.Tallerdecoches.DTOs.ordenReparacion.OrdenReparacionBusquedasDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.ManoDeObra;
import com.Tallerdecoches.entities.OrdenReparacion;
import com.Tallerdecoches.exceptions.ResourceNotFoundException;
import com.Tallerdecoches.repositories.ManoDeObraRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ManoDeObraServiceImpl implements ManoDeObraService {

    private final ManoDeObraRepository manoDeObraRepository;
    private  final ModelMapper modelMapper;

    public ManoDeObraServiceImpl(ManoDeObraRepository manoDeObraRepository, ModelMapper modelMapper) {
        this.manoDeObraRepository = manoDeObraRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ResponseEntity<ManoDeObraDTO> crearManoDeObra(ManoDeObraCrearDTO manoDeObraCrearDTO) {

        if (manoDeObraRepository.existsByprecioHoraClienteTaller(manoDeObraCrearDTO.getPrecioHoraClienteTaller()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El precio horario ya existe");

        // Establecemos todos los precios anteriores a false
        manoDeObraRepository.PrecioHoraActualFalse(false);

        ManoDeObra manoDeObra = modelMapper.map(manoDeObraCrearDTO, ManoDeObra.class);
        manoDeObra.setFechaNuevoPrecio(LocalDate.now());
        manoDeObra.setPrecioHoraClienteTallerActual(true);
        ManoDeObra nuevoPrecioManoDeObra = manoDeObraRepository.save(manoDeObra);
        ManoDeObraDTO manoDeObraRespuesta = modelMapper.map(nuevoPrecioManoDeObra, ManoDeObraDTO.class);

        return new ResponseEntity<>(manoDeObraRespuesta, HttpStatus.CREATED);
    }

    @Override
    public List<ManoDeObraDTO> findAll() {
        List<ManoDeObra> preciosManoDeObra = manoDeObraRepository.findAllByOrderByFechaNuevoPrecioDesc();

        return  preciosManoDeObra.stream().map(precioManoDeObra-> modelMapper.map(precioManoDeObra, ManoDeObraDTO.class)).toList();

    }

    @Override
    public ResponseEntity<ManoDeObraDTO> findByPrecioHoraClienteTallerActual(Boolean precioHoraClienteTallerActual) {
        Optional<ManoDeObra> manoDeObra = manoDeObraRepository.findByPrecioHoraClienteTallerActual(precioHoraClienteTallerActual);

        if (!manoDeObra.isPresent())
            throw new ResourceNotFoundException("precio de la mano de obra", "id", String.valueOf(precioHoraClienteTallerActual));

        return new ResponseEntity<>(modelMapper.map(manoDeObra, ManoDeObraDTO.class), HttpStatus.OK);
    }
}
