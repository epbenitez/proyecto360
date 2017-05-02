package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.ConfiguracionEnvioCorreosDao;
import com.allinone.persistence.model.ConfiguracionEnvioCorreos;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class ConfiguracionEnvioCorreosJpaDao extends JpaDaoBase<ConfiguracionEnvioCorreos,Long> 
implements ConfiguracionEnvioCorreosDao{
    
    public ConfiguracionEnvioCorreosJpaDao(){
        super(ConfiguracionEnvioCorreos.class);
    }
    
    public List<ConfiguracionEnvioCorreos> getConfiguraciones(Long condominioId, Long torreId){
        List<ConfiguracionEnvioCorreos> lista = null;
        
        String jpql = "select c from ConfiguracionEnvioCorreos c "
                + "where c.condominio.id = ?1 and c.torre.id = ?2 " ;

         lista = executeQuery(jpql, condominioId, torreId);

        
        return lista==null || lista.isEmpty()?null:lista;
    }
}
