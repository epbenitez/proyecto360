
package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benítez
 */
@Entity
@Table(name = "rmm_solicitudes_permisos")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class SolicitudesPermisos implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Condominio condominio;
    @ManyToOne
    private SolicitudesTipo tipoSolicitud;
    @ManyToOne
    private Usuario usuario;
    private String permiso;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    @Override
    public String toString() {
        return "SolicitudesPermisos{" + "id=" + id + ", condominio=" + condominio + ", tipoSolicitud=" + tipoSolicitud + ", usuario=" + usuario + ", permiso=" + permiso + '}';
    }

}
