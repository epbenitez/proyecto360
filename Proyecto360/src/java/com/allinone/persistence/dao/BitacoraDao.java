package com.allinone.persistence.dao;

import com.allinone.persistence.model.Bitacora;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Patricia Benitez
 */
public interface BitacoraDao extends DaoBase<Bitacora, Long> {

    public List<Bitacora> findByParameters (Map objetos,Date fechaIni,Date fechaFin, String objectName, String adminUserId);


}
