package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.EquipoMantenimientoFrecuenciaDao;
import com.allinone.persistence.model.EquipoMantenimientoFrecuencia;

/**
 *
 * @author patriciabenitez
 */
public class EquipoMantenimientoFrecuenciaJpaDao extends JpaDaoBase<EquipoMantenimientoFrecuencia,Long> 
        implements EquipoMantenimientoFrecuenciaDao {
    
    public EquipoMantenimientoFrecuenciaJpaDao(){
        super(EquipoMantenimientoFrecuencia.class);
    }
}
