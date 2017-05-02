package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoSistemaDao;
import com.allinone.persistence.model.EquipoSistema;

/**
 *
 * @author patriciabenitez
 */
public class EquipoSistemaJpaDao extends JpaDaoBase<EquipoSistema,Long> 
        implements EquipoSistemaDao {
    
    public EquipoSistemaJpaDao(){
        super(EquipoSistema.class);
    }
}
