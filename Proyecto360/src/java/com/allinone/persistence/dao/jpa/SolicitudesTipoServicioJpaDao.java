package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesTipoServicioDao;
import com.allinone.persistence.model.SolicitudesTipoServicio;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesTipoServicioJpaDao extends JpaDaoBase<SolicitudesTipoServicio, Long> implements SolicitudesTipoServicioDao {

    public SolicitudesTipoServicioJpaDao() {
        super(SolicitudesTipoServicio.class);
    }

    @Override
    public List<SolicitudesTipoServicio> find(Long condominioId) {
        String jpql = "SELECT  s FROM SolicitudesTipoServicio s ";
//        if (condominioId <= 66) {
//            jpql += "WHERE s.id <= 10 ";
//        } else {
//            jpql += "WHERE s.id > 10 ";
//        }
        List<SolicitudesTipoServicio> list = executeQuery(jpql);
        return list == null || list.isEmpty() ? null : list;
    }

    @Override
    public List<SolicitudesTipoServicio> findBySolicitudTipoInmueble(Long inmuebleId) {
        String sql = "select distinct(ts.id),ts.nombre, ts.clave from rmm_solicitudes_tipo_area a\n"
                + "inner join cat_solicitudes_tipo_servicio ts on ts.id = a.tiposervicio_id\n"
                + "where a.tipoinmueblesolicitud_id =  " +inmuebleId;
        List<Object[]> list = executeNativeQuery(sql);
        
        if(list==null || list.isEmpty()){
            return null;
        }else{
            List<SolicitudesTipoServicio> listaTipoServicio = new ArrayList<SolicitudesTipoServicio>();
            for(Object[] o : list){
                SolicitudesTipoServicio ts = new SolicitudesTipoServicio();
                ts.setId(new Long(o[0].toString()));
                ts.setNombre(o[1]==null?"":o[1].toString());
                listaTipoServicio.add(ts);
            }
            return  listaTipoServicio.isEmpty() ? null : listaTipoServicio;
        }
    }
}
