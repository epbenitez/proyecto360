package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Representación abstracta de una direccion en base de datos
 * <br>Tabla: ent_direccion
 *
 * @author Patricia Benitez
 * @version $Rev: 1165 $
 * @since   1.0
 */
@Entity
@Table(name = "ent_direccion")
public class Direccion implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private RelacionGeografica relacionGeografica;

    private String calle;

    private String codigoPostal;

    private String numeroInterior;

    private String numeroExterior;

    public Direccion() {
    }

    /**
     * Obtiene el valor de id
     *
     * @return el valor de id
     */
    @Override
    public Long getId() {
        return id;
    }

    /**
     * Establece el valor de id
     *
     * @param id nuevo valor de id
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getNumeroExterior() {
        return numeroExterior;
    }

    public void setNumeroExterior(String numeroExterior) {
        this.numeroExterior = numeroExterior;
    }

    public String getNumeroInterior() {
        return numeroInterior;
    }

    public void setNumeroInterior(String numeroInterior) {
        this.numeroInterior = numeroInterior;
    }

    public RelacionGeografica getRelacionGeografica() {
        return relacionGeografica;
    }

    public void setRelacionGeografica(RelacionGeografica relacionGeografica) {
        this.relacionGeografica = relacionGeografica;
    }

    @Override
    public String toString() {
        return "Direccion{" + "id=" + id + "relacionGeografica=" + relacionGeografica +
                "calle=" + calle + "codigoPostal=" + codigoPostal +
                "numeroInterior=" + numeroInterior + "numeroExterior=" + numeroExterior + '}';
    }



    

   

}
