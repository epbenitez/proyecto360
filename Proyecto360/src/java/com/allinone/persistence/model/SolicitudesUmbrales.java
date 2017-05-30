
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
@Table(name = "rmm_solicitudes_umbrales")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class SolicitudesUmbrales implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Condominio condominio;
    @ManyToOne
    private SolicitudesTipoServicio tipoServicio;
//    @ManyToOne
//    private SolicitudesCategoria categoriaSolicitud;
    
    private Integer diasUmbralVerde;
    private Integer diasUmbralAmarillo;
    private Integer diasUmbralNaranja;
    private Integer diasUmbralRojo;
    
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

    public SolicitudesTipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(SolicitudesTipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

//    public SolicitudesCategoria getCategoriaSolicitud() {
//        return categoriaSolicitud;
//    }
//
//    public void setCategoriaSolicitud(SolicitudesCategoria categoriaSolicitud) {
//        this.categoriaSolicitud = categoriaSolicitud;
//    }

    public Integer getDiasUmbralVerde() {
        return diasUmbralVerde;
    }

    public void setDiasUmbralVerde(Integer diasUmbralVerde) {
        this.diasUmbralVerde = diasUmbralVerde;
    }

    public Integer getDiasUmbralAmarillo() {
        return diasUmbralAmarillo;
    }

    public void setDiasUmbralAmarillo(Integer diasUmbralAmarillo) {
        this.diasUmbralAmarillo = diasUmbralAmarillo;
    }

    public Integer getDiasUmbralNaranja() {
        return diasUmbralNaranja;
    }

    public void setDiasUmbralNaranja(Integer diasUmbralNaranja) {
        this.diasUmbralNaranja = diasUmbralNaranja;
    }

    public Integer getDiasUmbralRojo() {
        return diasUmbralRojo;
    }

    public void setDiasUmbralRojo(Integer diasUmbralRojo) {
        this.diasUmbralRojo = diasUmbralRojo;
    }

    @Override
    public String toString() {
        return "SolicitudesUmbrales{" + "id=" + id + ", condominio=" + condominio + ", tipoServicio=" + tipoServicio + ", diasUmbralVerde=" + diasUmbralVerde + ", diasUmbralAmarillo=" + diasUmbralAmarillo + ", diasUmbralNaranja=" + diasUmbralNaranja + ", diasUmbralRojo=" + diasUmbralRojo + '}';
    }

}
