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
 * @author erikapatriciabenitezsoto
 */
@Entity
@Table(name = "cat_proyecto_estatus")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class ProyectoEstatus implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "ProyectoEstatus{" + "id=" + id + ", nombre=" + nombre + '}';
    }

}
