package com.allinone.persistence.dao;

import com.allinone.persistence.model.RelacionMenuRoles;
import com.allinone.persistence.model.Usuario;
import java.util.List;

/**
 * 
 * @author Patricia Benitez
 */
public interface RelacionMenuRolesDao extends DaoBase<RelacionMenuRoles, Long> {
    public String findURLByRols(Usuario usuario);
    public List<RelacionMenuRoles> getMenuPorRol(Long rolId);
}
