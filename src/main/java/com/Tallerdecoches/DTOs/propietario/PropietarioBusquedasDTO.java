package com.Tallerdecoches.DTOs.propietario;

import com.Tallerdecoches.entities.CodigoPostal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropietarioBusquedasDTO {

    private Long id;
    private String nombre;
    private String primerApellido;
    private String segundoApellido;
    private String dni;
    private String domicilio;
    private CodigoPostal codigoPostal;

    //public PropietarioBusquedasDTO() {
    //}

   // public PropietarioBusquedasDTO(Long id, String nombre, String primerApellido, String segundoApellido, String dni, String domicilio, CodigoPostal codigoPostal) {
     //   this.id = id;
       // this.nombre = nombre;
     //   this.primerApellido = primerApellido;
      //  this.segundoApellido = segundoApellido;
      //  this.dni = dni;
       // this.domicilio = domicilio;
      //  this.codigoPostal = codigoPostal;
    //}

    //public PropietarioBusquedasDTO(Long id, String nombre, String primerApellido, String segundoApellido, String dni, String domicilio) {
      //  this.id = id;
      //  this.nombre = nombre;
       // this.primerApellido = primerApellido;
       // this.segundoApellido = segundoApellido;
       // this.dni = dni;
       // this.domicilio = domicilio;
   // }

    //public Long getId() {
     //   return id;
    //}

    //public void setId(Long id) {
      //  this.id = id;
   // }

    //public String getNombre() {
      //  return nombre;
   // }

   // public void setNombre(String nombre) {
     //   this.nombre = nombre;
    //}

    //public String getPrimerApellido() {
      //  return primerApellido;
    //}

    //public void setPrimerApellido(String primerApellido) {
      //  this.primerApellido = primerApellido;
    //}

    //public String getSegundoApellido() {
      //  return segundoApellido;
    //}

    //public void setSegundoApellido(String segundoApellido) {
      //  this.segundoApellido = segundoApellido;
    //}

    //public String getDni() {
     //   return dni;
    //}

    //public void setDni(String dni) {
      //  this.dni = dni;
   // }

    //public String getDomicilio() {
      //  return domicilio;
    //}

    //public void setDomicilio(String domicilio) {
      //  this.domicilio = domicilio;
   // }

    //public CodigoPostal getCodigoPostal() {
      //  return codigoPostal;
    //}

    //public void setCodigoPostal(CodigoPostal codigoPostal) {
      //  this.codigoPostal = codigoPostal;
    //}
}
