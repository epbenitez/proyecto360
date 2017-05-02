package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.VWSolicitudesUmbralesResponsablesDao;
import com.allinone.persistence.model.SolicitudesUmbrales;
import com.allinone.persistence.model.VWSolicitudesUmbralesResponsables;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class VWSolicitudesUmbralesResponsablesJpaDao extends JpaDaoBase<VWSolicitudesUmbralesResponsables,Long> 
        implements VWSolicitudesUmbralesResponsablesDao{
    
    public VWSolicitudesUmbralesResponsablesJpaDao(){
        super(VWSolicitudesUmbralesResponsables.class);
    }
    
    @Override
    public List<VWSolicitudesUmbralesResponsables> findSolicitudes(String umbral) {

        String jpql = "SELECT s FROM VWSolicitudesUmbralesResponsables s WHERE s.umbral = ?1";
        List<VWSolicitudesUmbralesResponsables> lista = executeQuery(jpql, umbral);

        return (lista==null || lista.isEmpty())?null:lista;
    }
}
