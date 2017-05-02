
package com.allinone.persistence.dao;

import com.allinone.persistence.model.AbonoDatosPago;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public interface AbonoDatosPagoDao extends DaoBase<AbonoDatosPago,Long>{

    public List<AbonoDatosPago> abonosPorCondominioTorreDepartamento(Long condominioId, Long torreId, Long departamentoId, Date fechaInicial, Date fechaFinal);
}
