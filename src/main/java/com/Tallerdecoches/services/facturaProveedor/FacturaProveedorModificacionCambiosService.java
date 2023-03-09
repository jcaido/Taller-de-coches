package com.Tallerdecoches.services.facturaProveedor;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.FacturaProveedor;
import com.Tallerdecoches.repositories.FacturaProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaProveedorModificacionCambiosService {

    private final FacturaProveedorRepository facturaProveedorRepository;

    public FacturaProveedorModificacionCambiosService(FacturaProveedorRepository facturaProveedorRepository) {
        this.facturaProveedorRepository = facturaProveedorRepository;
    }

    public boolean validacionNumeroFactura(String numeroFactura) {
        List<FacturaProveedor> facturasProveedor = facturaProveedorRepository.findByNumeroFactura(numeroFactura);

        if (facturasProveedor.size() > 0)
            return false;

        return true;
    }
}
