package com.allinone.persistence.dao;

import com.allinone.persistence.model.NotificacionesGrupos;
import java.util.List;
/**
 *
 * @author patriciabenitez
 */
public interface NotificacionesGruposDao extends DaoBase<NotificacionesGrupos,Long> {
    
    public List<NotificacionesGrupos> getGrupos(Long notificacionId);
}
