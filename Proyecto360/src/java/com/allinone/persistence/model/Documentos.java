package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Victor Lozano
 */
@Entity
@Table(name = "ent_documentos")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Documentos implements Serializable, BaseEntity {
    @Id
    private Long id;
    private boolean estudioSocioeconomico;
    private boolean cartaCompromiso;
    private boolean curp;
    private boolean comprobanteIngresosEgresos;
    private boolean acuseSubes;
    private boolean folioSubes;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public boolean isEstudioSocioeconomico() {
        return estudioSocioeconomico;
    }

    public void setEstudioSocioeconomico(boolean estudioSocioeconimico) {
        this.estudioSocioeconomico = estudioSocioeconimico;
    }

    public boolean isCartaCompromiso() {
        return cartaCompromiso;
    }

    public void setCartaCompromiso(boolean cartaCompromiso) {
        this.cartaCompromiso = cartaCompromiso;
    }

    public boolean isCurp() {
        return curp;
    }

    public void setCurp(boolean curp) {
        this.curp = curp;
    }

    public boolean isComprobanteIngresosEgresos() {
        return comprobanteIngresosEgresos;
    }

    public void setComprobanteIngresosEgresos(boolean comprobanteIngresosEgresos) {
        this.comprobanteIngresosEgresos = comprobanteIngresosEgresos;
    }

    public boolean isAcuseSubes() {
        return acuseSubes;
    }

    public void setAcuseSubes(boolean acuseSubes) {
        this.acuseSubes = acuseSubes;
    }

    public boolean isFolioSubes() {
        return folioSubes;
    }

    public void setFolioSubes(boolean folioSubes) {
        this.folioSubes = folioSubes;
    }
}