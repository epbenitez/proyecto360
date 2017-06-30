package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesCategoriaDao;
import com.allinone.persistence.model.SolicitudesCategoria;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class SolicitudesCategoriaJpaDao extends JpaDaoBase<SolicitudesCategoria, Long> implements SolicitudesCategoriaDao {

    public SolicitudesCategoriaJpaDao() {
        super(SolicitudesCategoria.class);
    }

    @Override
    public List<SolicitudesCategoria> findBySolicitudTipoInmueble(Long inmuebleId, Long tipoServicioId, Long areaId) {
        String sql = "select distinct(c.id),c.nombre from rmm_solicitudes_tipo_area ta\n"
                + "inner join cat_solicitudes_categoria c on c.id = ta.categoriasolicitud_id\n"
                + "inner join ent_condominio co on co.tipoinmueble_id = ta.tipoinmueblesolicitud_id \n"
                + "where co.id =  " + inmuebleId + "\n"
                + " and ta.tiposervicio_id=" + tipoServicioId + "\n"
                + " and  ta.areasolicitud_id = " + areaId;
        List<Object[]> list = executeNativeQuery(sql);

        if (list == null || list.isEmpty()) {
            return null;
        } else {
            List<SolicitudesCategoria> listaSolicitudesCategoria = new ArrayList<SolicitudesCategoria>();
            for (Object[] o : list) {
                SolicitudesCategoria ts = new SolicitudesCategoria();
                ts.setId(new Long(o[0].toString()));
                ts.setNombre(o[1] == null ? "" : o[1].toString());
                listaSolicitudesCategoria.add(ts);
            }
            return listaSolicitudesCategoria.isEmpty() ? null : listaSolicitudesCategoria;
        }
    }
}
