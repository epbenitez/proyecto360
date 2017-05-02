package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionLogDao;
import com.allinone.persistence.model.NotificacionLog;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionLogJpaDao extends JpaDaoBase<NotificacionLog, Long>
        implements NotificacionLogDao {
    
    public NotificacionLogJpaDao(){
        super(NotificacionLog.class);
    }

    public List<NotificacionLog> getLog(String identificador) {
        List<NotificacionLog> log = new ArrayList<NotificacionLog>();
        String jpql = "SELECT g from NotificacionLog g WHERE g.identificador like ?1 ";
        
        log = executeQuery(jpql, identificador);
        return log == null || log.isEmpty() ? null : log;
    }
}
