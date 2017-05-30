
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
    public SolicitudesUmbrales findUmbral(Long condominioId, Long tipoServicioId) {

        String jpql = "SELECT s FROM SolicitudesUmbrales s WHERE s.condominio.id = ?1"
                + " and s.tipoServicio.id = ?2  ";
        List<SolicitudesUmbrales> lista = executeQuery(jpql, condominioId,tipoServicioId);

        return (lista==null || lista.isEmpty())?null:lista.get(0);
    }
}
