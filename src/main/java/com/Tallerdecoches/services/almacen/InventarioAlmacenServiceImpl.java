package com.Tallerdecoches.services.almacen;

import com.Tallerdecoches.DTOs.almacen.MovimientoAlmacenDTO;
import com.Tallerdecoches.repositories.EntradaPiezaRepository;
import com.Tallerdecoches.repositories.PiezasReparacionRepository;
import net.bytebuddy.asm.Advice;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingLong;

@Service
public class InventarioAlmacenServiceImpl implements  InventarioAlmacenService{
    private final EntradaPiezaRepository entradaPiezaRepository;
    private final PiezasReparacionRepository piezasReparacionRepository;

    public InventarioAlmacenServiceImpl(EntradaPiezaRepository entradaPiezaRepository, PiezasReparacionRepository piezasReparacionRepository) {
        this.entradaPiezaRepository = entradaPiezaRepository;
        this.piezasReparacionRepository = piezasReparacionRepository;
    }

    @Override
    public List<MovimientoAlmacenDTO> obtenerInventarioAlmacenFecha(LocalDate fecha) {

        List<MovimientoAlmacenDTO> entradas = entradaPiezaRepository.obtenerTotalEntradasFecha(fecha);

        List<MovimientoAlmacenDTO> salidas = piezasReparacionRepository.obtenerTotalPiezasReparacionFecha(fecha);

        for (MovimientoAlmacenDTO salida: salidas
        ) {
            salida.setTotal(-salida.getTotal());
        }

        List<MovimientoAlmacenDTO> inventario = Stream.of(entradas, salidas).flatMap(Collection::stream).toList();

        Map<MovimientoAlmacenDTO.Movimiento, Long> inventarioMapGroupSum = inventario.stream().collect(groupingBy(mov-> new MovimientoAlmacenDTO.Movimiento(mov.getPiezaReferencia(), mov.getPiezaNombre()), summingLong(MovimientoAlmacenDTO::getTotal)));

        List<MovimientoAlmacenDTO> inventarioAlmacen = new ArrayList<>();
        inventarioMapGroupSum.forEach((k, v)-> {
            inventarioAlmacen.add(new MovimientoAlmacenDTO(k.piezaReferencia(), k.piezaNombre(), v));
        });

        return inventarioAlmacen.stream().filter(movimiento-> movimiento.getTotal() != 0).toList();
    }

}
