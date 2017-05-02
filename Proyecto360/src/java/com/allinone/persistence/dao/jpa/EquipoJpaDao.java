package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoDao;
import com.allinone.persistence.model.Equipo;

/**
 *
 * @author patriciabenitez
 */
public class EquipoJpaDao extends JpaDaoBase<Equipo,Long> implements EquipoDao {
    
    public EquipoJpaDao(){
        super(Equipo.class);
    }
}
