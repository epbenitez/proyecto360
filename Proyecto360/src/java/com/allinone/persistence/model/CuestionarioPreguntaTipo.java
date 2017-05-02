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
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Tania G. Sánchez
 */
@Entity
@Table(name = "ent_cuestionario_pregunta_tipo")
@Cache(alwaysRefresh=true,type= org.eclipse.persistence.annotations.CacheType.NONE)
public class CuestionarioPreguntaTipo implements Serializable, BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;

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
    
    @Override
    public String toString() {
        return "EntCuestionarioPreguntaTipo{" + "id=" + id + ", nombre=" + nombre + '}';
    }
}