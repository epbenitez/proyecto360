package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoSubsistemaDao;
import com.allinone.persistence.model.EquipoSubsistema;

/**
 *
 * @author patriciabenitez
 */
public class EquipoSubsistemaJpaDao extends JpaDaoBase<EquipoSubsistema,Long> 
        implements EquipoSubsistemaDao {
    
    public EquipoSubsistemaJpaDao(){
        super(EquipoSubsistema.class);
    }
}
