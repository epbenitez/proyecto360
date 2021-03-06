package com.allinone.persistence.model;

import com.allinone.domain.CodigoPostal;
import com.allinone.util.ElementoGeografico;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_localidad_colonia")
public class LocalidadColonia extends ElementoGeografico<CodigoPostal> 
        implements Serializable, Comparable<LocalidadColonia>, BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String clave;

    private String nombre;

    public LocalidadColonia(){

    }
    public LocalidadColonia(Long id){
        this.id = id;
    }
    /**
     * Obtiene el valor de la variable id.
     *
     * @return el valor de la variable id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor de la variable id.
     *
     * @param id nuevo valor de la variable id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el valor de la variable clave.
     *
     * @return el valor de la variable clave.
     */
    public String getClave() {
        return clave;
    }

    /**
     * Establece el valor de la variable clave.
     *
     * @param clave nuevo valor de la variable clave.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }

    /**
     * Obtiene el valor de la variable nombre.
     *
     * @return el valor de la variable nombre.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de la variable nombre.
     *
     * @param nombre nuevo valor de la variable nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LocalidadColonia other = (LocalidadColonia) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.clave == null) ? (other.clave != null) : !this.clave.equals(other.clave)) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 67 * hash + (this.clave != null ? this.clave.hashCode() : 0);
        hash = 67 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    /**
     * {@inheritDoc}
     */
    public int compareTo(LocalidadColonia obj) {
        String nombreTmp = nombre == null ? null : nombre.toLowerCase();
        String objTmp = obj.getNombre() == null ? null : obj.getNombre().toLowerCase();

        return nombreTmp.compareTo(objTmp);
    }

    @Override
    public String toString() {
        return "LocalidadColonia{"  + id + "," + clave + "," + nombre + '}';
    }

    

}