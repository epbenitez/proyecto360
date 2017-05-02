
package com.allinone.persistence.dao;

import com.allinone.persistence.model.Condominio;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface CondominioDao extends DaoBase<Condominio,Long> {

    public List<Condominio> findBy(Long torreId);
    public List<Condominio> condominiosPorUsuario(Long usuarioId);
    public List<Condominio> findByName(String nombre);
    public Condominio findUniqueByName(String nombre);
}
