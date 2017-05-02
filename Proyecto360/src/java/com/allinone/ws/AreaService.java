package com.allinone.ws;

import com.allinone.persistence.model.SolicitudesArea;
import com.allinone.service.Service;
import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author patriciabenitez
 */
@WebService(serviceName = "AreaService")
public class AreaService  extends SpringBeanAutowiringSupport {

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
     */
    @WebMethod(operationName = "areas")
    public String areas() {
        List<SolicitudesArea> areas = service.getSolicitudesAreaDao().findAll();
        if (areas == null || areas.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append("{").append("\"SolicitudesArea\":[");
        for (SolicitudesArea sa : areas) {

            result.append("{\"id\":").append(sa.getId())
                    .append(",\"nombre\":\"").append(sa.getNombre()).append("\"},");

        }
        String res = result.length() <= 0 ? "" : result.toString().substring(0, result.toString().lastIndexOf(","));
        return res + "]}";
    }
}
