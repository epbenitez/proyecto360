
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesPermisosDao;
import com.allinone.persistence.model.SolicitudesPermisos;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesPermisosJpaDao extends JpaDaoBase<SolicitudesPermisos,Long> implements SolicitudesPermisosDao {

    public SolicitudesPermisosJpaDao(){
        super(SolicitudesPermisos.class);
    }
    
    @Override
    public List<SolicitudesPermisos> findByUsuario(Long idUsuario, Boolean atender) {

        String jpql = "SELECT u FROM SolicitudesPermisos u WHERE u.usuario.id = ?1 "
                + (atender?" and u.permiso like 'a' ": " and u.permiso like 'i' ");
        List<SolicitudesPermisos> lista = executeQuery(jpql, idUsuario);

        return (lista==null || lista.isEmpty())?null:lista;
    }
    
    @Override
    public List<SolicitudesPermisos> findBy(Long condominioId, Long tipoServicio, String tipoPermiso, Long idUsuario) {

        String jpql = "SELECT u FROM SolicitudesPermisos u WHERE 1=1 "
                + (condominioId==null?"":" and u.condominio.id = " + condominioId)
                + (tipoServicio==null?"":" and u.tipoServicio.id = " + tipoServicio)
                + (tipoPermiso==null || tipoPermiso.equals("")?"":" and u.permiso like '" + tipoPermiso + "'")
                + (idUsuario==null || idUsuario.toString().equals("")?"":" and u.usuario.id = " + idUsuario + "");
        List<SolicitudesPermisos> lista = executeQuery(jpql);

        return (lista==null || lista.isEmpty())?null:lista;
    }
    
    @Override
    public List<SolicitudesPermisos> findByCondominio(Long condominioId, Long idUsuario) {

        String jpql = "SELECT u FROM SolicitudesPermisos u WHERE u.condominio.id = " + condominioId +" and u.usuario.id = " + idUsuario;
        List<SolicitudesPermisos> lista = executeQuery(jpql);

        return (lista==null || lista.isEmpty())?null:lista;
    }
}
