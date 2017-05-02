package com.allinone.persistence.dao;

import com.allinone.persistence.model.NotificacionGrupoDepartamento;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface NotificacionGrupoDepartamentoDao extends DaoBase<NotificacionGrupoDepartamento, Long>{
    
    
    public List<NotificacionGrupoDepartamento> departamentosPorGrupo(Long grupoId);
}