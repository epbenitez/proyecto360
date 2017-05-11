

package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "ent_condominio")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Condominio  implements Serializable,  BaseEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String clave;
    private String direccion;
    private String contacto;
    private String contactoTelefono;
    private String contactoCelular;
    private String contactoMail;
    @ManyToOne(fetch = FetchType.LAZY)
    private SolicitudesTipoInmueble tipoInmueble;
    
    public Condominio(){
        
    }
    public Condominio(Long id){
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getContactoTelefono() {
        return contactoTelefono;
    }

    public void setContactoTelefono(String contactoTelefono) {
        this.contactoTelefono = contactoTelefono;
    }

    public String getContactoCelular() {
        return contactoCelular;
    }

    public void setContactoCelular(String contactoCelular) {
        this.contactoCelular = contactoCelular;
    }

    public String getContactoMail() {
        return contactoMail;
    }

    public void setContactoMail(String contactoMail) {
        this.contactoMail = contactoMail;
    }

    public SolicitudesTipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public void setTipoInmueble(SolicitudesTipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    @Override
    public String toString() {
        return "Condominio{" + "id=" + id + ", nombre=" + nombre + ", clave=" + clave + ", direccion=" + direccion + ", contacto=" + contacto + ", contactoTelefono=" + contactoTelefono + ", contactoCelular=" + contactoCelular + ", contactoMail=" + contactoMail + ", tipoInmueble=" + tipoInmueble + '}';
    }
    

}
