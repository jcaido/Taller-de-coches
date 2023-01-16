package com.Tallerdecoches.DTOs.propietario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PropietarioDTO {

    private Long id;
    @NotBlank(message = "debe introducir el nombre")
    private String nombre;
    @NotBlank(message = "debe introducir el primer apellido")
    private String primerApellido;
    @NotBlank(message = "debe introducir el segundo apellido")
    private String segundoApellido;
    @NotBlank(message = "debe introducir el dni")
    private String dni;
    private String domicilio;

    //public PropietarioDTO() {
    //}
    //public PropietarioDTO(Long id, String nombre, String primerApellido, String segundoApellido, String dni, String domicilio) {
      //  this.id = id;
        //this.nombre = nombre;
     //   this.primerApellido = primerApellido;
       // this.segundoApellido = segundoApellido;
       // this.dni = dni;
       // this.domicilio = domicilio;
   // }

    //public Long getId() {
      //  return id;
    //}

    //public void setId(Long id) {
      //  this.id = id;
    //}

    //public String getNombre() {
      //  return nombre;
    //}

    //public void setNombre(String nombre) {
      //  this.nombre = nombre;
    //}

    //public String getPrimerApellido() {
      //  return primerApellido;
   // }

   // public void setPrimerApellido(String primerApellido) {
     //   this.primerApellido = primerApellido;
    //}

    //public String getSegundoApellido() {
      //  return segundoApellido;
   // }

    //public void setSegundoApellido(String segundoApellido) {
     //   this.segundoApellido = segundoApellido;
    //}

    //public String getDni() {
      //  return dni;
    //}

    //public void setDni(String dni) {
      //  this.dni = dni;
    //}

    //public String getDomicilio() {
      //  return domicilio;
   // }

   // public void setDomicilio(String domicilio) {
     //   this.domicilio = domicilio;
    //}
}
