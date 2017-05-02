package com.allinone.persistence.dao;

import com.allinone.persistence.model.NotificacionGrupo;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface NotificacionGrupoDao extends DaoBase<NotificacionGrupo,Long> {
    
    public List<NotificacionGrupo> gruposPorCondominioNombre(Long condominioId, String nombre);
}
