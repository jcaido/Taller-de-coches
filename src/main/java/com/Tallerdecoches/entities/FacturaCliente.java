package com.Tallerdecoches.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "facturas_clientes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class FacturaCliente implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Propietario propietario;
    @Column(name = "fecha_factura")
    private LocalDate fechaFactura;
    @Column(name = "numero_factura")
    private Long numeroFactura;
    @Column(name = "tipo_iva")
    private Integer tipoIVA;
    @OneToOne()
    private OrdenReparacion ordenReparacion;
}
