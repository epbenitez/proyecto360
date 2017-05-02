package com.allinone.persistence.dao;

import com.allinone.persistence.model.Rol;
import java.util.List;
/**
 *
 * @author Patricia Benitez
 */
public interface RolDao extends DaoBase<Rol, Long> {

    public Rol findById(String id);

}