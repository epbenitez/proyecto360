package com.allinone.persistence.dao;

import com.allinone.domain.EquipoReporteEjecutivo;
import com.allinone.persistence.model.EquipoMantenimiento;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public interface EquipoMantenimientoDao extends DaoBase<EquipoMantenimiento, Long> {

    public List<EquipoReporteEjecutivo> reporteEjecutivo(String anio, Long condominioId);
}
