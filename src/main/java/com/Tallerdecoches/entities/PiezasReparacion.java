package com.Tallerdecoches.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "piezas_reparacion")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PiezasReparacion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne()
    private Pieza pieza;
    private Integer cantidad;
    @OneToOne()
    private OrdenReparacion ordenReparacion;
}
