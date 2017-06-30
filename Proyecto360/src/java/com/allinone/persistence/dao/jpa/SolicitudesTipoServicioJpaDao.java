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

    /**
     * 
     * @param inmuebleId
     * @param restringirPermisosUsuarioId ID de usuario al cual se deberá restringir de acuerdo a la tabla de permisos el listado
     * @param atender true: Atender Solicitudes, false: Ingresar Solicitudes
     * @return 
     */
    @Override
    public List<SolicitudesTipoServicio> findBySolicitudTipoInmueble(Long inmuebleId, Long restringirPermisosUsuarioId, Boolean atender) {
        String sql = "select distinct(ts.id),ts.nombre, ts.clave from rmm_solicitudes_tipo_area a\n"
                + "inner join cat_solicitudes_tipo_servicio ts on ts.id = a.tiposervicio_id\n"
                + "inner join ent_condominio c on c.tipoinmueble_id = a.tipoinmueblesolicitud_id \n"
                + "where  c.id  =  " +inmuebleId;
        if(restringirPermisosUsuarioId!=null){
            sql+=" and ts.id in ( select tiposervicio_id from rmm_solicitudes_permisos where usuario_id = "+restringirPermisosUsuarioId
                    +" and permiso like '"+(atender?"a":"i")+"'"
                    + ") ";
        }
        
        System.out.println("findBySolicitudTipoInmueble: " +sql);
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
