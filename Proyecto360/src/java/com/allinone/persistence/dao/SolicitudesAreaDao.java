package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesArea;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public interface SolicitudesAreaDao extends DaoBase<SolicitudesArea,Long> {
    public List<SolicitudesArea> findBySolicitudTipoInmueble(Long inmuebleId, Long tipoServicioId);
}
