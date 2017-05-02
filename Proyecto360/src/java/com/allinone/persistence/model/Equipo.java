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
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "ent_equipo")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Equipo  implements Serializable,  BaseEntity {

private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne
    private Condominio condominio;
    @ManyToOne
    private EquipoTipo tipoEquipo;
    @ManyToOne
    private EquipoSistema sistema;
    @ManyToOne
    private EquipoSubsistema subsistema;
    @ManyToOne
    private EquipoMantenimientoFrecuencia frecuencia;
    private Integer unidades;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechaInicio;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }

    public EquipoTipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(EquipoTipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public EquipoSistema getSistema() {
        return sistema;
    }

    public void setSistema(EquipoSistema sistema) {
        this.sistema = sistema;
    }

    public EquipoSubsistema getSubsistema() {
        return subsistema;
    }

    public void setSubsistema(EquipoSubsistema subsistema) {
        this.subsistema = subsistema;
    }

    public EquipoMantenimientoFrecuencia getFrecuencia() {
        return frecuencia;
    }

    public void setFrecuencia(EquipoMantenimientoFrecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }

    public Integer getUnidades() {
        return unidades;
    }

    public void setUnidades(Integer unidades) {
        this.unidades = unidades;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    @Override
    public String toString() {
        return "Equipo{" + "id=" + id + ", nombre=" + nombre + ", condominio=" + condominio + ", tipoEquipo=" + tipoEquipo + ", sistema=" + sistema + ", subsistema=" + subsistema + ", frecuencia=" + frecuencia + ", unidades=" + unidades + ", fechaInicio=" + fechaInicio + '}';
    }

    
}
