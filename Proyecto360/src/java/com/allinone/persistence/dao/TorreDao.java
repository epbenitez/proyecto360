package com.allinone.persistence.dao;

import com.allinone.persistence.model.Torre;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface TorreDao extends DaoBase<Torre,Long>{

    public List<Torre> torrePorCondominio(Long condominioId);
    public List<Torre> findByName(String nombre);
    public Torre findUniqueByName(String nombre);
    public Torre findByClave(String clave);
}
