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
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "rmm_solicitudes_tipo_area")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class SolicitudesTipoArea  implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private SolicitudesTipoServicio tipoServicio;
    @ManyToOne
    private SolicitudesArea areaSolicitud;
    @ManyToOne
    private SolicitudesCategoria categoriaSolicitud;
    @ManyToOne
    private SolicitudesTipoInmueble tipoInmuebleSolicitud;

    @Override
    public Long getId() {
        return id;
    }
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public SolicitudesTipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(SolicitudesTipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public SolicitudesCategoria getCategoriaSolicitud() {
        return categoriaSolicitud;
    }

    public void setCategoriaSolicitud(SolicitudesCategoria categoriaSolicitud) {
        this.categoriaSolicitud = categoriaSolicitud;
    }

    public SolicitudesTipoInmueble getTipoInmuebleSolicitud() {
        return tipoInmuebleSolicitud;
    }

    public void setTipoInmuebleSolicitud(SolicitudesTipoInmueble tipoInmuebleSolicitud) {
        this.tipoInmuebleSolicitud = tipoInmuebleSolicitud;
    }

    public SolicitudesArea getAreaSolicitud() {
        return areaSolicitud;
    }

    public void setAreaSolicitud(SolicitudesArea areaSolicitud) {
        this.areaSolicitud = areaSolicitud;
    }
    
    
}
