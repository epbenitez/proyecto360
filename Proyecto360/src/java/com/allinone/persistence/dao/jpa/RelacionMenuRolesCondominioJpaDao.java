package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.RelacionMenuRolesCondominioDao;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.RelacionMenuRolesCondominio;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class RelacionMenuRolesCondominioJpaDao extends JpaDaoBase<RelacionMenuRolesCondominio,Long> implements RelacionMenuRolesCondominioDao{
    
    public RelacionMenuRolesCondominioJpaDao(){
        super(RelacionMenuRolesCondominio.class);
    }
    
    @Override
    public List<RelacionMenuRolesCondominio> getMenuRolPorCondominio(Long rolId, Long condominioId){
        String jpql = "SELECT r FROM RelacionMenuRolesCondominio r WHERE r.menuRol.rol.id = ?1 and r.condominio.id = ?2 ";
        List<RelacionMenuRolesCondominio> lst = executeQuery(jpql, rolId, condominioId);
        return lst==null || lst.isEmpty()?null: lst;
    }
    
    @Override
    public List<RelacionMenuRolesCondominio> getMenuRolPorCondominio(RelacionMenuRoles menuRol, Condominio c){
        String jpql = "SELECT r FROM RelacionMenuRolesCondominio r WHERE r.menuRol.id = ?1 and r.condominio.id = ?2 ";
        List<RelacionMenuRolesCondominio> lst = executeQuery(jpql, menuRol.getId(), c.getId());
        return lst==null || lst.isEmpty()?null: lst;
    }
}
