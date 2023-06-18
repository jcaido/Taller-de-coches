package com.Tallerdecoches.repositories;

import com.Tallerdecoches.entities.CodigoPostal;

public class Datos {
    public final static CodigoPostal CODIGO_POSTAL_1 = CodigoPostal.builder()
            .codigo("14920")
            .localidad("Aguilar de la Frontera")
            .provincia("Cordoba")
            .build();

    public final static CodigoPostal CODIGO_POSTAL_2 = CodigoPostal.builder()
            .codigo("45111")
            .localidad("Zuheros")
            .provincia("Cordoba")
            .build();
}
