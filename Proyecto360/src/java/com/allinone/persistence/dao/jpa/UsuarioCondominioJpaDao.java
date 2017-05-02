package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.UsuarioCondominioDao;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class UsuarioCondominioJpaDao extends JpaDaoBase<UsuarioCondominio, Long> implements UsuarioCondominioDao {

    public UsuarioCondominioJpaDao() {
        super(UsuarioCondominio.class);
    }

    @Override
    public UsuarioCondominio getUsuarioCondominio(Long condominioId) {
        String jpql = "SELECT  u FROM UsuarioCondominio u WHERE u.condominio.id = ?1 ";
        List<UsuarioCondominio> ucList = executeQuery(jpql, condominioId);
        return ucList == null || ucList.isEmpty() ? null : ucList.get(0);
    }
    
    @Override
    public List<UsuarioCondominio> getUsuarioCondominio(Usuario usuario) {
        String jpql = "SELECT  u FROM UsuarioCondominio u WHERE u.usuario.id = ?1 ";
        List<UsuarioCondominio> ucList = executeQuery(jpql, usuario.getId());
        return ucList == null || ucList.isEmpty() ? null : ucList;
    }
    
    @Override
    public void eliminaUsuarioCondominio(Usuario usuario) {
        String jpql = "SELECT  u FROM UsuarioCondominio u WHERE u.usuario.id = ?1 ";
        List<UsuarioCondominio> ucList = executeQuery(jpql, usuario.getId());
        if(ucList!=null && !ucList.isEmpty()){
            for(UsuarioCondominio uc: ucList){
                delete(uc);
            }
        }
    }

}
