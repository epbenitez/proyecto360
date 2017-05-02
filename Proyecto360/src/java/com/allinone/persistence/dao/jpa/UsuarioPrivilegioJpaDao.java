/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.UsuarioPrivilegioDao;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioPrivilegio;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class UsuarioPrivilegioJpaDao extends JpaDaoBase<UsuarioPrivilegio, Long> implements UsuarioPrivilegioDao {

    public UsuarioPrivilegioJpaDao() {
        super(UsuarioPrivilegio.class);
    }

    @Override
    public List<UsuarioPrivilegio> getPrivilegiosByUser(Usuario u) {
        String jpql = "SELECT u FROM UsuarioPrivilegio u WHERE u.usuario.id = ?1";
        List<UsuarioPrivilegio> privilegiosLst = executeQuery(jpql, u.getId());

        return privilegiosLst;
    }

    @Override
    public void borraPrivilegios(Usuario u) {
//        String sql= "delete from rmm_rol_usuario p where p.usuario_id = " + u.getId();
//        executeNativeQuery(sql);
        String jpql = "SELECT u FROM UsuarioPrivilegio u WHERE u.usuario.id = ?1";
        List<UsuarioPrivilegio> privilegiosLst = executeQuery(jpql, u.getId());
        if(privilegiosLst!=null && !privilegiosLst.isEmpty()){
            for(UsuarioPrivilegio up: privilegiosLst){
                delete(up);
            }
        }

    }
    
    @Override
    public void borraPrivilegios(Usuario u, Rol r) {
//        String sql= "delete from rmm_rol_usuario p where p.usuario_id = " + u.getId() + " and p.rol_id = " + r.getId() ;
//        executeNativeQuery(sql);
        String jpql = "SELECT u FROM UsuarioPrivilegio u WHERE u.usuario.id = ?1 and u.privilegio.id = ?2";
        List<UsuarioPrivilegio> privilegiosLst = executeQuery(jpql, u.getId(), r.getId());
        
        UsuarioPrivilegio up = privilegiosLst==null || privilegiosLst.isEmpty()?null:privilegiosLst.get(0);
        if(up!=null){
            delete(up);
        }

    }
}
