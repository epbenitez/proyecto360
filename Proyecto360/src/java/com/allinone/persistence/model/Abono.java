package com.allinone.persistence.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benitez
 */
@Entity
@Table(name = "ent_abonos")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Abono implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Departamento departamento;
    @ManyToOne
    private Concepto concepto;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPago;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaAdmon;
    private Double monto;
    private String identificadorcarga;
    private AbonoDatosPago datosPago;
    
    public Abono(){
        
    }
    
    public Abono(Long id, Concepto concepto, Double monto){
        this.id = id;
        this.concepto = concepto;
        this.monto = monto;
    }

    @Transient
    private String montoFormato;

    @Override
    public Long getId() {
        return id;
    }
    @Override
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Date getFechaAdmon() {
        return fechaAdmon;
    }

    public void setFechaAdmon(Date fechaAdmon) {
        this.fechaAdmon = fechaAdmon;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public String getIdentificadorcarga() {
        return identificadorcarga;
    }

    public void setIdentificadorcarga(String identificadorcarga) {
        this.identificadorcarga = identificadorcarga;
    }

    public String getMontoFormato() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        return df.format(this.monto);
    }

    public void setMontoFormato(String montoFormato) {
        this.montoFormato = montoFormato;
    }

    public AbonoDatosPago getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(AbonoDatosPago datosPago) {
        this.datosPago = datosPago;
    }

    @Override
    public String toString() {
        return "Abono{" + "id=" + id + ", departamento=" + departamento + ", concepto=" + concepto + ", fechaPago=" + fechaPago + ", fechaAdmon=" + fechaAdmon + ", monto=" + monto + ", identificadorcarga=" + identificadorcarga + ", datosPago=" + datosPago + ", montoFormato=" + montoFormato + '}';
    }

}
