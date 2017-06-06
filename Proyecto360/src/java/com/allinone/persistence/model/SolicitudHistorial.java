package com.allinone.persistence.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benítez
 */
@Entity
@Table(name = "ent_solicitudes_historial")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class SolicitudHistorial implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Solicitud solicitud;
    @ManyToOne
    private SolicitudesEstado estadoSolicitud;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private String comentario;
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solicitudHistorial", cascade = {CascadeType.PERSIST})
    @OrderBy("id DESC")
    private List<SolicitudesDocumento> documentos = new ArrayList<SolicitudesDocumento>();

    @Transient
    private String usuarioRegistra;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public SolicitudesEstado getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(SolicitudesEstado estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(String usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }
    
    
    public void addDocumentos(SolicitudesDocumento docto) {
        this.documentos.add(docto);
        if (docto.getSolicitudHistorial() != this) {
            docto.setSolicitudHistorial(this);
        }
    }

    public List<SolicitudesDocumento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<SolicitudesDocumento> documentos) {
        this.documentos = documentos;
    }
    
    @Override
    public String toString() {
        return "SolicitudHistorial{" + "id=" + id + ", solicitud=" + solicitud + ", estadoSolicitud=" + estadoSolicitud + ", fecha=" + fecha + ", comentario=" + comentario + ", usuario=" + usuario + '}';
    }

}
