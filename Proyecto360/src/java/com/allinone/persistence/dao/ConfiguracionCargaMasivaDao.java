
package com.allinone.persistence.dao;

import com.allinone.persistence.model.ConfiguracionCargaMasiva;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface ConfiguracionCargaMasivaDao extends DaoBase<ConfiguracionCargaMasiva,Long>{

    public List<ConfiguracionCargaMasiva> getConfiguraciones(Long condominioId, Boolean cargo);
}
