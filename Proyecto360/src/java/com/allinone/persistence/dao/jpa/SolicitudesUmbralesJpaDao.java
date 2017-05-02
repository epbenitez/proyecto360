
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.model.SolicitudesUmbrales;
import com.allinone.persistence.dao.SolicitudesUmbralesDao;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesUmbralesJpaDao extends JpaDaoBase<SolicitudesUmbrales,Long> implements SolicitudesUmbralesDao {

    public SolicitudesUmbralesJpaDao(){
        super(SolicitudesUmbrales.class);
    }
    
    @Override
    public SolicitudesUmbrales findUmbral(Long condominioId, Long tipoSolicitudId) {

        String jpql = "SELECT s FROM SolicitudesUmbrales s WHERE s.condominio.id = ?1"
                + " and s.tipoSolicitud.id = ?2  ";
        List<SolicitudesUmbrales> lista = executeQuery(jpql, condominioId,tipoSolicitudId);

        return (lista==null || lista.isEmpty())?null:lista.get(0);
    }
}
