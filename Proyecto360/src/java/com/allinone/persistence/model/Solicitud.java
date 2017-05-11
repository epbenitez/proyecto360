
package com.allinone.persistence.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "ent_solicitudes")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Solicitud implements Serializable, BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Condominio condominio;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesTipo tipoSolicitud;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesArea area;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesCategoria categoriaSolicitud;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesEstado estadoSolicitud;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSolicitud;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaLectura;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaSolucion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaNotificacionCliente;
    private String solicitante;
    private String asunto;
    private String descripcion;
    private Long consecutivo;
    
    @Transient
    private SolicitudesUmbrales umbral;

    public Solicitud(){
        
    }
    public Solicitud(Long id, Long condominioId, Long tipoSolicitudId, 
            String tipoSolicitudNombre, String estado, Date fechaSolicitud, Date  fechaSolucion, Date fechaNotificacion,Long consecutivo){
        this.id = id;
        this.condominio = new Condominio(condominioId);
        this.tipoSolicitud = new SolicitudesTipo(tipoSolicitudId,tipoSolicitudNombre);
        this.estadoSolicitud = new SolicitudesEstado(estado);
        this.fechaSolicitud = fechaSolicitud;
        this.fechaSolucion = fechaSolucion;
        this.fechaNotificacionCliente = fechaNotificacion;
        this.consecutivo = consecutivo;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }
    public SolicitudesTipo getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(SolicitudesTipo tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public SolicitudesEstado getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(SolicitudesEstado estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaLectura() {
        return fechaLectura;
    }

    public void setFechaLectura(Date fechaLectura) {
        this.fechaLectura = fechaLectura;
    }

    public Date getFechaCompromiso() {
        return fechaCompromiso;
    }

    public void setFechaCompromiso(Date fechaCompromiso) {
        this.fechaCompromiso = fechaCompromiso;
    }

    public Date getFechaSolucion() {
        return fechaSolucion;
    }

    public void setFechaSolucion(Date fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public Date getFechaNotificacionCliente() {
        return fechaNotificacionCliente;
    }

    public void setFechaNotificacionCliente(Date fechaNotificacionCliente) {
        this.fechaNotificacionCliente = fechaNotificacionCliente;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SolicitudesCategoria getCategoriaSolicitud() {
        return categoriaSolicitud;
    }

    public void setCategoriaSolicitud(SolicitudesCategoria categoriaSolicitud) {
        this.categoriaSolicitud = categoriaSolicitud;
    }

    public SolicitudesUmbrales getUmbral() {
        return umbral;
    }

    public void setUmbral(SolicitudesUmbrales umbral) {
        this.umbral = umbral;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public SolicitudesArea getArea() {
        return area;
    }

    public void setArea(SolicitudesArea area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return "Solicitud{" + "id=" + id + ", condominio=" + condominio + ", tipoSolicitud=" + tipoSolicitud + ", area=" + area + ", categoriaSolicitud=" + categoriaSolicitud + ", estadoSolicitud=" + estadoSolicitud + ", fechaSolicitud=" + fechaSolicitud + ", fechaLectura=" + fechaLectura + ", fechaCompromiso=" + fechaCompromiso + ", fechaSolucion=" + fechaSolucion + ", fechaNotificacionCliente=" + fechaNotificacionCliente + ", solicitante=" + solicitante + ", asunto=" + asunto + ", descripcion=" + descripcion + ", consecutivo=" + consecutivo + ", umbral=" + umbral + '}';
    }
    
}
