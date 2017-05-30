package com.allinone.persistence.dao;

import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudDao extends DaoBase<Solicitud, Long> {

    public List<Solicitud> getSolicitudes(Long condominioId, Long torreId, Long departamentoId, Long tipoId, Long estadoId);

    public List<Solicitud> getSolicitudes(Long condominioId, Long torreId, Long departamentoId, List<SolicitudesTipoServicio> tipoLst, Long estadoId);

    public List<Solicitud> getSolicitudes(Long departamentoId, Long tipoId, Long estadoId);
    
    public Integer getConsecutivo(Long condominioId, Long tipoSolicitudId);
}
