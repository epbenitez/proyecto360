
package com.allinone.jobs.quartz;

/**
 *
 * @author Patricia Benítez
 */

import com.allinone.util.AsyncMailSender;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.commons.CommonsLogger;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.LogManager;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author Patricia Benítez
 */
public class TareaQuartz extends QuartzJobBean {

    protected static Logger log;
    protected AsyncMailSender mailSender;
    protected ApplicationContext applicationContext;

    public TareaQuartz() {
        log = new CommonsLogger(new Log4JLogger(LogManager.getLogger(this.getClass().getName())));

    }

    protected final void sendEmail(String from, String[] to, String subject, String body, InputStreamSource attach) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setBcc(to);
        helper.setSubject(subject);
        helper.setText("<html><body>" + body + "</body></html>", true);
        mailSender.send(message);
    }

    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    
    public void getApplicationContext(JobExecutionContext context)
            throws Exception {
        applicationContext = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
        if (applicationContext == null) {
            log.error("%s", "No application context available in scheduler context for key \"applicationContext\"");
        }
    }

    public AsyncMailSender getMailSender() throws SchedulerException {
        if(mailSender == null){
            try {
                mailSender = (AsyncMailSender) applicationContext.getBean("mailSender");
            } catch (BeansException beansException) {
                beansException.printStackTrace();
            }
        }
        return mailSender;
    }

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
