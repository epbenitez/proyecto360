package com.allinone.ws;

import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
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
@WebService(serviceName = "UsuarioService", targetNamespace = "http://localhost:8080/ws")
public class UsuarioService extends SpringBeanAutowiringSupport {

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
     * @param usuario
     * @return 
     */
    @WebMethod(operationName = "datosUsuario")
    public String datosUsuario(@WebParam(name = "usuario") String usuario) {
        Usuario u = service.getUsuarioDao().findById(usuario);
        if (u == null) {
            return "null";
        } else {
            DepartamentoUsuario du = service.getDepartamentoUsuarioDao().findByUserId(u.getId());
            if (du == null ) {
                return "";
            } else {
                StringBuilder result = new StringBuilder();
                    result.append("{\"Condominio\":[{\"id\":").append(du.getDepartamento().getCondominio().getId())
                            .append(",\"nombre\":\"").append(du.getDepartamento().getCondominio().getNombre()).append("\"}],")
                            .append("\"Torre\":[{\"id\":").append(du.getDepartamento().getTorre().getId())
                            .append(",\"nombre\":\"").append(du.getDepartamento().getTorre().getNombre()).append("\"}],")
                            .append("\"Departamento\":[{\"id\":").append(du.getDepartamento().getId())
                            .append(",\"nombre\":\"").append(du.getDepartamento().getNombre()).append("\"}]}");
                    
                    return result.toString();
            }

        }
    }
}
