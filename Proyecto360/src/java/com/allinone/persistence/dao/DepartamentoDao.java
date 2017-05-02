package com.allinone.persistence.dao;

import com.allinone.persistence.model.Departamento;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface DepartamentoDao extends DaoBase<Departamento,Long>{

    public Departamento findBy(String nombre, String claveTorre, Long condominioId);
    public List<Departamento> findBy(Long torreId, Long condominioId);
    
    public List<Departamento> findBy( Long condominioId);
}
