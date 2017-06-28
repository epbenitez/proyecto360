package com.allinone.jobs;

import com.allinone.jobs.quartz.TareaQuartz;
import com.allinone.persistence.model.Menu;
import com.allinone.persistence.model.VWSolicitudesUmbralesResponsables;
import com.allinone.service.Service;
import com.allinone.util.UtilFile;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Patricia Benítez
 */
public class EnvioCorreosJob extends TareaQuartz {

//    private AsyncMailSender mailSender;
    protected void executeInternal(JobExecutionContext context)
            throws JobExecutionException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        try {
            super.getApplicationContext(context);
            super.getMailSender();

            Service service = (Service) applicationContext.getBean("service");
            List<VWSolicitudesUmbralesResponsables> lista = service.getVwSolicitudesUmbralesResponsablesDao().findSolicitudes("rojo");
            for (VWSolicitudesUmbralesResponsables s : lista) {
                System.out.println("Job AllInOne" + UtilFile.dateToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
                String[] to = new String[1];
                to[0] = s.getCorreoElectronico();
                String body = "<b>Umbral:</b> " + s.getUmbral().toUpperCase() + "<br>"
                        + "<b>Departamento:</b> " + s.getDepartamento().getNombre() + "<br>"
                        + "<b>Condominio:</b> " + s.getDepartamento().getCondominio().getClave()  + "<br>"
                        + "<b>Solicitud:</b> " + s.getComentario() + "<br>"
                        + "<b>Solicitante:</b> " + s.getSolicitante() + "<br>"
                        + "<b>Fecha de Solicitud:</b> " + UtilFile.dateToString(s.getFechaSolicitud(), "dd-MM-yyyy hh:mm")+ "<br><br>"
                        + "<i>Por favor, no responda a este correo, el cuál fue generado automáticamente para ayudar a identificar las solicitudes que tienen la necesidad de ser atendidas de forma urgente.</i>";
                super.sendEmail("neooku@gmail.com", to, "Envío Automático: Departamento " + s.getDepartamento().getNombre(), body, null);
            }

        } catch (MessagingException ex) {
            ex.printStackTrace();
            Logger.getLogger(EnvioCorreosJob.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(EnvioCorreosJob.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
