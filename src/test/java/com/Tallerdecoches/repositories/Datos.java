package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.entities.CodigoPostal;

import java.util.Arrays;
import java.util.List;

public class Datos {
    public final static CodigoPostal CODIGO_POSTAL_1 = CodigoPostal.builder()
            .id(2L)
            .codigo("14920")
            .localidad("Aguilar de la Frontera")
            .provincia("Cordoba")
            .build();

    public final static CodigoPostal CODIGO_POSTAL_2 = CodigoPostal.builder()
            .id(3L)
            .codigo("45111")
            .localidad("Zuheros")
            .provincia("Cordoba")
            .build();

    public final static CodigoPostal CODIGO_POSTAL_3 = CodigoPostal.builder()
            .id(4L)
            .codigo("44666")
            .localidad("Baena")
            .provincia("Cordoba")
            .build();

    public final static CodigoPostal CODIGO_POSTAL_4 = CodigoPostal.builder()
            .id(5L)
            .codigo("98776")
            .localidad("Carmona")
            .provincia("Sevilla")
            .build();

    public final static List<CodigoPostal> CODIGOS_POSTALES = Arrays.asList(CODIGO_POSTAL_1, CODIGO_POSTAL_2);
    public final static List<CodigoPostal> CODIGOS_POSTALES_1 = Arrays.asList(CODIGO_POSTAL_1, CODIGO_POSTAL_2, CODIGO_POSTAL_3);

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

    public final static CodigoPostalDTO CODIGO_POSTAL_DTO_3 = CodigoPostalDTO.builder()
            .id(5L)
            .codigo("98776")
            .localidad("Carmona")
            .provincia("Sevilla")
            .build();

    public final static CodigoPostalCrearDTO CODIGO_POSTAL_CREAR_DTO_1 = CodigoPostalCrearDTO.builder()
            .codigo("14920")
            .localidad("Aguilar de la Frontera")
            .provincia("Cordoba")
            .build();

    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_1 = PropietarioBusquedasDTO.builder()
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .build();

    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_2 =PropietarioBusquedasDTO.builder()
            .nombre("Juan")
            .primerApellido("Cabello")
            .segundoApellido("Alba")
            .dni("33888000L")
            .domicilio("Calle Baja, 89")
            .build();
}
