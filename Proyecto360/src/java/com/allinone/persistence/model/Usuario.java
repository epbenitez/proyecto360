
package com.allinone.persistence.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;

/**
 * 
 * @author Patricia Benitez
 */
@Entity
@Table(name = "ent_usuario")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class Usuario implements BaseEntity,Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "usuario")
    private String usuario;

    @Column(name = "contrasenia")
    private String password;

    @Column(name = "activo")
    private boolean activo = true;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private Set<UsuarioPrivilegio> privilegios;

    public Usuario() {
        privilegios = new HashSet<UsuarioPrivilegio>();
    }
    
    public Usuario(Long id) {
        privilegios = new HashSet<UsuarioPrivilegio>();
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    /**
     * Obtiene el valor de la variable usuario
     *
     * @return el valor de la variable usuario
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Establece el valor de la variable usuario
     *
     * @param usuario nuevo valor de la variable usuario
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el valor de la variable password
     *
     * @return el valor de la variable password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Establece el valor de la variable password
     *
     * @param password nuevo valor de la variable password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Obtiene el valor de la variable activo
     *
     * @return el valor de la variable activo
     */
    public boolean isActivo() {
        return activo;
    }

    /**
     * Establece el valor de la variable activo
     *
     * @param activo nuevo valor de la variable activo
     */
    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    /**
     * Obtiene el valor de la variable privilegios
     *
     * @return el valor de la variable privilegios
     */
    public Set<UsuarioPrivilegio> getPrivilegios() {
        return privilegios;
    }

    /**
     * Establece el valor de la variable privilegios
     *
     * @param privilegios nuevo valor de la variable privilegios
     */
    public void setPrivilegios(Set<UsuarioPrivilegio> privilegios) {
        this.privilegios = privilegios;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", usuario=" + usuario + ", password=" + password + ", activo=" + activo + '}';
    }
}