package com.Tallerdecoches.services.albaranProveedor;

import com.Tallerdecoches.entities.AlbaranProveedor;
import com.Tallerdecoches.entities.Proveedor;
import com.Tallerdecoches.repositories.AlbaranProveedorRepository;
import com.Tallerdecoches.repositories.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbaranProveedorModificacionCambiosService {

    private final ProveedorRepository proveedorRepository;
    private final AlbaranProveedorRepository albaranProveedorRepository;

    public AlbaranProveedorModificacionCambiosService(ProveedorRepository proveedorRepository, AlbaranProveedorRepository albaranProveedorRepository) {
        this.proveedorRepository = proveedorRepository;
        this.albaranProveedorRepository = albaranProveedorRepository;
    }

    public boolean validacionProveedor(Long idProveedor) {
        Optional<Proveedor> proveedor = proveedorRepository.findById(idProveedor);

        if (proveedor.isPresent())
            return true;

        return false;
    }

    public boolean validacionNumeroAlbaran(String numeroAlbaran) {
        List<AlbaranProveedor> albaranesProveedor = albaranProveedorRepository.findByNumeroAlbaran(numeroAlbaran);

        if (albaranesProveedor.size() > 0)
            return false;

        return true;
    }
}
