package com.allinone.domain;

import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Departamento;
import java.util.Date;

/**
 *
 * @author Patricia Benitez 
 */
public class ConfiguracionExcel {

    private Concepto concepto;
    private Departamento departamento;
    private Integer columnaFecha;
    private Integer columnaMonto;
    private Date fecha;
    private Double monto;

    public Concepto getConcepto() {
        return concepto;
    }

    public void setConcepto(Concepto concepto) {
        this.concepto = concepto;
    }

    public Integer getColumnaFecha() {
        return columnaFecha;
    }

    public void setColumnaFecha(Integer columnaFecha) {
        this.columnaFecha = columnaFecha;
    }

    public Integer getColumnaMonto() {
        return columnaMonto;
    }

    public void setColumnaMonto(Integer columnaMonto) {
        this.columnaMonto = columnaMonto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }
    

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    @Override
    public String toString() {
        return "ConfiguracionExcel{" + "concepto=" + concepto + ", columnaFecha=" + columnaFecha + ", columnaMonto=" + columnaMonto + ", fecha=" + fecha + ", monto=" + monto + ", departamento=" + departamento + '}';
    }
    
    
    
    
}
