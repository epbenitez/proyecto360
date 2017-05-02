package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionDocumentoDao;
import com.allinone.persistence.model.NotificacionDocumento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionDocumentoJpaDao extends JpaDaoBase<NotificacionDocumento,Long> 
implements NotificacionDocumentoDao{
    
    public NotificacionDocumentoJpaDao(){
        super(NotificacionDocumento.class);
    }
    
    public List<NotificacionDocumento> getDocumentos(Long notificacionId) {
        List<NotificacionDocumento> doctos = new ArrayList<NotificacionDocumento>();
        String jpql = "SELECT g from NotificacionDocumento g WHERE g.notificacion.id = ?1 ";
        
        doctos = executeQuery(jpql, notificacionId);
        return doctos == null || doctos.isEmpty() ? null : doctos;
    }
}
