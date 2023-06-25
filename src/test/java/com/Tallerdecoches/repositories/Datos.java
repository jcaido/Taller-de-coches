package com.Tallerdecoches.repositories;

import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalCrearDTO;
import com.Tallerdecoches.DTOs.codigoPostal.CodigoPostalDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioBusquedasParcialDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioCrearDTO;
import com.Tallerdecoches.DTOs.propietario.PropietarioDTO;
import com.Tallerdecoches.entities.CodigoPostal;
import com.Tallerdecoches.entities.Propietario;

import java.util.Arrays;
import java.util.List;

public class Datos {

    //CODIGOS POSTALES
    public final static CodigoPostal CODIGO_POSTAL_1 = CodigoPostal.builder()
            .id(2L)
            .codigo("14920")
            .localidad("Aguilar de la Frontera")
            .provincia("Cordoba")
            .build();

    public final static CodigoPostal CODIGO_POSTAL_1_MODIFICADO = CodigoPostal.builder()
            .id(2L)
            .codigo("11111")
            .localidad("Rute")
            .provincia("Albacete")
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

    public final static CodigoPostalDTO CODIGO_POSTAL_DTO_1_MODIFICADO = CodigoPostalDTO.builder()
            .id(2L)
            .codigo("11111")
            .localidad("Rute")
            .provincia("Albacete")
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

    //PROPIETARIOS

    public final static Propietario PROPIETARIO_1 = Propietario.builder()
            .id(1L)
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .codigoPostal(CODIGO_POSTAL_1)
            .build();
    public final static PropietarioDTO PROPIETARIO_DTO_1 = PropietarioDTO.builder()
            .id(1L)
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .build();
    public final static PropietarioCrearDTO PROPIETARIO_CREAR_DTO_1 = PropietarioCrearDTO.builder()
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .build();
    public final static Propietario PROPIETARIO_2 = Propietario.builder()
            .id(2L)
            .nombre("Francisco")
            .primerApellido("Garcia")
            .segundoApellido("Gutierrez")
            .dni("55000900B")
            .domicilio("Calle San Anton, 54")
            .codigoPostal(CODIGO_POSTAL_2)
            .build();
    public final static PropietarioDTO PROPIETARIO_DTO_2 = PropietarioDTO.builder()
            .id(2L)
            .nombre("Francisco")
            .primerApellido("Garcia")
            .segundoApellido("Gutierrez")
            .dni("55000900B")
            .domicilio("Calle San Anton, 54")
            .build();
    public final static Propietario PROPIETARIO_3 = Propietario.builder()
            .id(3L)
            .nombre("Antonio")
            .primerApellido("Moreno")
            .segundoApellido("Gordillo")
            .dni("22777555K")
            .domicilio("Calle Vinuesa, 87")
            .codigoPostal(CODIGO_POSTAL_1)
            .build();
    public final static Propietario PROPIETARIO_DTO_3 = Propietario.builder()
            .id(3L)
            .nombre("Jose")
            .primerApellido("Moreno")
            .segundoApellido("Gordillo")
            .dni("22777555K")
            .domicilio("Calle Vinuesa, 87")
            .build();
    public final static List<Propietario> PROPIETARIOS = Arrays.asList(PROPIETARIO_1, PROPIETARIO_2);
    public final static List<Propietario> PROPIETARIOS_1 = Arrays.asList(PROPIETARIO_1, PROPIETARIO_3);
    public final static List<Propietario> PROPIETARIOS_2 = Arrays.asList(PROPIETARIO_1);
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_1 = PropietarioBusquedasDTO.builder()
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .build();
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_DTO_1 = PropietarioBusquedasDTO.builder()
            .id(1L)
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .domicilio("Calle Alta, 98")
            .codigoPostal(CODIGO_POSTAL_1)
            .build();
    public final static PropietarioBusquedasParcialDTO PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_1 = PropietarioBusquedasParcialDTO.builder()
            .id(1L)
            .nombre("Antonio")
            .primerApellido("Perez")
            .segundoApellido("Jimenez")
            .dni("44000888T")
            .build();
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_2 =PropietarioBusquedasDTO.builder()
            .nombre("Francisco")
            .primerApellido("Garcia")
            .segundoApellido("Gutierrez")
            .dni("55000900B")
            .domicilio("Calle San Anton, 54")
            .build();
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_DTO_2 = PropietarioBusquedasDTO.builder()
            .id(2L)
            .nombre("Francisco")
            .primerApellido("Garcia")
            .segundoApellido("Gutierrez")
            .dni("55000900B")
            .domicilio("Calle San Anton, 54")
            .codigoPostal(CODIGO_POSTAL_2)
            .build();
    public final static PropietarioBusquedasParcialDTO PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_2 = PropietarioBusquedasParcialDTO.builder()
            .id(2L)
            .nombre("Francisco")
            .primerApellido("Garcia")
            .segundoApellido("Gutierrez")
            .dni("55000900B")
            .build();
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_3 = PropietarioBusquedasDTO.builder()
            .nombre("Antonio")
            .primerApellido("Moreno")
            .segundoApellido("Gordillo")
            .dni("22777555K")
            .domicilio("Calle Vinuesa, 87")
            .build();
    public final static PropietarioBusquedasDTO PROPIETARIO_BUSQUEDAS_DTO_3 = PropietarioBusquedasDTO.builder()
            .id(3L)
            .nombre("Antonio")
            .primerApellido("Moreno")
            .segundoApellido("Gordillo")
            .dni("22777555K")
            .domicilio("Calle Vinuesa, 87")
            .codigoPostal(CODIGO_POSTAL_1)
            .build();
    public final static PropietarioBusquedasParcialDTO PROPIETARIO_BUSQUEDAS_PARCIAL_DTO_3 = PropietarioBusquedasParcialDTO.builder()
            .id(3L)
            .nombre("Antonio")
            .primerApellido("Moreno")
            .segundoApellido("Gordillo")
            .dni("22777555K")
            .build();
}
