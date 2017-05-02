package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionEstatusDao;
import com.allinone.persistence.model.NotificacionEstatus;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionEstatusJpaDao extends JpaDaoBase<NotificacionEstatus,Long> 
        implements NotificacionEstatusDao {
    
    public NotificacionEstatusJpaDao(){
        super(NotificacionEstatus.class);
    }
}
