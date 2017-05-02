
/**
* ALL IN ONE
* 
* 2015
**/

package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 *
 * @author Patricia Benitez 
 */
@Entity
@Table(name = "cat_genero")
@Cache(alwaysRefresh=true,type= org.eclipse.persistence.annotations.CacheType.NONE)
public class Genero implements Serializable, BaseEntity {

    @Id
    private Long id;
    private String nombre;
    private String clave;

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

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    @Override
    public String toString() {
        return "Genero{" + "id=" + id + ", nombre=" + nombre + ", clave=" + clave + '}';
    }
    
    

}
