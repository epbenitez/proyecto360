package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesDocumento;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface SolicitudesDocumentoDao extends DaoBase<SolicitudesDocumento,Long> {
    
    public List<SolicitudesDocumento> getDocumentos(Long solicitudHistorialId);
    
    public List<SolicitudesDocumento> getDocumentos(Long solicitudId, Long estadoSolicitudId);
    
}
