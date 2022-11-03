package com.Tallerdecoches.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "codigos_postales")
public class CodigoPostal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@NotBlank(message =  "debe introducir el codigo")
    //@Length(min=5, max=5, message = "el codigo postal debe tener 5 digitos")
    @Column(name = "codigo_postal", unique = true)
    private String codigo;
    //@NotBlank(message = "debe introducir una localidad")
    @Column(unique = true)
    private String localidad;
    //@NotBlank(message = "debe introducit una provincia")
    private String provincia;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "codigoPostal")
    private List<Propietario> propietarios = new ArrayList<>();

    public CodigoPostal() {
    }

    public CodigoPostal(Long id, String codigo, String localidad, String provincia) {
        this.id = id;
        this.codigo = codigo;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigoPostal) {
        this.codigo = codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public List<Propietario> getPropietarios() {
        return propietarios;
    }

    public void setPropietarios(List<Propietario> propietarios) {
        this.propietarios = propietarios;
    }

    @Override
    public String toString() {
        return "CodigoPostal{" +
                "id=" + id +
                ", codigoPostal='" + codigo + '\'' +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
