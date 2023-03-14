package com.Tallerdecoches.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "factura_proveedor")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FacturaProveedor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Proveedor proveedor;
    @Column(name = "fecha_factura")
    private LocalDate fechaFactura;
    @Column(name = "numero_factura")
    private String numeroFactura;
    private Integer tipoIVA;
    private Boolean contabilizada = false;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "facturaProveedor")
    private List<AlbaranProveedor> albaranesProveedores = new ArrayList<>();
}
