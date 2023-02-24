package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.repositories.PiezaRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EntradaPiezaModificacionCambiosService {
    private final PiezaRepository piezaRepository;

    public EntradaPiezaModificacionCambiosService(PiezaRepository piezaRepository) {
        this.piezaRepository = piezaRepository;
    }
    public boolean validacionPieza(Long idPieza) {
        Optional<Pieza> pieza = piezaRepository.findById(idPieza);

        if (pieza.isPresent())
            return true;

        return false;
    }
}
