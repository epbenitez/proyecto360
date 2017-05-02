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
@Table(name = "cat_equipo_mantenimiento_frecuencia")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class EquipoMantenimientoFrecuencia  implements Serializable,  BaseEntity {

private static final long serialVersionUID = 1L;
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
        return "EquipoMantenimientoFrecuencia{" + "id=" + id + ", nombre=" + nombre + '}';
    }

    
}
