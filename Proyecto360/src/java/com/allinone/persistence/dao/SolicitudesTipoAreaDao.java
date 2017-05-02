package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesTipoArea;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public interface SolicitudesTipoAreaDao extends DaoBase<SolicitudesTipoArea,Long> {
    
    public List<SolicitudesTipoArea> find(Long tipoSolicitudId);
    
}
