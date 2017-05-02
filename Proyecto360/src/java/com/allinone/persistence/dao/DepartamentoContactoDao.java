package com.allinone.persistence.dao;

import com.allinone.persistence.model.DepartamentoContacto;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface DepartamentoContactoDao extends DaoBase<DepartamentoContacto,Long> {
    
    public List<DepartamentoContacto> getContactos(Long grupoId);
}
