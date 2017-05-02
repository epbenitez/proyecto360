package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.CuestionarioPreguntaRespuestaDao;
import com.allinone.persistence.model.CuestionarioPreguntaRespuesta;
import com.allinone.persistence.model.CuestionarioPreguntaTipo;
import com.allinone.persistence.model.CuestionarioPreguntas;
import com.allinone.persistence.model.CuestionarioRespuestas;
import com.allinone.persistence.model.CuestionarioSeccion;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tania G. Sánchez
 */
public class CuestionarioPreguntaRespuestaJpaDao extends JpaDaoBase<CuestionarioPreguntaRespuesta, Long> implements CuestionarioPreguntaRespuestaDao {

    public CuestionarioPreguntaRespuestaJpaDao() {
        super(CuestionarioPreguntaRespuesta.class);
    }

    @Override
    public List<CuestionarioPreguntaRespuesta> findByCuestionario(Long cuestionarioId) {
        String sql = "select cpr.id, cs.id, cs.nombre, cs.orden, cp.id, cp.nombre, cp.cuestionariopreguntatipo_id, cp.orden, cr.id, cr.nombre, cpr.orden "
                + "from ent_cuestionario_pregunta_respuesta cpr "
                + "left join ent_cuestionario_pregunta cp on cp.id = cpr.pregunta_id "
                + "left join ent_cuestionario_respuestas cr on cr.id = cpr.respuesta_id "
                + "left join ent_cuestionario_seccion cs on cs.id = cpr.seccion_id "
                + "left join ent_cuestionario c on c.id = cs.cuestionario_id "
                + "where c.activo = 1 and cp.activo = 1 and cs.activo = 1 and  c.id = " + cuestionarioId
                + " order by cs.orden, cp.orden, cpr.orden";
        List<Object[]> lista = executeNativeQuery(sql);
        List<CuestionarioPreguntaRespuesta> lcpr = new ArrayList<CuestionarioPreguntaRespuesta>();
        for (Object[] res : lista) {
            CuestionarioPreguntaRespuesta cpr = new CuestionarioPreguntaRespuesta();
            cpr.setId((Long) res[0]);
            //Sección
            CuestionarioSeccion cs = new CuestionarioSeccion();
            cs.setId((Long) res[1]);
            cs.setNombre((String) res[2]);
            cs.setOrden(new Integer(res[3].toString()));
            cpr.setSeccion(cs);
            //Preguntas
            CuestionarioPreguntas cp = new CuestionarioPreguntas();
            cp.setId((Long) res[4]);
            cp.setNombre((String) res[5]);
            //Pregunta Tipo
            CuestionarioPreguntaTipo pt = new CuestionarioPreguntaTipo();
            pt.setId((Long) res[6]);
            cp.setCuestionarioPreguntaTipo(pt);
            cp.setOrden(new Integer(res[7].toString()));
            cpr.setPregunta(cp);
            //Respuestas
            CuestionarioRespuestas cr = new CuestionarioRespuestas();
            cr.setId((Long) res[8]);
            cr.setNombre((String) res[9]);
            cpr.setRespuesta(cr);
            cpr.setOrden(new Integer(res[10].toString()));
            lcpr.add(cpr);
        }
        return lcpr;
    }

    @Override
    public Integer totalPreguntas(Long cuestionarioId) {
        String sql = "select distinct pregunta_id from ent_cuestionario_pregunta_respuesta cpr "
                    + "left join ent_cuestionario_pregunta cp on cp.id = cpr.pregunta_id "
                    + "left join ent_cuestionario_seccion cs on cs.id = cpr.seccion_id "
                    + "where cs.activo=1 and cp.activo=1 and cuestionario_id=" + cuestionarioId;
        List<Object[]> lista =  executeNativeQuery(sql);
       return lista.size();
    }
}