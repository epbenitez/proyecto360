package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesCategoria;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public interface SolicitudesCategoriaDao extends DaoBase<SolicitudesCategoria,Long> {
    
    public List<SolicitudesCategoria> findBySolicitudTipoInmueble(Long inmuebleId, Long tipoServicioId, Long areaId);
}
