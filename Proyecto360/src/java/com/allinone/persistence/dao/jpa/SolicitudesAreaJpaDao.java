package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudesAreaDao;
import com.allinone.persistence.model.SolicitudesArea;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class SolicitudesAreaJpaDao extends JpaDaoBase<SolicitudesArea, Long> implements SolicitudesAreaDao {

    public SolicitudesAreaJpaDao() {
        super(SolicitudesArea.class);
    }

    @Override
    public List<SolicitudesArea> findBySolicitudTipoInmueble(Long inmuebleId, Long tipoServicioId) {
        String sql = "select distinct(a.id),a.nombre from rmm_solicitudes_tipo_area ta\n"
                + "inner join cat_solicitudes_area a on a.id = ta.areasolicitud_id \n"
                + "inner join ent_condominio c on c.tipoinmueble_id = ta.tipoinmueblesolicitud_id \n"
                + "where c.id =  " + inmuebleId + "\n"
                + " and ta.tiposervicio_id=" +tipoServicioId;
        List<Object[]> list = executeNativeQuery(sql);

        if (list == null || list.isEmpty()) {
            return null;
        } else {
            List<SolicitudesArea> listaSolicitudesArea = new ArrayList<SolicitudesArea>();
            for (Object[] o : list) {
                SolicitudesArea ts = new SolicitudesArea();
                ts.setId(new Long(o[0].toString()));
                ts.setNombre(o[1] == null ? "" : o[1].toString());
                listaSolicitudesArea.add(ts);
            }
            return listaSolicitudesArea.isEmpty() ? null : listaSolicitudesArea;
        }
    }
}
