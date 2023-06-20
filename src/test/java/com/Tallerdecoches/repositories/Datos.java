package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.entities.CodigoPostal;

import java.util.Arrays;
import java.util.List;

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

    public final static List<CodigoPostal> CODIGOS_POSTALES = Arrays.asList(CODIGO_POSTAL_1, CODIGO_POSTAL_2);

    public final static CodigoPostalDTO CODIGO_POSTAL_DTO_1 = CodigoPostalDTO.builder()
            .id(2L)
            .codigo("14920")
            .localidad("Aguilar de la Frontera")
            .provincia("Cordoba")
            .build();
    public final static CodigoPostalDTO CODIGO_POSTAL_DTO_2 = CodigoPostalDTO.builder()
            .id(3L)
            .codigo("45111")
            .localidad("Zuheros")
            .provincia("Cordoba")
            .build();
}
