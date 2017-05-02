package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.TorreDao;
import com.allinone.persistence.model.Torre;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public class TorreJpaDao extends JpaDaoBase<Torre,Long> implements TorreDao {

    public TorreJpaDao(){
        super(Torre.class);
    }
    
    @Override
    public List<Torre> torrePorCondominio(Long condominioId) {

        String jpql = "SELECT DISTINCT d.torre FROM Departamento d WHERE  d.condominio.id = ?1 ";
        List<Torre> torreList = executeQuery(jpql, condominioId);

            return torreList==null || torreList.isEmpty()?null: torreList;
    }
    
    @Override
    public List<Torre> findByName(String nombre) {
        String jpql = "SELECT t FROM Torre t WHERE  t.nombre like ?1 ";
        List<Torre> torreList = executeQuery(jpql, nombre);

        return torreList == null || torreList.isEmpty() ? null : torreList;
    }
    
    @Override
    public Torre findUniqueByName(String nombre) {
        List<Torre> torreList = findByName(nombre);

        return torreList == null || torreList.isEmpty() ? null : torreList.get(0);
    }
    
    @Override
    public Torre findByClave(String clave) {
        String jpql = "SELECT t FROM Torre t WHERE  t.clave like ?1 ";
        List<Torre> torreList = executeQuery(jpql, clave);

        return torreList == null || torreList.isEmpty() ? null : torreList.get(0);
    }
}
