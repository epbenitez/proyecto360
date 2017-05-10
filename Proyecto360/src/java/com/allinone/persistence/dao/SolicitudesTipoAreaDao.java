package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesTipoArea;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public interface SolicitudesTipoAreaDao extends DaoBase<SolicitudesTipoArea,Long> {
    
    public List<SolicitudesTipoArea> findByInmueble(Long tipoInmuebleId);
    public List<SolicitudesTipoArea> findByServicio(Long tipoSolicitudId);
    public List<SolicitudesTipoArea> findByAreas(Long areaSolicitudId);
    public List<SolicitudesTipoArea> findByCategoria(Long categoriaSolicitudId);
    
}
