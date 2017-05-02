package com.allinone.persistence.dao;

import com.allinone.persistence.model.ConfiguracionEnvioCorreos;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface ConfiguracionEnvioCorreosDao extends DaoBase<ConfiguracionEnvioCorreos,Long> {
    
    public List<ConfiguracionEnvioCorreos> getConfiguraciones(Long condominioId, Long torreId);
}
