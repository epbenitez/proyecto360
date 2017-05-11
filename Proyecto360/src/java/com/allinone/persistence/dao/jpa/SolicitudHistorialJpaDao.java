package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.SolicitudHistorialDao;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesEstado;
import com.allinone.persistence.model.SolicitudesTipo;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.List;
import oracle.net.aso.s;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudHistorialJpaDao extends JpaDaoBase<SolicitudHistorial, Long> implements SolicitudHistorialDao {

    public SolicitudHistorialJpaDao() {
        super(SolicitudHistorial.class);
    }

    @Override
    public List<SolicitudHistorial> getHistorial(Long solicitudId) {
        String jpql = "SELECT  s FROM SolicitudHistorial s WHERE s.solicitud.id = ?1 ";
        List<SolicitudHistorial> list = executeQuery(jpql, solicitudId);
        return list == null || list.isEmpty() ? null : list;
    }
    
    @Override
    public SolicitudHistorial getHistorial(Long solicitudId, Long estadoId) {
        String jpql = "SELECT  s FROM SolicitudHistorial s WHERE s.solicitud.id = ?1 and s.estadoSolicitud.id = ?2 order by 1 asc";
        List<SolicitudHistorial> list = executeQuery(jpql, solicitudId, estadoId);
        return list == null || list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, Long tipoId, Long estadoId) {
        
        StringBuilder sql = new StringBuilder("select c.nombre, c.clave,'', '', st.nombre, st.clave, se.nombre, s.id, s.fechasolicitud, ");
        sql.append(" s.fechasolucion , s.consecutivo, s.solicitante, u.usuario,  h.comentario, u2.usuario, c.id, st.id, se.id ");
        sql.append(" from ent_solicitudes s ");
        sql.append(" inner join ent_condominio c on c.id = s.condominio_id ");
        sql.append(" inner join cat_solicitudes_tipo st on st.id = s.tiposolicitud_id ");
        sql.append(" inner join cat_solicitudes_estado se on se.id = s.estadosolicitud_id ");
        sql.append(" inner join ent_solicitudes_historial h on h.id = ");
        sql.append(" (select min(hh.id) from ent_solicitudes_historial hh where  hh.solicitud_id = s.id) ");
        sql.append(" inner join ent_usuario u on u.id = h.usuario_id ");
        sql.append(" inner join ent_solicitudes_historial h2 on h2.id = ");
        sql.append(" (select max(hh.id) from ent_solicitudes_historial hh where  hh.solicitud_id = s.id) ");
        sql.append(" inner join ent_usuario u2 on u2.id = h2.usuario_id ");
        sql.append(" where 1 = 1 ");

        if (condominioId != null && !condominioId.toString().isEmpty()) {
            sql.append(" and s.condominio_id = ").append(condominioId);
        }

        if (tipoId != null && !tipoId.toString().isEmpty()) {
            sql.append(" and s.tipoSolicitud_id = ").append(tipoId);
        }

        if (estadoId != null && !estadoId.toString().isEmpty()) {
            sql.append(" and s.estadoSolicitud_id = ").append(estadoId);
        }

//        List<Solicitud> solicitudesList = executeQuery(jpql.toString());
        List<SolicitudHistorial> solicitudesList = new ArrayList<SolicitudHistorial>();
        List<Object[]> lista = executeNativeQuery(sql.toString());
        if (lista != null && !lista.isEmpty()) {
            for (Object[] o : lista) {
                SolicitudHistorial sh = new SolicitudHistorial();
                Solicitud s = new Solicitud();
                s.setId(o[7]==null?null:new Long(o[7].toString()));
                s.setFechaSolicitud(o[8]==null?null:UtilFile.strToDate(o[8].toString(), "yyyy-MM-dd"));
                s.setFechaSolucion(o[9]==null?null:UtilFile.strToDate(o[9].toString(), "yyyy-MM-dd"));
                s.setConsecutivo(o[10]==null?null:new Long(o[10].toString()));
                s.setSolicitante(o[11]==null?null:o[11].toString());
                
                
                Usuario u = new Usuario();
                u.setUsuario(o[12]==null?null:o[12].toString());
                sh.setUsuario(u);
                sh.setComentario(o[13]==null?null:o[13].toString());
                sh.setUsuarioRegistra(o[14]==null?null:o[14].toString());
                
//                Departamento d = new Departamento();
                
                Condominio c = new Condominio();
                c.setId(o[15]==null?null:new Long(o[15].toString()));
                c.setNombre(o[0]==null?null:o[0].toString());
                c.setClave(o[1]==null?null:o[1].toString());
                s.setCondominio(c);
//                d.setCondominio(c);
                
//                Torre t = new Torre();
//                t.setNombre(o[2]==null?null:o[2].toString());
//                d.setTorre(t);
                
//                d.setNombre(o[3]==null?null:o[3].toString());
//                s.setDepartamento(d);
                
                SolicitudesTipo tipoSolicitud = new SolicitudesTipo();
                tipoSolicitud.setId(o[16]==null?null:new Long(o[16].toString()));
                tipoSolicitud.setNombre(o[4]==null?null:o[4].toString());
                tipoSolicitud.setClave(o[5]==null?null:o[5].toString());
                s.setTipoSolicitud(tipoSolicitud);
                
                SolicitudesEstado se = new SolicitudesEstado();
                se.setId(o[17]==null?null:new Long(o[17].toString()));
                se.setNombre(o[6]==null?null:o[6].toString());
                
                s.setEstadoSolicitud(se);
                
                sh.setSolicitud(s);
                solicitudesList.add(sh);

            }
        }

        return solicitudesList == null || solicitudesList.isEmpty() ? null : solicitudesList;
    }
    
    @Override
    public List<SolicitudHistorial> getSolicitudesHistorial(Long condominioId, List<SolicitudesTipo> tipoLst, Long estadoId) {

        StringBuilder sql = new StringBuilder("select c.nombre, c.clave,'', '', st.nombre, st.clave, se.nombre, s.id, s.fechasolicitud, ");
        sql.append(" s.fechasolucion , s.consecutivo, s.solicitante, u.usuario,  h.comentario, u2.usuario, c.id, st.id, se.id ");
        sql.append(" from ent_solicitudes s ");
        sql.append(" inner join ent_condominio c on c.id = s.condominio_id ");
        sql.append(" inner join cat_solicitudes_tipo st on st.id = s.tiposolicitud_id ");
        sql.append(" inner join cat_solicitudes_estado se on se.id = s.estadosolicitud_id ");
        sql.append(" inner join ent_solicitudes_historial h on h.id = ");
        sql.append(" (select min(hh.id) from ent_solicitudes_historial hh where  hh.solicitud_id = s.id) ");
        sql.append(" inner join ent_usuario u on u.id = h.usuario_id ");
        sql.append(" inner join ent_solicitudes_historial h2 on h2.id = ");
        sql.append(" (select max(hh.id) from ent_solicitudes_historial hh where  hh.solicitud_id = s.id) ");
        sql.append(" inner join ent_usuario u2 on u2.id = h2.usuario_id ");
        sql.append(" where 1 = 1 ");

        if (condominioId != null && !condominioId.toString().isEmpty()) {
            sql.append(" and s.condominio_id = ").append(condominioId);
        }

        if (tipoLst != null && !tipoLst.isEmpty()) {
            sql.append(" and (");
            for (SolicitudesTipo st : tipoLst) {
                sql.append("  s.tipoSolicitud_id = ").append(st.getId()).append(" or ");
            }
            sql.delete(sql.lastIndexOf(" or "), sql.lastIndexOf(" or ") + 4);
            sql.append(" )");
        }

        if (estadoId != null && !estadoId.toString().isEmpty()) {
            sql.append(" and s.estadoSolicitud_id = ").append(estadoId);
        }

//        List<Solicitud> solicitudesList = executeQuery(jpql.toString());
        List<SolicitudHistorial> solicitudesList = new ArrayList<SolicitudHistorial>();
        List<Object[]> lista = executeNativeQuery(sql.toString());
        if (lista != null && !lista.isEmpty()) {
            for (Object[] o : lista) {
                SolicitudHistorial sh = new SolicitudHistorial();
                Solicitud s = new Solicitud();
                s.setId(o[7]==null?null:new Long(o[7].toString()));
                s.setFechaSolicitud(o[8]==null?null:UtilFile.strToDate(o[8].toString(), "yyyy-MM-dd"));
                s.setFechaSolucion(o[9]==null?null:UtilFile.strToDate(o[9].toString(), "yyyy-MM-dd"));
                s.setConsecutivo(o[10]==null?null:new Long(o[10].toString()));
                s.setSolicitante(o[11]==null?null:o[11].toString());
                
                
                Usuario u = new Usuario();
                u.setUsuario(o[12]==null?null:o[12].toString());
                sh.setUsuario(u);
                sh.setComentario(o[13]==null?null:o[13].toString());
                sh.setUsuarioRegistra(o[14]==null?null:o[14].toString());
                
                Condominio c = new Condominio();
                c.setId(o[15]==null?null:new Long(o[15].toString()));
                c.setNombre(o[0]==null?null:o[0].toString());
                c.setClave(o[1]==null?null:o[1].toString());
                s.setCondominio(c);
                
                
                SolicitudesTipo tipoSolicitud = new SolicitudesTipo();
                tipoSolicitud.setId(o[16]==null?null:new Long(o[16].toString()));
                tipoSolicitud.setNombre(o[4]==null?null:o[4].toString());
                tipoSolicitud.setClave(o[5]==null?null:o[5].toString());
                s.setTipoSolicitud(tipoSolicitud);
                
                SolicitudesEstado se = new SolicitudesEstado();
                se.setId(o[17]==null?null:new Long(o[17].toString()));
                se.setNombre(o[6]==null?null:o[6].toString());
                
                s.setEstadoSolicitud(se);
                
                sh.setSolicitud(s);
                solicitudesList.add(sh);

            }
        }

        return solicitudesList == null || solicitudesList.isEmpty() ? null : solicitudesList;
    }
}
