package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoTipoDao;
import com.allinone.persistence.model.EquipoTipo;

/**
 *
 * @author patriciabenitez
 */
public class EquipoTipoJpaDao extends JpaDaoBase<EquipoTipo,Long> 
        implements EquipoTipoDao {
    
    public EquipoTipoJpaDao(){
        super(EquipoTipo.class);
    }
}
