package com.allinone.persistence.dao;

import com.allinone.persistence.model.Abono;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface AbonoDao extends DaoBase<Abono,Long>{

    public void elimina (String identificador);
    
    public List<Abono> abonosPeriodoDetalle(Date fechaInicial, Date fechaFinal, Long departamentoId);
    
    public List<Abono> abonosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId, Integer tipoAbono);
    public List<Abono> abonosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId);
    public List<String> getIdentificadoresCarga(Long condominioId);
    public List<Abono> abonos(String identificadorcarga, Long condominioId);
    public List<Abono> abonos( Long datosPagoId);
}
