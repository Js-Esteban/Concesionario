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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Js
 */
@Entity
@Table(name = "vehiculo")
@NamedQueries({
    @NamedQuery(name = "Vehiculo.findAll", query = "SELECT v FROM Vehiculo v"),
    @NamedQuery(name = "Vehiculo.findByIdVehiculo", query = "SELECT v FROM Vehiculo v WHERE v.idVehiculo = :idVehiculo"),
    @NamedQuery(name = "Vehiculo.findByPlaca", query = "SELECT v FROM Vehiculo v WHERE v.placa = :placa"),
    @NamedQuery(name = "Vehiculo.findByModelo", query = "SELECT v FROM Vehiculo v WHERE v.modelo = :modelo"),
    @NamedQuery(name = "Vehiculo.findByPrecio", query = "SELECT v FROM Vehiculo v WHERE v.precio = :precio"),
    @NamedQuery(name = "Vehiculo.findByIdConcesionarioFk", query = "SELECT v FROM Vehiculo v WHERE v.idConcesionarioFk = :idConcesionarioFk")})
public class Vehiculo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;
    @Column(name = "placa")
    private String placa;
    @Column(name = "modelo")
    private Integer modelo;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio")
    private Double precio;
    @Column(name = "id_concesionario_fk")
    private Integer idConcesionarioFk;
    @JoinColumn(name = "id_tipo_fk", referencedColumnName = "id_tipo")
    @ManyToOne
    private Tipovehiculo idTipoFk;
    @JoinColumn(name = "id_marca_fk", referencedColumnName = "id_marca")
    @ManyToOne
    private Marca idMarcaFk;

    public Vehiculo() {
    }

    public Vehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public Integer getIdVehiculo() {
        return idVehiculo;
    }

    public void setIdVehiculo(Integer idVehiculo) {
        this.idVehiculo = idVehiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Integer getModelo() {
        return modelo;
    }

    public void setModelo(Integer modelo) {
        this.modelo = modelo;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getIdConcesionarioFk() {
        return idConcesionarioFk;
    }

    public void setIdConcesionarioFk(Integer idConcesionarioFk) {
        this.idConcesionarioFk = idConcesionarioFk;
    }

    public Tipovehiculo getIdTipoFk() {
        return idTipoFk;
    }

    public void setIdTipoFk(Tipovehiculo idTipoFk) {
        this.idTipoFk = idTipoFk;
    }

    public Marca getIdMarcaFk() {
        return idMarcaFk;
    }

    public void setIdMarcaFk(Marca idMarcaFk) {
        this.idMarcaFk = idMarcaFk;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idVehiculo != null ? idVehiculo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vehiculo)) {
            return false;
        }
        Vehiculo other = (Vehiculo) object;
        if ((this.idVehiculo == null && other.idVehiculo != null) || (this.idVehiculo != null && !this.idVehiculo.equals(other.idVehiculo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entities.Vehiculo[ idVehiculo=" + idVehiculo + " ]";
    }
    
}
