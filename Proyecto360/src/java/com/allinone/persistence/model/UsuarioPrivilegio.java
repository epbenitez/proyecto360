package com.allinone.persistence.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.eclipse.persistence.annotations.Cache;


@Entity
@Table(name = "rmm_rol_usuario")
@Cache(alwaysRefresh = true, type = org.eclipse.persistence.annotations.CacheType.NONE)
public class UsuarioPrivilegio implements Serializable, BaseEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "rol_id", nullable = false)
    private Rol privilegio;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public Rol getPrivilegio() {
        return privilegio;
    }

    public void setPrivilegio(Rol privilegio) {
        this.privilegio = privilegio;
    }


    /**
     * Obtiene el valor de la variable usuario.
     *
     * @return el valor de la variable usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Establece el valor de la variable usuario.
     *
     * @param usuario nuevo valor de la variable usuario.
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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
        final UsuarioPrivilegio other = (UsuarioPrivilegio) obj;
        if ((this.usuario.getId() != other.usuario.getId()) ||
                (this.privilegio.getId() == null ? other.privilegio.getId() != null : !this.privilegio.getId().equals(other.privilegio.getId()))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "UsuarioPrivilegio{"+  privilegio + "," + 
                (usuario==null?"-":usuario.getUsuario()) + '}';
//        return "";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

}