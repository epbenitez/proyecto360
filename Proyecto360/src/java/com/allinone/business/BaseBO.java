/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.business;

import com.allinone.actions.Mensajes;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import com.allinone.util.Ambiente;
import com.allinone.util.AsyncMailSender;
import java.util.Collection;
import java.util.StringTokenizer;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * Funciones básicas de la lógica de negocios
 *
 * @author Patricia Benitez
 */
public class BaseBO implements Mensajes{

    protected Logger log = LogManager.getLogger(this.getClass().getName());
    protected Ambiente ambiente = new Ambiente();
    protected Service service;

    private AsyncMailSender mailSender;
    
    /**
     * Constructor Default
     */
    public BaseBO() {
    }

    /**
     * Constructor que recibe un objeto de tipo <code>service</code>
     * @param service
     * @see mx.gob.sep.pead.service.Service
     */
    public BaseBO(Service service) {
        this.service = service;
        ambiente.setService(service);
    }

    
    public Usuario getUsuarioByFolio(String folio, Service service) {
        Usuario usuario = null;

        if (folio != null && !folio.isEmpty() && service != null) {
            usuario = service.getUsuarioDao().findById(folio);
        }

        return usuario;
    }

    /**
     * Verifica que el usuario sea un administrativo
     * @return true si es administrativo, false de lo contrario.
     */
    protected boolean isAdministrativo() {
        Collection<GrantedAuthority> gas = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        for (GrantedAuthority ga : gas) {
            if (ga.getAuthority().equals("ROLE_ALUMNO")) {
                return false;
            }
            if (ga.getAuthority().equals("ROLE_TUTOR")) {
                return false;
            }
            if (ga.getAuthority().equals("ROLE_FACILITADOR")) {
                return false;
            }
        }
        return true;
    }

    

    /**
     * Obtiene la interfaz JavaMailSender, para la creación correos electrónicos.
     *
     * @return manejador del envío de emails.
     */
    private AsyncMailSender getMailSender() {
        if (mailSender == null) {
            try {
                ApplicationContext applicationContext =
                        WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
                mailSender = (AsyncMailSender) applicationContext.getBean("mailSender");
            } catch (BeansException beansException) {
                beansException.printStackTrace();
            }
        }
        return mailSender;
    }

    /**
     * Envía un correo electrónico a una lista de destinatarios junto con un archivo anexo.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Lista correos electrónicos de los destinatarios (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @param attach Archivo anexo.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String[] to, String subject, String body, InputStreamSource attach) throws MessagingException {
        AsyncMailSender sender = getMailSender();
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
//        helper.setBcc(to);
        helper.setSubject(subject);
        helper.setText("<html><body>" + body + "</body></html>", true);
        sender.send(message);
    }

    /**
     * Envía un correo electrónico al destinatario deseado.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Correo electrónico del destinatario (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String to, String subject, String body) throws MessagingException {
        String[] toArray = {};
        if (to != null && !to.isEmpty()) {
            StringTokenizer st = new StringTokenizer(to, ",");
            toArray = new String[st.countTokens()];
            int i = 0;
            while (st.hasMoreTokens()) {
                toArray[i] = st.nextToken().trim();
                i++;
            }
        }
        sendEmail(from, toArray, subject, body);
    }

    /**
     * Envía un correo electrónico a una lista de destinatarios.
     *
     * @param from Correo electrónico del remitente (De).
     * @param to Lista correos electrónicos de los destinatarios (Para).
     * @param subject Asunto del correo electrónico.
     * @param body Cuerpo del correo electrónico.
     * @throws MessagingException
     */
    protected final void sendEmail(String from, String[] to, String subject, String body) throws MessagingException {
        sendEmail(from, to, subject, body, null);
    }
}
