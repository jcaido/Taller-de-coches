package com.Tallerdecoches.services.entradaPieza;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.EntradaPieza;
import com.Tallerdecoches.entities.Pieza;
import com.Tallerdecoches.repositories.EntradaPiezaRepository;
import com.Tallerdecoches.repositories.PiezaRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class EntradaPiezaModificacionCambiosService {
    private final PiezaRepository piezaRepository;
    private final EntradaPiezaRepository entradaPiezaRepository;

    public EntradaPiezaModificacionCambiosService(PiezaRepository piezaRepository, EntradaPiezaRepository entradaPiezaRepository) {
        this.piezaRepository = piezaRepository;
        this.entradaPiezaRepository = entradaPiezaRepository;
    }
    public boolean validacionPieza(Long idPieza) {
        Optional<Pieza> pieza = piezaRepository.findById(idPieza);

        if (pieza.isPresent())
            return true;

        return false;
    }

    public boolean validaciionEntradaPiezaAlbaranFacturado(Long idPieza) {
        EntradaPieza entradaPieza = entradaPiezaRepository.findById(idPieza).get();
        AlbaranProveedor albaranProveedor = entradaPieza.getAlbaranProveedor();

        if (albaranProveedor.getFacturado())
            return true;

        return false;
    }
}
