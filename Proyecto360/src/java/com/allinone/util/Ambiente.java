package com.allinone.util;

import com.allinone.persistence.model.Configuracion;
import com.allinone.persistence.model.EntidadFederativa;
import com.allinone.persistence.model.NotificacionEstatus;
import com.allinone.persistence.model.Pais;
import com.allinone.persistence.model.ProyectoCategoria;
import com.allinone.persistence.model.ProyectoCuota;
import com.allinone.persistence.model.ProyectoEstatus;
import com.allinone.persistence.model.ProyectoPrioridad;
import com.allinone.persistence.model.ProyectoTipo;
import com.allinone.persistence.model.Rol;
import com.allinone.service.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Manejo de listas, mapas y otras estructuras de uso general.
 *
 * @author Patricia Benitez
 */
public class Ambiente {

    private Logger log = LogManager.getLogger(this.getClass().getName());
    private Service service;

    /**
     * Obtiene el valor de la variable service
     *
     * @return el valor de la variable service
     */
    public Service getService() {
        return service;
    }

    /**
     * Establece el valor de la variable service
     *
     * @param service nuevo valor de la variable service
     */
    public void setService(Service service) {
        this.service = service;
    }

    /**
     * Lista de Entidades Federativas
     */
    List<EntidadFederativa> entidadFederativaList = new ArrayList<EntidadFederativa>();

    /**
     * Obtiene la lista de entidades federativas
     *
     * @return entidadFederativaList.
     */
    public List<EntidadFederativa> getEntidadFederativa() {

        if (entidadFederativaList == null || entidadFederativaList.isEmpty()) {
            entidadFederativaList = service.getEntidadFederativaDao().findAll();
        }
        Collections.sort(entidadFederativaList);
        return entidadFederativaList;
    }

    List<Rol> rolList = new ArrayList<Rol>();

    public List<Rol> getAllRoles() {
        if (rolList == null || rolList.isEmpty()) {
            rolList = service.getRolDao().findAll();
        }
        return rolList;
    }

    List<Configuracion> configuracionList = new ArrayList<Configuracion>();

    public List<Configuracion> getConfiguracion() {
        if (configuracionList == null || configuracionList.isEmpty()) {
            System.out.println("::::CONSULTA DE LA CONFIGURACION::::");
            configuracionList = service.getConfiguracionDao().findAll();
        }
//        System.out.println("::::CONSULTA DE LA CONFIGURACION VALORES ACTUALES AMBIENTE::::");
//        for (Configuracion c : configuracionList) {
//            System.out.println(c.getPropiedad() + "," + c.getValor());
//        }

        return configuracionList;
    }

    public void setConfiguracionList(List<Configuracion> configuracionList) {
        this.configuracionList = configuracionList;
    }

    private List<Pais> paisNacimientoList = new ArrayList<Pais>();

    public List<Pais> getPaisNacimientoList() {
        if (paisNacimientoList == null || paisNacimientoList.isEmpty()) {
            paisNacimientoList = service.getPaisDao().findAll();

            Collections.sort(paisNacimientoList);
        }

        return paisNacimientoList;
    }

    public List<Rol> getRolList() {
        if (rolList == null || rolList.isEmpty()) {
            rolList = service.getRolDao().findAll();
        }
        return rolList;
    }

    List<ProyectoCategoria> proyectoCategoriaList = new ArrayList<ProyectoCategoria>();

    public List<ProyectoCategoria> getProyectoCategoriaList() {
        if (proyectoCategoriaList == null || proyectoCategoriaList.isEmpty()) {
            proyectoCategoriaList = service.getProyectoCategoriaDao().findAll();
        }
        return proyectoCategoriaList;
    }
    
    List<ProyectoCuota> proyectoCuotaList = new ArrayList<ProyectoCuota>();

    public List<ProyectoCuota> getProyectoCuotaList() {
        if (proyectoCuotaList == null || proyectoCuotaList.isEmpty()) {
            proyectoCuotaList = service.getProyectoCuotaDao().findAll();
        }
        return proyectoCuotaList;
    }
    
    List<ProyectoEstatus> proyectoEstatusList = new ArrayList<ProyectoEstatus>();

    public List<ProyectoEstatus> getProyectoEstatusList() {
        if (proyectoEstatusList == null || proyectoEstatusList.isEmpty()) {
            proyectoEstatusList = service.getProyectoEstatusDao().findAll();
        }
        return proyectoEstatusList;
    }
    
    List<ProyectoPrioridad> proyectoPrioridadList = new ArrayList<ProyectoPrioridad>();

    public List<ProyectoPrioridad> getProyectoPrioridadList() {
        if (proyectoPrioridadList == null || proyectoPrioridadList.isEmpty()) {
            proyectoPrioridadList = service.getProyectoPrioridadDao().findAll();
        }
        return proyectoPrioridadList;
    }
    
    List<ProyectoTipo> proyectoTipoList = new ArrayList<ProyectoTipo>();

    public List<ProyectoTipo> getProyectoTipoList() {
        if (proyectoTipoList == null || proyectoTipoList.isEmpty()) {
            proyectoTipoList = service.getProyectoTipoDao().findAll();
        }
        return proyectoTipoList;
    }
    
    List<NotificacionEstatus> notificacionEstatusList = new ArrayList<NotificacionEstatus>();

    public List<NotificacionEstatus> getNotificacionEstatusList() {
        if (notificacionEstatusList == null || notificacionEstatusList.isEmpty()) {
            notificacionEstatusList = service.getNotificacionEstatusDao().findAll();
        }
        return notificacionEstatusList;
    }

}
