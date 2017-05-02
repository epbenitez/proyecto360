package com.allinone.persistence.dao;

import com.allinone.persistence.model.VWSolicitudesUmbralesResponsables;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public interface VWSolicitudesUmbralesResponsablesDao extends DaoBase<VWSolicitudesUmbralesResponsables,Long> {
    
    public List<VWSolicitudesUmbralesResponsables> findSolicitudes(String umbral);
    
}
