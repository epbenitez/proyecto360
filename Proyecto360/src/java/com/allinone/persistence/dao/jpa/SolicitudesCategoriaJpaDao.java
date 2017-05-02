package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesCategoriaDao;
import com.allinone.persistence.model.SolicitudesCategoria;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class SolicitudesCategoriaJpaDao extends JpaDaoBase<SolicitudesCategoria,Long> implements SolicitudesCategoriaDao {
    
    public SolicitudesCategoriaJpaDao(){
        super(SolicitudesCategoria.class);
    }
}
