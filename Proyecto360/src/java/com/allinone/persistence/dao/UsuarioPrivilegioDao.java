/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allinone.persistence.dao;

import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioPrivilegio;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public interface UsuarioPrivilegioDao extends DaoBase<UsuarioPrivilegio, Long> {

    public List<UsuarioPrivilegio> getPrivilegiosByUser(Usuario u);

    public void borraPrivilegios(Usuario u);
    
    public void borraPrivilegios(Usuario u, Rol r);
    
}
