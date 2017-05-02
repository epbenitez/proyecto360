package com.allinone.persistence.dao;

import com.allinone.persistence.model.NotificacionLog;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface NotificacionLogDao extends DaoBase<NotificacionLog,Long>{
    
    public List<NotificacionLog> getLog(String identificador);
}
