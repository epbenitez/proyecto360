package com.allinone.persistence.model;

import java.io.Serializable;
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
 * @author Patricia Benítez
 */
@Entity
@Table(name = "ent_abonos_datos_pago")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class AbonoDatosPago implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private String titular;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaEmision;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaPago;
    @ManyToOne
    private Banco banco;
    @ManyToOne
    private AbonoTipo abonoTipo;
    private Double monto;
    
    private Usuario usuario;
    
    @Transient
    private Departamento departamento;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }


    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public AbonoTipo getAbonoTipo() {
        return abonoTipo;
    }

    public void setAbonoTipo(AbonoTipo abonoTipo) {
        this.abonoTipo = abonoTipo;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    
    
    
    @Override
    public String toString() {
        return "AbonoDatosPago{" + "id=" + id + ", numero=" + numero + ", titular=" + titular + ", fechaEmision=" + fechaEmision + ", fechaPago=" + fechaPago + ", banco=" + banco + ", abonoTipo=" + abonoTipo + ", monto=" + monto + '}';
    }
    

}
