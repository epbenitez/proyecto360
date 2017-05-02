/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.allinone.business;

import com.allinone.persistence.model.Configuracion;
import com.allinone.service.Service;
import com.allinone.util.Ambiente;
import com.opensymphony.xwork2.ActionContext;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class ConfiguracionAppBO {

    public void actualizaConfiguracion(String[] config, Service service) {
        if (config != null && config.length > 0) {
            for (String c : config) {
                String[] datos = c.split("-");
                Long id = new Long(datos[1]);
                Configuracion configuracion = service.getConfiguracionDao().findById(id);
                configuracion.setValor(datos[0].trim());
                service.getConfiguracionDao().update(configuracion);
            }
        }
    }

    public void actualizaConfiguracionEnMemoria(Service service) {

        System.out.println("::::: ACTUALIZACION DE VALORES EN MEMORIA ::::");
        List<Configuracion> configuracionList = service.getConfiguracionDao().findAll();

        Ambiente ambiente = new Ambiente();
        ambiente.setConfiguracionList(configuracionList);
        for (Configuracion c : configuracionList) {
            System.out.println(c.getPropiedad() + "," + c.getValor());
            ActionContext.getContext().getSession().get(c.getPropiedad());
            ActionContext.getContext().getSession().put(c.getPropiedad(), c.getValor());
        }
    }

    
}
