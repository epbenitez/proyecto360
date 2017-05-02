package com.allinone.persistence.model;

import com.allinone.util.Util;
import java.io.Serializable;
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
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "vw_proyecto_siguiente_tarea")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class VwProyectoSiguienteTarea implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String condominio;
    private Long condominioId;
    private String tipo;
    private Long tipoId;
    private String prioridad;
    private Long prioridadId;
    private String estatus;
    private Long estatusId;
    private String cuota;
    private String categoria;
    private Long categoriaId;
    private Boolean visible;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    private String descripcion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaVencimiento;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSigTarea;
    private String titulo;

    @Transient
    private Long diasTranscurridos;
    @Transient
    private Long diasRestantes;
    @Transient
    private ProyectoTarea siguienteTarea;

    public VwProyectoSiguienteTarea() {

    }
    
    public VwProyectoSiguienteTarea(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getCuota() {
        return cuota;
    }

    public void setCuota(String cuota) {
        this.cuota = cuota;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaSigTarea() {
        return fechaSigTarea;
    }

    public void setFechaSigTarea(Date fechaSigTarea) {
        this.fechaSigTarea = fechaSigTarea;
    }

    public ProyectoTarea getSiguienteTarea() {
        return siguienteTarea;
    }

    public void setSiguienteTarea(ProyectoTarea siguienteTarea) {
        this.siguienteTarea = siguienteTarea;
    }

    

    public Long getDiasTranscurridos() {
        if (getFechaRegistro() != null) {
            return Util.numeroDeDias(getFechaRegistro(), new Date());
        } else {
            return 0L;
        }
    }

    public void setDiasTranscurridos(Long diasTranscurridos) {
        this.diasTranscurridos = diasTranscurridos;
    }

    public Long getDiasRestantes() {
        if (getFechaRegistro() != null && getFechaVencimiento()!=null) {
            return Util.numeroDeDias(getFechaRegistro(), getFechaVencimiento());
        } else {
            return 0L;
        }
    }

    public void setDiasRestantes(Long diasRestantes) {
        this.diasRestantes = diasRestantes;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }

    public Long getEstatusId() {
        return estatusId;
    }

    public void setEstatusId(Long estatusId) {
        this.estatusId = estatusId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Long getPrioridadId() {
        return prioridadId;
    }

    public void setPrioridadId(Long prioridadId) {
        this.prioridadId = prioridadId;
    }

    @Override
    public String toString() {
        return "VwProyectoSiguienteTarea{" + "id=" + id + ", condominio=" + condominio + ", condominioId=" + condominioId + ", tipo=" + tipo + ", tipoId=" + tipoId + ", prioridad=" + prioridad + ", prioridadId=" + prioridadId + ", estatus=" + estatus + ", estatusId=" + estatusId + ", cuota=" + cuota + ", categoria=" + categoria + ", categoriaId=" + categoriaId + ", visible=" + visible + ", fechaRegistro=" + fechaRegistro + ", descripcion=" + descripcion + ", fechaVencimiento=" + fechaVencimiento + ", fechaSigTarea=" + fechaSigTarea + ", titulo=" + titulo + ", diasTranscurridos=" + diasTranscurridos + ", diasRestantes=" + diasRestantes + ", siguienteTarea=" + siguienteTarea + '}';
    }

}
