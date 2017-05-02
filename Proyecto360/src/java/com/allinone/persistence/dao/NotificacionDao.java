package com.allinone.persistence.dao;

import com.allinone.persistence.model.Notificacion;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface NotificacionDao extends DaoBase<Notificacion, Long> {
    public List<Notificacion> notificacionesPorCondominioAsunto(Long condominioId, String mensaje);
}
