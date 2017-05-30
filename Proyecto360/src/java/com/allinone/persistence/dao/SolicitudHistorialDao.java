package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudHistorialDao extends DaoBase<SolicitudHistorial, Long> {

    public List<SolicitudHistorial> getHistorial(Long solicitudId);

    public SolicitudHistorial getHistorial(Long solicitudId, Long estadoId);

    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, Long tipoId, Long estadoId);

    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, List<SolicitudesTipoServicio> tipoLst, Long estadoId);
}
