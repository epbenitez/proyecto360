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
@Table(name = "ent_configuracion_cargas_masivas")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class ConfiguracionCargaMasiva implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Concepto concepto;
    @ManyToOne
    private Condominio condominio;
    private Integer columnaFecha;
    private Integer columnaMonto;
    private Boolean cargo;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Condominio getCondominio() {
        return condominio;
    }

    public void setCondominio(Condominio condominio) {
        this.condominio = condominio;
    }


    public Integer getColumnaFecha() {
        return columnaFecha;
    }

    public void setColumnaFecha(Integer columnaFecha) {
        this.columnaFecha = columnaFecha;
    }

    public Integer getColumnaMonto() {
        return columnaMonto;
    }

    public void setColumnaMonto(Integer columnaMonto) {
        this.columnaMonto = columnaMonto;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

}
