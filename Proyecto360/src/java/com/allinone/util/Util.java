/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Patricia Benitez
 */
public class Util {

    public static Date agregaDias(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }

    public static Long numeroDeDias(Date finicial, Date ffinal) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(finicial);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(ffinal);
        long diff = calendar2.getTimeInMillis() -calendar1.getTimeInMillis();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;
    }

    public static String enmascara(String numero) {

        Float calificacion = Float.parseFloat(numero);

        if (calificacion < 1) {
            return "NP";
        } else if (calificacion >= 1 && calificacion <= 59) {
            return "NA";
        } else {
            return calificacion.toString();
        }
    }

    public static String getURL(String url, HashMap<String, String> params) throws UnsupportedEncodingException, IOException {
        String inputText = "";
        HashMap<String, String> map = new HashMap<String, String>();
        URL postUrl = new URL(url);

        try {
            String data = "";

            for (String param : params.keySet()) {

                String valor = params.get(param);

                data += "&" + URLEncoder.encode(param, "UTF-8") + "=" + URLEncoder.encode(valor, "UTF-8");
            }

            URLConnection conn = postUrl.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = rd.readLine()) != null) {
                inputText += line;
            }

            System.out.println(inputText);
            wr.close();
            rd.close();

        } catch (MalformedURLException ex) {
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }

        return inputText;
    }
}
