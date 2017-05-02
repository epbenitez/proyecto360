package com.allinone.persistence.model;

import com.allinone.util.ElementoGeografico;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cat_estado")
public class EntidadFederativa extends ElementoGeografico<MunicipioDelegacion>
        implements Serializable, Comparable<EntidadFederativa>, BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;

    public EntidadFederativa() {
    }

    public EntidadFederativa(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el valor de la variable id
     *
     * @return el valor de la variable id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor de la variable id
     *
     * @param id nuevo valor de la variable id
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el valor de la variable nombre
     *
     * @return el valor de la variable nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el valor de la variable nombre
     *
     * @param nombre nuevo valor de la variable nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EntidadFederativa other = (EntidadFederativa) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.nombre == null) ? (other.nombre != null) : !this.nombre.equals(other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 89 * hash + (this.nombre != null ? this.nombre.hashCode() : 0);
        return hash;
    }

    @Override
    public int compareTo(EntidadFederativa obj) {
//        int compareValue = 0;
//        if (this.getId() < obj.getId()) {
//            compareValue = -1;
//        } else if (this.getId() > obj.getId()) {
//            compareValue = 1;
//        }
//        //Trato especial de  EntidadFederativa
//        if (this.getId().intValue() == 9) {
//            compareValue = -1;
//        } else if (obj.getId().intValue() == 9) {
//            compareValue = 1;
//        } else if (this.getId().intValue() == 15) {
//            compareValue = -1;
//        } else if (obj.getId().intValue() == 15) {
//            compareValue = 1;
//        }
//        //Si son iguales
//        if (this.getId().intValue() == obj.getId().intValue()) {
//            compareValue = 0;
//        }
//        return compareValue;
        String nombreTmp = nombre == null ? null : nombre.toLowerCase();
        String objTmp = obj.getNombre() == null ? null : obj.getNombre().toLowerCase();

        return nombreTmp.compareTo(objTmp);
    }

    @Override
    public String toString() {
        return "EntidadFederativa{" + id + "," + nombre + '}';
    }
}
