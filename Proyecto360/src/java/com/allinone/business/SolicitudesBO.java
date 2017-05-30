package com.allinone.business;

import com.allinone.persistence.model.Solicitud;
import com.allinone.persistence.model.SolicitudHistorial;
import com.allinone.persistence.model.SolicitudesUmbrales;
import com.allinone.service.Service;
import com.allinone.util.UtilFile;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class SolicitudesBO {

    private Service service;

    public SolicitudesBO(Service service) {
        this.service = service;
    }

    public List<SolicitudHistorial> getUmbrales(List<SolicitudHistorial> solicitudesH) {
        if (solicitudesH == null || solicitudesH.isEmpty()) {
            return null;
        }

        for (SolicitudHistorial sh : solicitudesH) {
            SolicitudesUmbrales umbral = service.getSolicitudesUmbralesDao().findUmbral(sh.getSolicitud().getCondominio().getId(), sh.getSolicitud().getTipoServicio().getId());
            sh.getSolicitud().setUmbral(umbral);
        }

        return solicitudesH;
    }

    /**
     * Color del umbral correspondiente de acuerdo a los dias pasados desde la
     * fecha compromiso
     *
     * @param umbrales
     * @param fecha
     * @param fechaSolucion
     * @return
     */
    public String[] getColor(SolicitudesUmbrales umbrales, Date fecha, Date fechaSolucion) {
        String[] colorMasTooltip = new String[2];
        String color = "gray";
        if (fecha == null || umbrales == null) {
            colorMasTooltip[0] = color;
            colorMasTooltip[1] = "Sin umbrales";

            return colorMasTooltip;
        }
        Long diasTranscurridosDesdeFechaCompromiso = 0L;
        if (fechaSolucion == null) {
            //Si aun no se soluciona, se toma la fecha actual para saber cuántos días han pasado
            diasTranscurridosDesdeFechaCompromiso = UtilFile.getDias(fecha, new Date());
        } else {
            //Si ya se solucionó, se toma la fecha de solución para saber cuántos días han pasado
            //diasTranscurridosDesdeFechaCompromiso = UtilFile.getDias(fechaCompromiso, fechaSolucion);
            //20160404- Si ya hay fecha de solucion, se marca en blanco
            color = "#cef9ff";
            colorMasTooltip[0] = color;
            colorMasTooltip[1] = "Atendido";

            return colorMasTooltip;
        }

        if (diasTranscurridosDesdeFechaCompromiso <= umbrales.getDiasUmbralVerde()) {
            color = "green";

            colorMasTooltip[0] = color;
            colorMasTooltip[1] = "Solicitud en tiempo";
        } else if (diasTranscurridosDesdeFechaCompromiso > umbrales.getDiasUmbralVerde()
                && diasTranscurridosDesdeFechaCompromiso <= umbrales.getDiasUmbralNaranja()) {
            color = "orange";

            colorMasTooltip[0] = color;
            colorMasTooltip[1] = "Atención";
        } else if (diasTranscurridosDesdeFechaCompromiso > umbrales.getDiasUmbralNaranja()) {
            color = "red";

            colorMasTooltip[0] = color;
            colorMasTooltip[1] = "¡Atención urgente!";
        }

        return colorMasTooltip;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
