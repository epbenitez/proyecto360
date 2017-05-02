package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.DepartamentoDao;
import com.allinone.persistence.model.Departamento;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class DepartamentoJpaDao extends JpaDaoBase<Departamento, Long> implements DepartamentoDao {

    public DepartamentoJpaDao() {
        super(Departamento.class);
    }

    @Override
    public Departamento findBy(String nombre, String claveTorre, Long condominioId) {

        String[] arr = nombre.split("\\.");
        String jpql = "SELECT d FROM Departamento d WHERE d.nombre = ?1 and d.torre.clave = ?2 and d.condominio.id = ?3 ";
        List<Departamento> deptoLst = executeQuery(jpql, arr[0], claveTorre, condominioId);

        if (deptoLst != null && deptoLst.size() > 0) {
            return deptoLst.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<Departamento> findBy(Long torreId, Long condominioId) {

        String jpql = "SELECT  NEW com.allinone.persistence.model.Departamento(d.id, d.nombre, d.tipoDepartamento.nombre)  FROM Departamento d WHERE  d.torre.id = ?1 and d.condominio.id =  ?2";
        List<Departamento> deptoList = executeQuery(jpql, torreId, condominioId);
        return deptoList == null || deptoList.isEmpty() ? null : deptoList;
    }
    
    @Override
    public List<Departamento> findBy( Long condominioId) {

        String jpql = "SELECT  NEW com.allinone.persistence.model.Departamento(d.id, d.nombre, d.tipoDepartamento.nombre)  FROM Departamento d WHERE  d.condominio.id =  ?1";
        List<Departamento> deptoList = executeQuery(jpql,  condominioId);
        return deptoList == null || deptoList.isEmpty() ? null : deptoList;
    }
}
