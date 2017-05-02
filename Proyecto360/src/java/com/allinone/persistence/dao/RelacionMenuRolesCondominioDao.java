package com.allinone.persistence.dao;

import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.RelacionMenuRolesCondominio;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface RelacionMenuRolesCondominioDao extends DaoBase<RelacionMenuRolesCondominio,Long> {
    
    public List<RelacionMenuRolesCondominio> getMenuRolPorCondominio(Long rolId, Long condominioId);
    public List<RelacionMenuRolesCondominio> getMenuRolPorCondominio(RelacionMenuRoles menuRol, Condominio c);
}
