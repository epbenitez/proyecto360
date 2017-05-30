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
 * @author Patricia Benítez
 */
@Entity
@Table(name = "cat_solicitudes_tipo_servicio")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class SolicitudesTipoServicio implements Serializable, BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String clave;
    
    public SolicitudesTipoServicio(){
        
    }
    public SolicitudesTipoServicio(Long id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }
    public SolicitudesTipoServicio(String nombre){
        this.nombre = nombre;
    }
    
    public SolicitudesTipoServicio(Long id){
        this.id = id;
    }

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "SolicitudesTipoServicio{" + "id=" + id + ", nombre=" + nombre + ", clave=" + clave + '}';
    }
    

}
