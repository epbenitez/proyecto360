package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.NotificacionGrupoDao;
import com.allinone.persistence.model.NotificacionGrupo;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class NotificacionGrupoJpaDao extends JpaDaoBase<NotificacionGrupo, Long> implements NotificacionGrupoDao {

    public NotificacionGrupoJpaDao() {
        super(NotificacionGrupo.class);
    }

    public List<NotificacionGrupo> gruposPorCondominioNombre(Long condominioId, String nombre) {
        List<NotificacionGrupo> grupos = new ArrayList<NotificacionGrupo>();
        String jpql = "SELECT g from NotificacionGrupo g WHERE g.estatus=1 ";
        jpql += (condominioId==null?"":" and g.condominio.id = ?1 ");
        nombre = (nombre==null || nombre.equals("")?"%":"%"+nombre+"%");
        jpql += " and g.nombre like ?2 ";
        
        grupos = executeQuery(jpql, condominioId, nombre);
        return grupos == null || grupos.isEmpty() ? null : grupos;
    }
}
