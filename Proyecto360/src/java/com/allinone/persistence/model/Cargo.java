/**
 * SISTEMA DE BECAS DEL INSTITUTO POLITECNICO NACIONAL DIRECCION DE SERVICIOS
 * ESTUDIANTILES Created on : 29-dic-2015, 16:48:29
*
 */
package com.allinone.persistence.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benitez
 */
@Entity
@Table(name = "ent_cargos")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Cargo implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Departamento departamento;
    private Concepto concepto;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPeriodo;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaDescuento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaIntereses;
    private Double monto;
    private String identificadorcarga;
    @Transient
    private String montoFormato;
    @Transient
    private String montoFormatoConDescuento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Date getFechaPeriodo() {
        return fechaPeriodo;
    }

    public void setFechaPeriodo(Date fechaPeriodo) {
        this.fechaPeriodo = fechaPeriodo;
    }

    public Date getFechaDescuento() {
        return fechaDescuento;
    }

    public void setFechaDescuento(Date fechaDescuento) {
        this.fechaDescuento = fechaDescuento;
    }

    public Date getFechaIntereses() {
        return fechaIntereses;
    }

    public void setFechaIntereses(Date fechaIntereses) {
        this.fechaIntereses = fechaIntereses;
    }

    

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    
    public String getMontoFormato() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return df.format(this.monto);
    }

    public void setMontoFormato(String montoFormato) {
        this.montoFormato = montoFormato;
    }

    public String getIdentificadorcarga() {
        return identificadorcarga;
    }

    public void setIdentificadorcarga(String identificadorcarga) {
        this.identificadorcarga = identificadorcarga;
    }

    public String getMontoFormatoConDescuento() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return this.concepto.getId()==1?df.format(this.monto-300):df.format(this.monto);
    }

    public void setMontoFormatoConDescuento(String montoFormatoConDescuento) {
        this.montoFormatoConDescuento = montoFormatoConDescuento;
    }
    

    @Override
    public String toString() {
        return "Cargo{" + "id=" + id + ", departamento=" + departamento + ", concepto=" + concepto + ", fechaPeriodo=" + fechaPeriodo + ", fechaDescuento=" + fechaDescuento + ", fechaIntereses=" + fechaIntereses + ", monto=" + monto + ", identificadorcarga=" + identificadorcarga + '}';
    }
    

}
