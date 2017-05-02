package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionesGruposDao;
import com.allinone.persistence.model.NotificacionesGrupos;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionesGruposJpaDao extends JpaDaoBase<NotificacionesGrupos,Long> 
        implements NotificacionesGruposDao {
    
    public NotificacionesGruposJpaDao(){
        super(NotificacionesGrupos.class);
    }
    
    public List<NotificacionesGrupos> getGrupos(Long notificacionId) {
        List<NotificacionesGrupos> gpos = new ArrayList<NotificacionesGrupos>();
        String jpql = "SELECT g from NotificacionesGrupos g WHERE g.notificacion.id = ?1 ";
        
        gpos = executeQuery(jpql, notificacionId);
        return gpos == null || gpos.isEmpty() ? null : gpos;
    }
}
