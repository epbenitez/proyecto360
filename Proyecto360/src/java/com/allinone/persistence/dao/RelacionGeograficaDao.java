package com.allinone.persistence.dao;

import com.allinone.persistence.model.LocalidadColonia;
import com.allinone.persistence.model.MunicipioDelegacion;
import com.allinone.persistence.model.RelacionGeografica;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public interface RelacionGeograficaDao extends DaoBase<RelacionGeografica, Long>  {

    public List<MunicipioDelegacion> getMunicipiosByEstado(Long estadoId);
    public List<LocalidadColonia> getColoniasByMunicipio(Long municipioId);
    public RelacionGeografica getRelacionGeografica(Long estadoId, Long municipioId, Long coloniaId);
    
}
