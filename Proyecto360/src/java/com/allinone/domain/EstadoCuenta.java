package com.allinone.domain;

import com.allinone.persistence.model.Departamento;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author Patricia Benitez
 */
public class EstadoCuenta implements Comparable<EstadoCuenta>{

    private Date fechaPeriodo;
    private BigDecimal cargos;
    private BigDecimal abonos;
    private BigDecimal saldo;
    private Departamento departamento;

    public Date getFechaPeriodo() {
        return fechaPeriodo;
    }

    public void setFechaPeriodo(Date fechaPeriodo) {
        this.fechaPeriodo = fechaPeriodo;
    }

    public BigDecimal getCargos() {
        return cargos;
    }

    public void setCargos(BigDecimal cargos) {
        this.cargos = cargos;
    }

    public BigDecimal getAbonos() {
        return abonos;
    }

    public void setAbonos(BigDecimal abonos) {
        this.abonos = abonos;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "EstadoCuenta{" + "fechaPeriodo=" + fechaPeriodo + ", cargos=" + cargos + ", abonos=" + abonos + ", saldo=" + saldo + ", departamento=" + departamento + '}';
    }
    
    @Override
    public int compareTo(EstadoCuenta o) {
        return getFechaPeriodo().compareTo(o.getFechaPeriodo());
    }

}
