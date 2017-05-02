package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoMantenimientoTipoDao;
import com.allinone.persistence.model.EquipoMantenimientoTipo;

/**
 *
 * @author patriciabenitez
 */
public class EquipoMantenimientoTipoJpaDao extends JpaDaoBase<EquipoMantenimientoTipo,Long> 
        implements EquipoMantenimientoTipoDao {
    
    public EquipoMantenimientoTipoJpaDao(){
        super(EquipoMantenimientoTipo.class);
    }
}
