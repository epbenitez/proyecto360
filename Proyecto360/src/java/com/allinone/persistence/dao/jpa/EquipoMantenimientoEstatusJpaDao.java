package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoMantenimientoEstatusDao;
import com.allinone.persistence.model.EquipoMantenimientoEstatus;

/**
 *
 * @author patriciabenitez
 */
public class EquipoMantenimientoEstatusJpaDao extends JpaDaoBase<EquipoMantenimientoEstatus,Long> 
        implements EquipoMantenimientoEstatusDao {
    
    public EquipoMantenimientoEstatusJpaDao(){
        super(EquipoMantenimientoEstatus.class);
    }
}
