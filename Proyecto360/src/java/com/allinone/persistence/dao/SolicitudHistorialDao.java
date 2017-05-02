
package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesTipo;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudHistorialDao  extends DaoBase<SolicitudHistorial,Long>{

    public List<SolicitudHistorial> getHistorial(Long solicitudId);
    
    public SolicitudHistorial getHistorial(Long solicitudId, Long estadoId);
    
    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, Long torreId, Long departamentoId, Long tipoId, Long estadoId);
    
    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, Long torreId, Long departamentoId, List<SolicitudesTipo> tipoLst, Long estadoId);
}
