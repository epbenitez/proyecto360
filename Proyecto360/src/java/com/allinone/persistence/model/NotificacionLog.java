package com.allinone.persistence.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "ent_notificaciones_log")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class NotificacionLog implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Notificacion notificacion;
    private NotificacionGrupo grupo;
    private Usuario usuario;
    private Departamento departamento;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fecha;
    private String identificador;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }

    public NotificacionGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(NotificacionGrupo grupo) {
        this.grupo = grupo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "NotificacionLog{" + "id=" + id + ", notificacion=" + notificacion + ", grupo=" + grupo + ", usuario=" + usuario + ", departamento=" + departamento + ", fecha=" + fecha + ", identificador=" + identificador + '}';
    }


}
