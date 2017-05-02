package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionDao;
import com.allinone.persistence.model.Notificacion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionJpaDao extends JpaDaoBase<Notificacion,Long> implements NotificacionDao {
    
    public NotificacionJpaDao(){
        super(Notificacion.class);
    }
    
    public List<Notificacion> notificacionesPorCondominioAsunto(Long condominioId, String mensaje) {
        List<Notificacion> notificaciones = new ArrayList<Notificacion>();
        String jpql = "SELECT g from Notificacion g WHERE 1=1 ";
        jpql += (condominioId==null?"":" and g.condominio.id = ?1 ");
        mensaje = (mensaje==null || mensaje.equals("")?"%":"%"+mensaje+"%");
        jpql += " and (g.asunto like ?2 or g.mensaje like ?2 ) ";
        
        notificaciones = executeQuery(jpql, condominioId, mensaje);
        return notificaciones == null || notificaciones.isEmpty() ? null : notificaciones;
    }
}
