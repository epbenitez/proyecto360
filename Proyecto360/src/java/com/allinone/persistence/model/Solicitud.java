
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
    private SolicitudesTipoServicio tipoServicio;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesArea area;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesCategoria categoriaSolicitud;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesEstado estadoSolicitud;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaIngresoTicket;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaLectura;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaProgramada;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaAtencion;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaNotificacionCliente;
    private String solicitante;
    private String asunto;
    private String descripcion;
    private Long consecutivo;
    private String correoElectronico;
    
    private Usuario usuarioSolicita;
    
    @Transient
    private SolicitudesUmbrales umbral;

    public Solicitud(){
        
    }
    public Solicitud(Long id, Long condominioId, Long tipoServicioId, 
            String tipoServicioNombre, String estado, Date fechaIngresoTicket, Date  fechaAtencion, Date fechaNotificacion,Long consecutivo){
        this.id = id;
        this.condominio = new Condominio(condominioId);
        this.tipoServicio = new SolicitudesTipoServicio(tipoServicioId,tipoServicioNombre);
        this.estadoSolicitud = new SolicitudesEstado(estado);
        this.fechaIngresoTicket = fechaIngresoTicket;
        this.fechaAtencion = fechaAtencion;
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
    

    public SolicitudesEstado getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(SolicitudesEstado estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
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

    public Date getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public Date getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(Date fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
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

    public SolicitudesTipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(SolicitudesTipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public Usuario getUsuarioSolicita() {
        return usuarioSolicita;
    }

    public void setUsuarioSolicita(Usuario usuarioSolicita) {
        this.usuarioSolicita = usuarioSolicita;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "Solicitud{" + "id=" + id + ", condominio=" + condominio + ", tipoServicio=" + tipoServicio + ", area=" + area + ", categoriaSolicitud=" + categoriaSolicitud + ", estadoSolicitud=" + estadoSolicitud + ", fechaIngresoTicket=" + fechaIngresoTicket + ", fechaLectura=" + fechaLectura + ", fechaProgramada=" + fechaProgramada + ", fechaAtencion=" + fechaAtencion + ", fechaNotificacionCliente=" + fechaNotificacionCliente + ", solicitante=" + solicitante + ", asunto=" + asunto + ", descripcion=" + descripcion + ", consecutivo=" + consecutivo + ", correoElectronico=" + correoElectronico + ", usuarioSolicita=" + usuarioSolicita + ", umbral=" + umbral + '}';
    }
    
    

}
