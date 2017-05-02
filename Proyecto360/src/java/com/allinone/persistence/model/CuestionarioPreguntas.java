/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

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
 * @author Patricia Benitez
 */
@Entity
@Table(name = "ent_cuestionario_pregunta")
@Cache(alwaysRefresh=true,type= org.eclipse.persistence.annotations.CacheType.NONE)
public class CuestionarioPreguntas implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private CuestionarioPreguntaTipo  cuestionarioPreguntaTipo;
    private String nombre;
    private Integer orden;
    private Integer activo;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

    public Integer getActivo() {
        return activo;
    }

    public void setActivo(Integer activo) {
        this.activo = activo;
    }

    public CuestionarioPreguntaTipo getCuestionarioPreguntaTipo() {
        return cuestionarioPreguntaTipo;
    }

    public void setCuestionarioPreguntaTipo(CuestionarioPreguntaTipo cuestionarioPreguntaTipo) {
        this.cuestionarioPreguntaTipo = cuestionarioPreguntaTipo;
    }

    @Override
    public String toString() {
        return "CuestionarioPreguntas{" + "id=" + id + ", cuestionarioPreguntaTipo=" + cuestionarioPreguntaTipo + ", nombre=" + nombre + ", orden=" + orden + ", activo=" + activo + '}';
    }
}