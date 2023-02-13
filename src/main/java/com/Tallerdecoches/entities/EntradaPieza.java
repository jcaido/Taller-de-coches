package com.Tallerdecoches.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "entrada_pieza")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class EntradaPieza implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Proveedor proveedor;
    @Column(name = "fecha_entrada")
    private LocalDate fechaEntrada;
    @Column(name = "numero_albaran", unique = true)
    private String numeroAlbaran;
    @OneToOne()
    private Pieza pieza;
    private Integer cantidad;
    @Column(name = "precio_entrada")
    private Double precioEntrada;
    private Boolean facturada = false;
}
