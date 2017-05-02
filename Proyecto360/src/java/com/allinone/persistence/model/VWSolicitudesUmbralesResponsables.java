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
    private Departamento departamento;
    @ManyToOne
    private SolicitudesTipo tipoSolicitud;
    @ManyToOne
    private SolicitudesEstado estadoSolicitud;
//    @ManyToOne
//    private SolicitudesCategoria categoriaSolicitud;
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
    private String comentario;
    private Long consecutivo;
    private String correoElectronico;
    private Long diferenciaDias;
    private String umbral;
    

    public VWSolicitudesUmbralesResponsables(){
        
    }
    public VWSolicitudesUmbralesResponsables(Long id, String departamento, String torreNombre, Long condominioId, Long tipoSolicitudId, 
            String tipoSolicitudNombre, String estado, Date fechaSolicitud, Date  fechaSolucion, Date fechaNotificacion,Long consecutivo){
        this.id = id;
        this.departamento = new Departamento(departamento);
        this.departamento.setCondominio(new Condominio(condominioId));
        this.departamento.setTorre(new Torre(torreNombre));
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

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
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

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = solicitante;
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

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    @Override
    public String toString() {
        return "VWSolicitudesUmbralesResponsables{" + "id=" + id + ", departamento=" + departamento + ", tipoSolicitud=" + tipoSolicitud + ", estadoSolicitud=" + estadoSolicitud + ", fechaSolicitud=" + fechaSolicitud + ", fechaLectura=" + fechaLectura + ", fechaCompromiso=" + fechaCompromiso + ", fechaSolucion=" + fechaSolucion + ", fechaNotificacionCliente=" + fechaNotificacionCliente + ", solicitante=" + solicitante + ", comentario=" + comentario + ", consecutivo=" + consecutivo + ", correoElectronico=" + correoElectronico + ", diferenciaDias=" + diferenciaDias + ", umbral=" + umbral + '}';
    }
}