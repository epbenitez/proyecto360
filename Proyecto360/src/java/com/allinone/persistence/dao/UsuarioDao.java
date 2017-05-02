/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.persistence.dao;

import com.allinone.persistence.model.Usuario;

/**
 *
 * @author Patricia Benitez
 */
public interface UsuarioDao extends DaoBase<Usuario, Long> {

    public Usuario findById(String id);

    public Usuario findByIdAlumno(Long idAlumno);

}
