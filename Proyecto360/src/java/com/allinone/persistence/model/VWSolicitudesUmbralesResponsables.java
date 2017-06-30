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
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "vw_solicitudes_umbrales_responsable_mail")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class VWSolicitudesUmbralesResponsables implements Serializable, BaseEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Solicitud solicitud;
    @ManyToOne
    private SolicitudesEstado estadoSolicitud;
    @ManyToOne
    private SolicitudesCategoria categoriaSolicitud;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaIngresoTicket;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaLectura;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaCompromiso;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaatencion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaNotificacionCliente;
    private String solicitante;
    private String comentario;
    private Long consecutivo;
    private String correoElectronico;
    private Long diferenciaDias;
    private String umbral;
    

    public VWSolicitudesUmbralesResponsables(){
        
    }   
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SolicitudesEstado getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(SolicitudesEstado estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public SolicitudesCategoria getCategoriaSolicitud() {
        return categoriaSolicitud;
    }

    public void setCategoriaSolicitud(SolicitudesCategoria categoriaSolicitud) {
        this.categoriaSolicitud = categoriaSolicitud;
    }

    public Date getFechaIngresoTicket() {
        return fechaIngresoTicket;
    }

    public void setFechaIngresoTicket(Date fechaIngresoTicket) {
        this.fechaIngresoTicket = fechaIngresoTicket;
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

    public Date getFechaatencion() {
        return fechaatencion;
    }

    public void setFechaatencion(Date fechaatencion) {
        this.fechaatencion = fechaatencion;
    }

    public Date getFechaNotificacionCliente() {
        return fechaNotificacionCliente;
    }

    public void setFechaNotificacionCliente(Date fechaNotificacionCliente) {
        this.fechaNotificacionCliente = fechaNotificacionCliente;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Long getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Long consecutivo) {
        this.consecutivo = consecutivo;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public Long getDiferenciaDias() {
        return diferenciaDias;
    }

    public void setDiferenciaDias(Long diferenciaDias) {
        this.diferenciaDias = diferenciaDias;
    }

    public String getUmbral() {
        return umbral;
    }

    public void setUmbral(String umbral) {
        this.umbral = umbral;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    @Override
    public String toString() {
        return "VWSolicitudesUmbralesResponsables{" + "id=" + id + ", solicitud=" + solicitud + ", estadoSolicitud=" + estadoSolicitud + ", categoriaSolicitud=" + categoriaSolicitud + ", fechaIngresoTicket=" + fechaIngresoTicket + ", fechaLectura=" + fechaLectura + ", fechaCompromiso=" + fechaCompromiso + ", fechaatencion=" + fechaatencion + ", fechaNotificacionCliente=" + fechaNotificacionCliente + ", solicitante=" + solicitante + ", comentario=" + comentario + ", consecutivo=" + consecutivo + ", correoElectronico=" + correoElectronico + ", diferenciaDias=" + diferenciaDias + ", umbral=" + umbral + '}';
    }
    
}