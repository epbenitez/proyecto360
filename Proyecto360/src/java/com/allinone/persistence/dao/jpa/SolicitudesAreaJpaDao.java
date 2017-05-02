package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesAreaDao;
import com.allinone.persistence.model.SolicitudesArea;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class SolicitudesAreaJpaDao extends JpaDaoBase<SolicitudesArea,Long> implements SolicitudesAreaDao {
    
    public SolicitudesAreaJpaDao(){
        super(SolicitudesArea.class);
    }
}
