package com.allinone.actions.ajax;

import com.allinone.domain.EquipoReporteEjecutivo;
import com.allinone.persistence.model.AreaReglasReservacion;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class EquipoAjaxAction extends JSONAjaxAction {
    
    private String anio;
    private Long condominioId;

    public String reporteEjecutivo() {

        List<EquipoReporteEjecutivo> reporte = getDaos().getEquipoMantenimientoDao().reporteEjecutivo(anio, condominioId);

        if (reporte == null) {
        } else {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            String[] enero, febrero, marzo, abril, mayo, junio, julio, agosto, septiembre, octubre, noviembre, diciembre;
            for (EquipoReporteEjecutivo r : reporte) {
                enero = r.getEnero() == null ? null : r.getEnero().split("-");
                febrero = r.getFebrero()== null ? null : r.getFebrero().split("-");
                marzo = r.getMarzo()== null ? null : r.getMarzo().split("-");
                abril = r.getAbril()== null ? null : r.getAbril().split("-");
                mayo = r.getMayo()== null ? null : r.getMayo().split("-");
                junio = r.getJunio()== null ? null : r.getJunio().split("-");
                julio = r.getJulio()== null ? null : r.getJulio().split("-");
                agosto = r.getAgosto()== null ? null : r.getAgosto().split("-");
                septiembre = r.getSeptiembre()== null ? null : r.getSeptiembre().split("-");
                octubre = r.getOctubre()== null ? null : r.getOctubre().split("-");
                noviembre = r.getNoviembre()== null ? null : r.getNoviembre().split("-");
                diciembre = r.getDiciembre()== null ? null : r.getDiciembre().split("-");
                getJsonResult().add("[\"" + r.getEquipo().getNombre()
                        + "\", \"" + r.getFrecuencia().getNombre()
                         + "\", \"" + (enero == null ? "" :  enero[1] +"<br>"+ enero[0])
                        + "\", \"" + (febrero == null ? "" :  febrero[1] +"<br>"+ febrero[0])
                        + "\", \"" + (marzo == null ? "" :  marzo[1] +"<br>"+ marzo[0])
                        + "\", \"" + (abril == null ? "" :  abril[1]+"<br>"+ abril[0] )
                        + "\", \"" + (mayo == null ? "" :  mayo[1]+"<br>"+ mayo[0])
                        + "\", \"" + (junio == null ? "" :  junio[1] +"<br>"+ junio[0])
                        + "\", \"" + (julio == null ? "" :  julio[1] +"<br>"+ julio[0]+"")
                        + "\", \"" + (agosto == null ? "" :  agosto[1] +"<br>"+ agosto[0])
                        + "\", \"" + (septiembre == null ? "" :  septiembre[1] +"<br>"+ septiembre[0])
                        + "\", \"" + (octubre == null ? "" :  octubre[1] +"<br>"+octubre[0])
                        + "\", \"" + (noviembre == null ? "" :  noviembre[1] +"<br>"+ noviembre[0])
                        + "\", \"" + (diciembre == null ? "" :  diciembre[1]+"<br>"+ diciembre[0])
                       
//                        + "\", \"" + (enero == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + enero[1] + "' style='color:" + (enero[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + enero[0])
//                        + "\", \"" + (febrero == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + febrero[1] + "' style='color:" + (febrero[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + febrero[0])
//                        + "\", \"" + (marzo == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + marzo[1] + "' style='color:" + (marzo[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + marzo[0])
//                        + "\", \"" + (abril == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + abril[1] + "' style='color:" + (abril[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + abril[0] )
//                        + "\", \"" + (mayo == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + mayo[1] + "' style='color:" + (mayo[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + mayo[0])
//                        + "\", \"" + (junio == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + junio[1] + "' style='color:" + (junio[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + junio[0])
//                        + "\", \"" + (julio == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + julio[1] + "' style='color:" + (julio[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + julio[0])
//                        + "\", \"" + (agosto == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + agosto[1] + "' style='color:" + (agosto[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + agosto[0])
//                        + "\", \"" + (septiembre == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + septiembre[1] + "' style='color:" + (septiembre[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + septiembre[0])
//                        + "\", \"" + (octubre == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + octubre[1] + "' style='color:" + (octubre[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + octubre[0])
//                        + "\", \"" + (noviembre == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + noviembre[1] + "' style='color:" + (noviembre[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + noviembre[0])
//                        + "\", \"" + (diciembre == null ? "" : "<span data-toggle='tooltip' data-placement='top' title='" + diciembre[1] + "' style='color:" + (diciembre[1].equals("Programado") ? "yellow" : "green") + "' class='fa-stack'><i class='fa fa-square fa-stack-2x'></i><i class='fa fa-stack-1x fa-inverse'></i></span><br>" + diciembre[0])
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }
    
    
}
