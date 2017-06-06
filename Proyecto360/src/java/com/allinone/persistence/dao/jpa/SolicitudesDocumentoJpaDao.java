package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesDocumentoDao;
import com.allinone.persistence.model.SolicitudesDocumento;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class SolicitudesDocumentoJpaDao extends JpaDaoBase<SolicitudesDocumento, Long> 
implements SolicitudesDocumentoDao{
    
    public SolicitudesDocumentoJpaDao(){
        super(SolicitudesDocumento.class);
    }
    
    @Override
    public List<SolicitudesDocumento> getDocumentos(Long solicitudHistorialId) {
        String jpql = "SELECT  s FROM SolicitudesDocumento s WHERE s.solicitudHistorial.id = ?1";
        List<SolicitudesDocumento> list = executeQuery(jpql, solicitudHistorialId);
        return list == null || list.isEmpty() ? null : list;
    }
    
     @Override
    public List<SolicitudesDocumento> getDocumentos(Long solicitudId, Long estadoSolicitudId) {
        String jpql = "SELECT  s FROM SolicitudesDocumento s WHERE s.solicitudHistorial.solicitud.id = ?1 and s.solicitudHistorial.estadoSolicitud.id = ?2 ";
        List<SolicitudesDocumento> list = executeQuery(jpql, solicitudId,estadoSolicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
}
