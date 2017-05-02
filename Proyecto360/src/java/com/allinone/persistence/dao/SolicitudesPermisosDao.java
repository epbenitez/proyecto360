package com.allinone.persistence.dao;

import com.allinone.persistence.model.SolicitudesPermisos;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface SolicitudesPermisosDao extends DaoBase<SolicitudesPermisos, Long> {

    public List<SolicitudesPermisos> findByUsuario(Long idUsuario, Boolean atender);

    public List<SolicitudesPermisos> findBy(Long condominioId, Long tipoSolicitud, String tipoPermiso, Long idUsuario);
    
    public List<SolicitudesPermisos> findByCondominio(Long condominioId, Long idUsuario);
}
