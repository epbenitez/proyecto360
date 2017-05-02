package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author patriciabenitez
 */
@Entity
@Table(name = "rmm_notificaciones_grupos")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class NotificacionesGrupos implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Notificacion notificacion;
    private NotificacionGrupo grupo;
    
    

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

    @Override
    public String toString() {
        return "NotificacionesGrupos{" + "id=" + id + ", notificacion=" + notificacion + ", grupo=" + grupo + '}';
    }

}
