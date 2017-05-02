package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionGrupoDepartamentoDao;
import com.allinone.persistence.model.NotificacionGrupoDepartamento;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionGrupoDepartamentoJpaDao extends JpaDaoBase<NotificacionGrupoDepartamento, Long>
        implements NotificacionGrupoDepartamentoDao {

    public NotificacionGrupoDepartamentoJpaDao() {
        super(NotificacionGrupoDepartamento.class);
    }
    
    @Override
    public List<NotificacionGrupoDepartamento> departamentosPorGrupo(Long grupoId) {
        List<NotificacionGrupoDepartamento> grupos = new ArrayList<NotificacionGrupoDepartamento>();
        String jpql = "SELECT g from NotificacionGrupoDepartamento g WHERE g.grupo.id=?1 ";
        grupos = executeQuery(jpql, grupoId);
        return grupos == null || grupos.isEmpty() ? null : grupos;
    }
    
}
