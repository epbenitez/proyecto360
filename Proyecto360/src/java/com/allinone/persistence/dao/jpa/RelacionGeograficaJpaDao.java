package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.model.LocalidadColonia;
import com.allinone.persistence.model.MunicipioDelegacion;
import com.allinone.persistence.model.RelacionGeografica;
import com.allinone.persistence.dao.RelacionGeograficaDao;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Patricia Benitez
 */
public class RelacionGeograficaJpaDao  extends JpaDaoBase<RelacionGeografica, Long> implements RelacionGeograficaDao {

    public RelacionGeograficaJpaDao() {
        super(RelacionGeografica.class);
    }

    @Override
    public List<MunicipioDelegacion> getMunicipiosByEstado(Long estadoId) {
        List<MunicipioDelegacion> municipios = new LinkedList<MunicipioDelegacion>();
        String jpql = "SELECT distinct rel.municipio FROM RelacionGeografica rel WHERE rel.estado.id = ?1";
        Query query = getEntityManager().createQuery(jpql.toString());
        query.setParameter(1, estadoId);
        municipios = (List<MunicipioDelegacion>) query.getResultList();

        if (!municipios.isEmpty()) {
            return municipios;
        } else {
            return null;
        }
    }

    @Override
    public List<LocalidadColonia> getColoniasByMunicipio(Long municipioId) {
        List<LocalidadColonia> colonias = new LinkedList<LocalidadColonia>();
        String jpql = "SELECT distinct rel.colonia FROM RelacionGeografica rel WHERE rel.municipio.id = ?1";
        Query query = getEntityManager().createQuery(jpql.toString());
        query.setParameter(1, municipioId);
        colonias = (List<LocalidadColonia>) query.getResultList();
//        String jpql = "SELECT rel FROM RelacionGeografica rel WHERE rel.municipio.id = ?1";
//        List<RelacionGeografica> rel = executeQuery(jpql, municipioId);
//        for(RelacionGeografica rg: rel){
//            colonias.add(rg.getColonia());
//        }

        if (!colonias.isEmpty()) {
            return colonias;
        } else {
            return null;
        }
    }

    @Override
    public RelacionGeografica getRelacionGeografica(Long estadoId, Long municipioId, Long coloniaId) {
        String jpql = "SELECT rel FROM RelacionGeografica rel WHERE 1=1 "
                + (estadoId==null?"":(" and rel.estado.id =  " + estadoId))
                + (municipioId==null?"":(" and rel.municipio.id = " + municipioId))
                + (coloniaId==null?"":(" and rel.colonia.id = " + coloniaId));
        List<RelacionGeografica> rel = executeQuery(jpql);

        return (rel==null || rel.isEmpty())?null:rel.get(0);
    }


}
