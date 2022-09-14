/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Js
 */
@Entity
@Table(name = "concesionario")
@NamedQueries({
    @NamedQuery(name = "Concesionario.findAll", query = "SELECT c FROM Concesionario c"),
    @NamedQuery(name = "Concesionario.findByIdConcecionario", query = "SELECT c FROM Concesionario c WHERE c.idConcecionario = :idConcecionario"),
    @NamedQuery(name = "Concesionario.findByNombre", query = "SELECT c FROM Concesionario c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Concesionario.findByDirreccion", query = "SELECT c FROM Concesionario c WHERE c.dirreccion = :dirreccion"),
    @NamedQuery(name = "Concesionario.findByTelefono", query = "SELECT c FROM Concesionario c WHERE c.telefono = :telefono")})
public class Concesionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_concecionario")
    private Integer idConcecionario;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "dirreccion")
    private String dirreccion;
    @Column(name = "telefono")
    private Integer telefono;

    public Concesionario() {
    }

    public Concesionario(Integer idConcecionario) {
        this.idConcecionario = idConcecionario;
    }

    public Integer getIdConcecionario() {
        return idConcecionario;
    }

    public void setIdConcecionario(Integer idConcecionario) {
        this.idConcecionario = idConcecionario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDirreccion() {
        return dirreccion;
    }

    public void setDirreccion(String dirreccion) {
        this.dirreccion = dirreccion;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConcecionario != null ? idConcecionario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concesionario)) {
            return false;
        }
        Concesionario other = (Concesionario) object;
        if ((this.idConcecionario == null && other.idConcecionario != null) || (this.idConcecionario != null && !this.idConcecionario.equals(other.idConcecionario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Concesionario[ idConcecionario=" + idConcecionario + " ]";
    }
    
}
