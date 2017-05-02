package com.allinone.persistence.dao;

import com.allinone.domain.EstadoCuenta;
import com.allinone.persistence.model.Cargo;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benitez 
 */
public interface CargoDao extends DaoBase<Cargo,Long> {

    public EstadoCuenta cargosPeriodo(EstadoCuenta ec, Date fechaInicial, Date fechaFinal, Long departamentoId);
    public EstadoCuenta abonosPeriodo(EstadoCuenta ec, Date fechaInicial, Date fechaFinal, Long departamentoId);
    public EstadoCuenta cargosAnteriores(EstadoCuenta ec, Date fechaInicial, Long departamentoId);
    public EstadoCuenta abonosAnteriores(EstadoCuenta ec,Date fechaInicial, Long departamentoId);
    public void elimina (String identificador);
    public List<Cargo> cargosPeriodoDetalle(Date fechaInicial, Date fechaFinal, Long departamentoId);
    public List<Cargo> cargosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId);
    public List<String> getIdentificadoresCarga(Long condominioId);
    public List<Cargo> cargos(String identificadorcarga, Long condominioId);
    
    public List<EstadoCuenta> saldos(Date fechaInicial, Date fechaFinal, Long condominioId);
    public List<EstadoCuenta> saldosDetalleDepartamento(Date fechaInicial, Date fechaFinal, Long condominioId);
}
