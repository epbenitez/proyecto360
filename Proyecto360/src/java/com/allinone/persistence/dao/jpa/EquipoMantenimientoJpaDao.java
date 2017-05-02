package com.allinone.persistence.dao.jpa;

import com.allinone.domain.EquipoReporteEjecutivo;
import com.allinone.persistence.dao.EquipoMantenimientoDao;
import com.allinone.persistence.model.Equipo;
import com.allinone.persistence.model.EquipoMantenimiento;
import com.allinone.persistence.model.EquipoMantenimientoFrecuencia;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class EquipoMantenimientoJpaDao extends JpaDaoBase<EquipoMantenimiento, Long>
        implements EquipoMantenimientoDao {

    public EquipoMantenimientoJpaDao() {
        super(EquipoMantenimiento.class);
    }

    @Override
    public List<EquipoReporteEjecutivo> reporteEjecutivo(String anio, Long condominioId) {
        if(anio==null){
            return null;
        }
        List<EquipoReporteEjecutivo> reporte = new ArrayList<EquipoReporteEjecutivo>();
        String dinicio = anio + "-01-01";
        String dfinal = anio + "-12-31";
        String sql = "SELECT  \n"
                + "         e.id,\n"
                + "         e.nombre, \n"
                + "         f.nombre, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 1 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Jan, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 2 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Feb, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 3 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Mar, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 4 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Apr, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 5 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS May, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 6 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Jun, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 7 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Jul,  \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 8 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Aug, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)= 9 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Sep, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)=10 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Oct, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)=11 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS Nov, \n"
                + "         max(CASE WHEN Month( em.fechaprogramada)=12 THEN  concat(DATE_FORMAT(em.fechaprogramada,'%d/%m/%Y'),'-', cme.nombre) END)  AS `Dec`, \n"
                + "         Count(*) AS Conteo\n"
                + "  FROM ent_equipo_mantenimiento em\n"
                + "  inner join cat_equipo_mantenimiento_estatus cme on cme.id = em.estatusmantenimiento_id\n"
                + "  inner join ent_equipo e on e.id = em.equipo_id\n"
                + "  inner join cat_equipo_mantenimiento_frecuencia f on f.id = e.frecuencia_id\n"
                + "  where em.fechaprogramada between '"+dinicio+"' and '"+dfinal+"' and e.condominio_id = "+condominioId+" \n"
                + "  GROUP BY em.equipo_id; ";

        List<Object[]> lista = executeNativeQuery(sql);
        if(lista==null || lista.isEmpty()){
            return null;
        }else{
            for(Object[] o : lista){
                EquipoReporteEjecutivo r = new EquipoReporteEjecutivo();
                Equipo equipo = new Equipo();
                equipo.setId(new Long(o[0]==null?null:o[0].toString()));
                equipo.setNombre(o[1]==null?null:o[1].toString());
                r.setEquipo(equipo);
                
                EquipoMantenimientoFrecuencia frecuencia = new EquipoMantenimientoFrecuencia();
                frecuencia.setNombre(o[2]==null?null:o[2].toString());
                r.setFrecuencia(frecuencia);
                
                r.setEnero(o[3]==null?null:o[3].toString());
                r.setFebrero(o[4]==null?null:o[4].toString());
                r.setMarzo(o[5]==null?null:o[5].toString());
                r.setAbril(o[6]==null?null:o[6].toString());
                r.setMayo(o[7]==null?null:o[7].toString());
                r.setJunio(o[8]==null?null:o[8].toString());
                r.setJulio(o[9]==null?null:o[9].toString());
                r.setAgosto(o[10]==null?null:o[10].toString());
                r.setSeptiembre(o[11]==null?null:o[11].toString());
                r.setOctubre(o[12]==null?null:o[12].toString());
                r.setNoviembre(o[13]==null?null:o[13].toString());
                r.setDiciembre(o[14]==null?null:o[14].toString());
                r.setTotal(new Integer(o[15]==null?"0":o[15].toString()));
                
                reporte.add(r);
                
            }
        }

        return reporte.isEmpty()?null:reporte;
    }
}
