package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesTipoInmuebleDao;
import com.allinone.persistence.model.SolicitudesTipoInmueble;

/**
 *
 * @author patriciabenitez
 */
public class SolicitudesTipoInmuebleJpaDao extends JpaDaoBase<SolicitudesTipoInmueble,Long> implements SolicitudesTipoInmuebleDao{
    
    public SolicitudesTipoInmuebleJpaDao(){
        super(SolicitudesTipoInmueble.class);
    }
}
