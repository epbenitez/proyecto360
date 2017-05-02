
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ConfiguracionCargaMasivaDao;
import com.allinone.persistence.model.ConfiguracionCargaMasiva;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class ConfiguracionCargaMasivaJpaDao extends JpaDaoBase<ConfiguracionCargaMasiva,Long> implements ConfiguracionCargaMasivaDao{

    public ConfiguracionCargaMasivaJpaDao(){
        super(ConfiguracionCargaMasiva.class);
    }
    
    public List<ConfiguracionCargaMasiva> getConfiguraciones(Long condominioId, Boolean cargo){
        List<ConfiguracionCargaMasiva> lista = null;
        
        String jpql = "select   c  from ConfiguracionCargaMasiva c "
                + "where c.condominio.id = ?1 and c.cargo = ?2 " ;

         lista = executeQuery(jpql, condominioId, cargo);

        
        return lista==null || lista.isEmpty()?null:lista;
    }
}
