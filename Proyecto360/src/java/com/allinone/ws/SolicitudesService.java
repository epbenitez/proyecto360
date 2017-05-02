package com.allinone.ws;

import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesArea;
import com.allinone.persistence.model.SolicitudesCategoria;
import com.allinone.persistence.model.SolicitudesEstado;
import com.allinone.persistence.model.SolicitudesPermisos;
import com.allinone.persistence.model.SolicitudesTipo;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author patriciabenitez
 */
@WebService(serviceName = "SolicitudesService")
public class SolicitudesService extends SpringBeanAutowiringSupport {

    @Autowired
    @Qualifier("service")
    private Service service;

    @WebMethod(exclude = true)
    public Service getService() {
        return service;
    }

    @WebMethod(exclude = true)
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * This is a sample web service operation
     *
     * @param condominioId Id del condominio
     * @param usuario Nombre de usuario del condominio
     * @return Lista de permisos por condominio y usuario
     */
    @WebMethod(operationName = "permisosSolicitudes")
    public String permisosSolicitudes(@WebParam(name = "condominioId") String condominioId,
            @WebParam(name = "usuario") String usuario) {

        try {
            Usuario u = service.getUsuarioDao().findById(usuario);
            if (u == null) {
                return "null";
            }

            List<SolicitudesPermisos> permisos = service.getSolicitudesPermisosDao().findByCondominio(new Long(condominioId), u.getId());
            if (permisos == null || permisos.isEmpty()) {
                return "";
            }
            StringBuilder result = new StringBuilder();
            result.append("{").append("\"TipoSolicitud\":[");
            for (SolicitudesPermisos sp : permisos) {

                result.append("{\"id\":").append(sp.getTipoSolicitud().getId())
                        .append(",\"nombre\":\"").append(sp.getTipoSolicitud().getNombre()).append("\"")
                        .append(",\"permiso\":\"").append(sp.getPermiso()).append("\"},");

            }
            String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
            return res + "]}";

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR";
        }
    }

    /**
     * Web service operation
     *
     * @return Catálogo de Estatus de Solicitudes
     */
    @WebMethod(operationName = "estatusSolicitudes")
    public String estatusSolicitudes() {
        List<SolicitudesEstado> estatus = service.getSolicitudesEstadoDao().findAll();
        if (estatus == null || estatus.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append("{").append("\"SolicitudesEstatus\":[");
        for (SolicitudesEstado se : estatus) {

            result.append("{\"id\":").append(se.getId())
                    .append(",\"nombre\":\"").append(se.getNombre()).append("\"},");

        }
        String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
        return res + "]}";

    }

    /**
     * Web service operation
     *
     * @return Catálogo de Estatus de Solicitudes
     */
    @WebMethod(operationName = "tipoSolicitudes")
    public String tipoSolicitudes() {
        List<SolicitudesTipo> tipos = service.getSolicitudesTipoDao().findAll();
        if (tipos == null || tipos.isEmpty()) {
            return "{\"ERROR\":\"No se encontraron tipos de solicitudes\"}";
        }

        StringBuilder result = new StringBuilder();
        result.append("{").append("\"SolicitudesTipo\":[");
        for (SolicitudesTipo st : tipos) {

            result.append("{\"id\":").append(st.getId())
                    .append(",\"nombre\":\"").append(st.getNombre()).append("\"},");

        }
        String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
        return res + "]}";

    }
    
    
    /**
     * Web service operation
     *
     * @return Catálogo de Estatus de Solicitudes
     */
    @WebMethod(operationName = "categoriaSolicitudes")
    public String categoriaSolicitudes() {
        List<SolicitudesCategoria> categorias = service.getSolicitudesCategoriaDao().findAll();
        if (categorias == null || categorias.isEmpty()) {
            return "{\"ERROR\":\"No se encontraron categorias de solicitudes\"}";
        }

        StringBuilder result = new StringBuilder();
        result.append("{").append("\"SolicitudesCategoria\":[");
        for (SolicitudesCategoria sc : categorias) {

            result.append("{\"id\":").append(sc.getId())
                    .append(",\"nombre\":\"").append(sc.getNombre()).append("\"},");

        }
        String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
        return res + "]}";

    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "consultaSolicitudes")
    public String consultaSolicitudes(@WebParam(name = "departamentoId") String departamentoId,
            @WebParam(name = "tipoSolicitudId") String tipoSolicitudId,
            @WebParam(name = "estadoSolicitudId") String estadoSolicitudId) {

        StringBuilder result = new StringBuilder();
        Departamento d = service.getDepartamentoDao().findById(new Long(departamentoId));
        if (d == null) {
            return ("{\"ERROR\":\"Departamento no encontrado\"}");
        }
        SolicitudesTipo tipo = null;
        if (!tipoSolicitudId.equals("-1")) {
            tipo = service.getSolicitudesTipoDao().findById(new Long(tipoSolicitudId));
            if (tipo == null) {
                return ("{\"ERROR\":\"Tipo de Solicitud no válida\"}");
            }
        }
        SolicitudesEstado estado = null;
        if (!estadoSolicitudId.equals("-1")) {
            estado = service.getSolicitudesEstadoDao().findById(new Long(estadoSolicitudId));
            if (estado == null) {
                return ("{\"ERROR\":\"Estado de solicitud no válido\"}");
            }
        }

        List<Solicitud> solicitudes = service.getSolicitudDao().getSolicitudes(d.getId(), (tipo == null ? null : tipo.getId()), (estado == null ? null : estado.getId()));
        if (solicitudes == null || solicitudes.isEmpty()) {
            return "{\"ERROR\":\"No se encontraron solicitudes con las características solicitadas\"}";
        }

        DecimalFormat myFormatter = new DecimalFormat("0000");

        result.append("{").append("\"Solicitudes\":[");
        for (Solicitud s : solicitudes) {

            result.append("{\"Condominio\":[{\"id\":").append(s.getDepartamento().getCondominio().getId())
                    .append(",\"nombre\":\"").append(s.getDepartamento().getCondominio().getNombre()).append("\"}],")
                    .append("\"Torre\":[{\"id\":").append(s.getDepartamento().getTorre().getId())
                    .append(",\"nombre\":\"").append(s.getDepartamento().getTorre().getNombre()).append("\"}],")
                    .append("\"Departamento\":[{\"id\":").append(s.getDepartamento().getId())
                    .append(",\"nombre\":\"").append(s.getDepartamento().getNombre()).append("\"}],")
                    .append("\"TipoSolicitud\":[{\"id\":").append(s.getTipoSolicitud().getId())
                    .append(",\"nombre\":\"").append(s.getTipoSolicitud().getNombre()).append("\"}],")
                    .append("\"EstatusSolicitud\":[{\"id\":").append(s.getEstadoSolicitud().getId())
                    .append(",\"nombre\":\"").append(s.getEstadoSolicitud().getNombre()).append("\"}],")
                    .append("\"FechaSolicitud\":\"").append(s.getFechaSolicitud()).append("\",")
                    .append("\"FechaSolucion\":\"").append(s.getFechaSolucion()).append("\",")
                    .append("\"Folio\":\"").append(s.getDepartamento().getCondominio().getClave()).append("-").append(s.getTipoSolicitud().getClave()).append("-").append(myFormatter.format(s.getConsecutivo())).append("\",")
                    .append("\"Comentario\":\"").append(s.getComentario()).append("\"")
                    //                    .append("\"Atendio\":\"").append(s.get()).append("\",")
                    .append("},");

        }
        String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
        return res + "]}";

    }

    /**
     * Web service operation
     *
     * @param departamentoId
     * @param tipoSolicitudId
     * @param areaSolicitudId
     * @param condomino
     * @param comentario
     * @param usuarioCreador
     * @param usuarioWS
     * @param contraseniaWS
     * @param contraseniaWS
     * @return
     */
    @WebMethod(operationName = "nuevaSolicitud")
    public String nuevaSolicitud(@WebParam(name = "departamentoId") String departamentoId,
            @WebParam(name = "tipoSolicitudId") String tipoSolicitudId,
            @WebParam(name = "areaSolicitudId") String areaSolicitudId,
            @WebParam(name = "condomino") String condomino,
            @WebParam(name = "comentario") String comentario,
            @WebParam(name = "usuarioCreador") String usuarioCreador,
            @WebParam(name = "usuarioWS") String usuarioWS,
            @WebParam(name = "contraseniaWS") String contraseniaWS) {

        Departamento d = service.getDepartamentoDao().findById(new Long(departamentoId));
        if (d == null) {
            return ("{\"ERROR\":\"Departamento no encontrado\"}");
        }
        SolicitudesTipo tipo = null;
        if (!tipoSolicitudId.equals("-1")) {
            tipo = service.getSolicitudesTipoDao().findById(new Long(tipoSolicitudId));
            if (tipo == null) {
                return ("{\"ERROR\":\"Tipo de Solicitud no válida\"}");
            }
        }
        SolicitudesArea area = null;
        if (!areaSolicitudId.equals("-1")) {
            area = service.getSolicitudesAreaDao().findById(new Long(areaSolicitudId));
            if (area == null) {
                return ("{\"ERROR\":\"Area de solicitud no válida\"}");
            }
        }
        Usuario creador = null;
        creador = service.getUsuarioDao().findById(usuarioCreador);
        if (creador == null) {
            return ("{\"ERROR\":\"Usuario creador de la solicitud,no válido\"}");
        }

        Usuario usuario = service.getUsuarioDao().findById(8035L);
        if(!usuario.getUsuario().equals(usuarioWS)){
            return("{\"ERROR\":\"El usuario Web Service con el que intenta realizar la operación, no es válido\"}");
        }
        if(!usuario.getPassword().equals(contraseniaWS)){
            return("{\"ERROR\":\"La contraseña del usuario Web Service con el que intenta realizar la operación, no es válida\"}");
        }

        Solicitud solicitud = new Solicitud();
        solicitud.setDepartamento(d);
        solicitud.setComentario(comentario);
        solicitud.setTipoSolicitud(tipo);
        solicitud.setArea(area);

        Integer consecutivo = service.getSolicitudDao().getConsecutivo(solicitud.getDepartamento().getCondominio().getId(), solicitud.getTipoSolicitud().getId());

        try {
            solicitud.setFechaSolicitud(new Date());
            solicitud.setEstadoSolicitud(new SolicitudesEstado(1L));
            solicitud.setConsecutivo(consecutivo.longValue() + 1);
            solicitud.setSolicitante(condomino);
            solicitud = service.getSolicitudDao().save(solicitud);

            SolicitudHistorial h = new SolicitudHistorial();
            h.setSolicitud(solicitud);
            h.setFecha(new Date());
            h.setUsuario(creador);
            h.setEstadoSolicitud(solicitud.getEstadoSolicitud());
            h.setComentario(solicitud.getComentario());

            service.getSolicitudHistorialDao().save(h);
        } catch (Exception e) {
            e.printStackTrace();
            return ("{\"ERROR\":\"Ocurrió un error al intentar dar de alta la solicitud. " + e.getLocalizedMessage() + "\"}");
        }
        return ("{\"OK\"}");
    }
}
