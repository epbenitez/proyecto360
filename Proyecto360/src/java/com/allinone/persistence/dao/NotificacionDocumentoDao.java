package com.allinone.persistence.dao;

import com.allinone.persistence.model.NotificacionDocumento;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface NotificacionDocumentoDao extends DaoBase<NotificacionDocumento,Long> {
    
    public List<NotificacionDocumento> getDocumentos(Long notificacionId);
}
