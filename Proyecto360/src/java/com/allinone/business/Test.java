
/**
* ALL IN ONE
* 
* Created on : 07-ago-2015, 11:06:28
**/

package com.allinone.business;


import com.allinone.util.UtilFile;
import java.util.Date;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
/**
 *
 * @author Patricia Benitez 
 */
public class Test  extends QuartzJobBean
{
	 
	protected void executeInternal(JobExecutionContext context)
	throws JobExecutionException {
 
		System.out.println("Job AllInOne" + UtilFile.dateToString(new Date(), "yyyy-MM-dd hh:mm:ss"));
 
	}

    public static void main(String[] args){
        String cal = "8.1";
        Double calificacion = new Double(cal);
        System.out.println("c: "+ calificacion);
    }
}
