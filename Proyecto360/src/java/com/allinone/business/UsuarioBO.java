/**
 * ALL IN ONE DIRECCION DE SERVICIOS
 * ESTUDIANTILES Created on : 18-ago-2015, 12:52:17
 *
 */
package com.allinone.business;

import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class UsuarioBO extends BaseBO {
    
    public UsuarioBO(Service service) {
        super(service);
    }
    
    public Boolean actualizaContrasenia(String usuario, String contraseniaActual, String contraseniaNueva) {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (u == null ) {
            return false;
        }
        if (u.getPassword().equals(contraseniaActual)) {
            u.setPassword(contraseniaNueva);
            service.getUsuarioDao().update(u);
            return true;
        }
        return false;
    }
    
    public Usuario getUsuario(Long id) {
        Usuario usuario = service.getUsuarioDao().findById(id);
        return usuario;
    }
    
    public Boolean borrarUsuario(Usuario usuario) {
        try {
            service.getUsuarioDao().update(usuario);
        } catch (Exception e) {
            return Boolean.FALSE;
        }
        
        return Boolean.TRUE;
    }
}
