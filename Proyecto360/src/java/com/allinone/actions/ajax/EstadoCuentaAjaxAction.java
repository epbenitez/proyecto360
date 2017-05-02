package com.allinone.actions.ajax;

import com.allinone.business.AbonosBO;
import com.allinone.business.CargosBO;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.domain.EstadoCuenta;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.Cargo;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.util.StringUtils;

/**
 *
 * @author Patricia Benitez
 */
public class EstadoCuentaAjaxAction extends JSONAjaxAction {

    private String anioInicio;
    private String mesInicio;
    private String anioFin;
    private String mesFin;
    private Long departamentoId;
    private Long condominioId;

    private Long pkTorre;
    private Long pkCondominio;

    private Integer linkDetail = 1;

    //Gestión de cargas
    private Long opcionCarga;
    private String idCarga;
    private Long idMovimiento;

    private Integer tipoAbono;

    public String listado() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());

        Integer anioInicioInteger = new Integer(anioInicio);
        Integer anioFinInteger = new Integer(anioFin);
        Integer mesInicioInteger = new Integer(mesInicio);
        Integer mesFinInteger = new Integer(mesFin);
        for (Integer anio = anioInicioInteger; anio <= anioFinInteger; anio++) {
            Integer mesFinalUltimoAnio = 12;
            Integer mesInicioAnio = 1;
            if (anio.intValue() == anioInicioInteger.intValue()) {
                mesInicioAnio = mesInicioInteger;
            }
            if (anio.intValue() == anioFinInteger.intValue()) {
                mesFinalUltimoAnio = mesFinInteger;
            }
            for (Integer mes = mesInicioAnio; mes <= mesFinalUltimoAnio; mes++) {

                EstadoCuenta anteriores = bo.getCargosAbonosAnteriores(bo.getFechaInicial("01", mes.toString(), anio.toString()), departamentoId);
                EstadoCuenta periodo = bo.getCargosAbonosPeriodo(bo.getFechaInicial("01", mes.toString(), anio.toString()), bo.getFechaFinal("01", mes.toString(), anio.toString()), departamentoId);

                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);

                if (anteriores == null && periodo == null) {
                    getJsonResult().add("[\"\"]");
                } else {

                    Date d = periodo == null || periodo.getFechaPeriodo() == null ? UtilFile.strToDate(anio.toString() + "-" + (mes < 10 ? "0" + mes.toString() : mes.toString()), "yyyy-MM") : periodo.getFechaPeriodo();
                    BigDecimal cargosAnteriores = anteriores == null || anteriores.getCargos() == null ? new BigDecimal(0) : anteriores.getCargos();
                    BigDecimal abonosAnteriores = anteriores == null || anteriores.getAbonos() == null ? new BigDecimal(0) : anteriores.getAbonos();
                    BigDecimal cargosPeriodo = periodo == null || periodo.getCargos() == null ? new BigDecimal(0) : periodo.getCargos();
                    BigDecimal abonosPeriodo = periodo == null || periodo.getAbonos() == null ? new BigDecimal(0) : periodo.getAbonos();
                    System.out.println("cargosAnteriores - abonosAnteriores" + cargosAnteriores + "-" + abonosAnteriores);
                    if (cargosPeriodo.intValue() == 0 && abonosPeriodo.intValue() == 0) {
                        //No se muestran registros que no tengan cargos ni abonos
                    } else {
                        getJsonResult().add("[\"" + UtilFile.dateToString(d, "yyyy")
                                + "\", \"" + StringUtils.capitalize(UtilFile.dateToString(d, "MMMM"))
                                + "\", \"$" + df.format(cargosAnteriores.subtract(abonosAnteriores))
                                + (cargosPeriodo.intValue() == 0 || linkDetail == 0 ? "\", \"$" + df.format(cargosPeriodo) : "\", \"<a href='#seccionDetalle' onclick='detalle(" + anio.toString() + "," + mes.toString() + ")' >$" + df.format(cargosPeriodo) + "</a>")
                                + (abonosPeriodo.intValue() == 0 || linkDetail == 0 ? "\", \"$" + df.format(abonosPeriodo) : "\", \"<a href='#seccionDetalleAbono' onclick='detalleAbono(" + anio.toString() + "," + mes.toString() + ")' >$" + df.format(abonosPeriodo) + "</a>")
                                + "\", \"$" + df.format((cargosAnteriores.add(cargosPeriodo)).subtract(abonosAnteriores.add(abonosPeriodo)))
                                + " \"]");
                    }
                }

            }
        }

        return SUCCESS_JSON;
    }

    /**
     * Vista del detalle de cargos del mes en curso
     *
     * @return
     */
    public String avisoCobro() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        List<Cargo> cargos = null;
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            cargos = bo.getCargos(bo.getFechaInicial(new Date()), bo.getFechaFinal(new Date()), bo.getDepartamento(u).getId());
        }
        if (isAdministrator()) {
            cargos = bo.getCargos(bo.getFechaInicial(new Date()), bo.getFechaFinal(new Date()), bo.getDepartamento(departamentoId).getId());
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if (cargos == null) {
            getJsonResult().add("[\"No hay datos disponibles para esta consulta\",\"\",\"\"]");
        } else {

            Double sumatoriaCargos = 0d;
            Double sumatoriaCargosIntereses = 0d;

            for (Cargo c : cargos) {

                getJsonResult().add("[\"" + c.getConcepto().getNombre()
                        + "\", \"$" + (c.getConcepto().getId()==1?df.format(c.getMonto() -300):df.format(c.getMonto()))
                        + "\", \"$" + df.format((c.getMonto()))
                        + " \"]");
                sumatoriaCargos += (c.getConcepto().getId()==1?(c.getMonto() -300):(c.getMonto()));
                sumatoriaCargosIntereses += (c.getMonto());
            }

            getJsonResult().add("[\"<b>Total de cargos:</b>"
                    + "\", \"<b>$" + df.format(sumatoriaCargos) + "</b>"
                    + "\", \"<b>$" + df.format(sumatoriaCargosIntereses) + "</b>"
                    + " \"]");
        }

        return SUCCESS_JSON;
    }

    /**
     * Vista del detalle del estado de cuenta del mes seleccionado
     *
     * @return
     */
    public String detalleCobro() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());

        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal("01", mesFin, anioFin);

        List<Cargo> cargos = null;
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            cargos = bo.getCargos(dateInicial, dateFinal, bo.getDepartamento(u).getId());
        }
        if (isAdministrator()) {
            cargos = bo.getCargos(dateInicial, dateFinal, bo.getDepartamento(departamentoId).getId());
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if (cargos == null) {
            getJsonResult().add("[\"\",\"\",\"No hay datos disponibles para esta consulta\",\"\",\"\"]");
        } else {

            Double sumatoriaCargos = 0d;
            Double sumatoriaCargosIntereses = 0d;

            for (Cargo c : cargos) {

                getJsonResult().add("[\"" + UtilFile.dateToString(dateInicial, "yyyy")
                        + "\", \"" + StringUtils.capitalize(UtilFile.dateToString(dateInicial, "MMMM"))
                        + "\", \"" + c.getConcepto().getNombre()
                        + "\", \"$" + df.format(c.getMonto() * 0.95)
                        + "\", \"$" + df.format(c.getMonto())
                        + " \"]");
                sumatoriaCargos += (c.getMonto() * 0.95);
                sumatoriaCargosIntereses += (c.getMonto());
            }

            getJsonResult().add("[\"\",\"\",\"<b>Total de cargos:</b>"
                    + "\", \"<b>$" + df.format(sumatoriaCargos) + "</b>"
                    + "\", \"<b>$" + df.format(sumatoriaCargosIntereses) + "</b>"
                    + " \"]");
        }

        return SUCCESS_JSON;
    }

    /**
     * Detalle de los abonos realizados por el propietario para el mes señalado
     *
     * @return
     */
    public String detalleAbono() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());

        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal("01", mesFin, anioFin);

        List<Abono> abonos = null;
        if (isPropietario()) {
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
            abonos = bo.getAbonos(dateInicial, dateFinal, bo.getDepartamento(u).getId());
        }
        if (isAdministrator()) {
            abonos = bo.getAbonos(dateInicial, dateFinal, bo.getDepartamento(departamentoId).getId());
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if (abonos == null) {
            getJsonResult().add("[\"\",\"\",\"No hay datos disponibles para esta consulta\",\"\",\"\"]");
        } else {

            Double sumatoriaAbonos = 0d;

            for (Abono c : abonos) {

                getJsonResult().add("[\"" + UtilFile.dateToString(dateInicial, "yyyy")
                        + "\", \"" + StringUtils.capitalize(UtilFile.dateToString(dateInicial, "MMMM"))
                        + "\", \"" + StringUtils.capitalize(UtilFile.dateToString(c.getFechaPago(), "dd"))
                        + "\", \"" + c.getConcepto().getNombre()
                        + "\", \"$" + df.format(c.getMonto())
                        + " \"]");
                sumatoriaAbonos += (c.getMonto());
            }

            getJsonResult().add("[\"\",\"\",\"\",\"<b>Total de cargos:</b>"
                    + "\", \"<b>$" + df.format(sumatoriaAbonos) + "</b>"
                    + " \"]");
        }

        return SUCCESS_JSON;
    }

    public String reciboPago() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());

        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        List<Abono> abonos = bo.getAbonos(bo.getFechaInicial(new Date()), bo.getFechaFinal(new Date()), bo.getDepartamento(u).getId());

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if (abonos == null) {
            getJsonResult().add("[\"\",\"<p style='text-align: center;'>No hay datos disponibles para esta consulta</p>\",\"\"]");
        } else {

            Double sumatoria = 0d;

            for (Abono c : abonos) {

                getJsonResult().add("[\"" + UtilFile.dateToString(c.getFechaPago(), "dd-MM-yyyy")
                        + "\", \"" + c.getConcepto().getNombre()
                        + "\", \"$" + df.format(c.getMonto())
                        + " \"]");
                sumatoria += c.getMonto();
            }

            getJsonResult().add("[\""
                    + "\", \"<b>Total:</b>"
                    + "\", \"<b>$" + df.format(sumatoria) + "</b>"
                    + " \"]");
        }

        return SUCCESS_JSON;
    }

    public String reporteAbonos() {
        if (anioInicio == null || anioFin == null || mesInicio == null || mesFin == null
                || anioInicio.length() == 0 || anioFin.length() == 0 || mesInicio.length() == 0 || mesFin.length() == 0) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>Debe especificar fecha de inicio y fin</p>\",\"\"]");
        }

        if (condominioId == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No especificó el condominio</p>\",\"\"]");
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal(bo.getFechaFinal("01", mesFin, anioFin));

        List<Abono> abonos = bo.getAbonosPorCondominio(dateInicial, dateFinal, condominioId, tipoAbono);
        if (abonos == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No se encontraron pagos con la búsqueda seleccionada</p>\",\"\",\"\",\"\",\"\",\"\",\"\",\"\"]");
            return SUCCESS_JSON;
        }
        Double total = 0D;
        for (Abono a : abonos) {
            total += a.getMonto();
            getJsonResult().add("[\"" + a.getDepartamento().getTorre().getNombre()
                    + "\", \"" + a.getDepartamento().getNombre()
                    + "\", \"" + UtilFile.dateToString(a.getFechaPago(), "yyyy")
                    + "\", \"" + UtilFile.dateToString(a.getFechaPago(), "MMM")
                    + "\", \"" + UtilFile.dateToString(a.getFechaPago(), "dd")
                    + "\", \"" + a.getConcepto().getNombre()
                    + "\", \"$" + a.getMontoFormato()
                    + "\", \"" + a.getDatosPago().getAbonoTipo().getNombre()
                    + "\", \"" + a.getDatosPago().getNumero()
                    + "\", \"" + UtilFile.dateToString(a.getDatosPago().getFechaEmision(), "yyyy-MM-dd")
                    + "\", \"" + a.getDatosPago().getBanco().getNombre()
                    + "\", \"" + a.getDatosPago().getTitular()
                    + "\", \"" + (a.getFechaAdmon() == null ? "Carga Masiva" : "Administración")
                    + " \"]");
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        getJsonResult().add("[\""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \" Total:"
                + "\", \"$" + df.format(total)
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \""
                + " \"]");

        return SUCCESS_JSON;
    }

    public String reporteEstadisticaCargosAbonos() {
        if (anioInicio == null || anioFin == null || mesInicio == null || mesFin == null
                || anioInicio.length() == 0 || anioFin.length() == 0 || mesInicio.length() == 0 || mesFin.length() == 0) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>Debe especificar fecha de inicio y fin</p>\"]");
        }

        if (condominioId == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No especificó el condominio</p>\"]");
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal(bo.getFechaFinal("01", mesFin, anioFin));

        List<EstadoCuenta> saldos = bo.getSaldos(dateInicial, dateFinal, condominioId);
        if (saldos == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No se encontraron pagos con la búsqueda seleccionada</p>\"]");
            return SUCCESS_JSON;
        }
        Double totalcargos = 0D;
        Double totalabonos = 0D;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (EstadoCuenta a : saldos) {
//            totalSaldo += a.getMonto();

            totalcargos += a.getCargos().doubleValue();
            totalabonos += a.getAbonos().doubleValue();

            Calendar cal = Calendar.getInstance();
            cal.setTime(a.getFechaPeriodo());
            int mes = cal.get(Calendar.MONTH) + 1;

            getJsonResult().add("[\"" + UtilFile.dateToString(a.getFechaPeriodo(), "yyyy")
                    + "\", \"" + UtilFile.dateToString(a.getFechaPeriodo(), "MMMM")
                    + "\", \""+ (a.getCargos().intValue()==0?"$" + df.format(a.getCargos()):"<a href='/estadodecuenta/reportes/reporteEstadisticaCargosCargosAbonos.action?condominioId="+condominioId+"&mesInicio="+mes+"&anioInicio="+UtilFile.dateToString(a.getFechaPeriodo(), "yyyy")+"'  class='fancybox fancybox.iframe'>$" + df.format(a.getCargos()) + "</a>")
                    + "\", \""+ (a.getAbonos().intValue()==0?"$" + df.format(a.getAbonos()):"<a href='/estadodecuenta/reportes/reporteEstadisticaAbonosCargosAbonos.action?condominioId="+condominioId+"&mesInicio="+mes+"&anioInicio="+UtilFile.dateToString(a.getFechaPeriodo(), "yyyy")+"'   class='fancybox fancybox.iframe'>$" + df.format(a.getAbonos()) + "</a>")
                    + "\", \""+ (a.getSaldo().intValue()==0?"$" + df.format(a.getSaldo()):"<a onclick=reporteEstadisticaDetalleDepto('" + UtilFile.dateToString(a.getFechaPeriodo(), "yyyy") + "','" + mes + "'," + condominioId + ")>$" + df.format(a.getSaldo()) + "</a>")
                    + " \"]");
        }

        getJsonResult().add("[\""
                + "\", \""
                + "\", \""
                + "\", \"Total:"
                + "\", \"$" + df.format(totalcargos-totalabonos)
                + " \"]");

        return SUCCESS_JSON;
    }

    public String reporteEstadisticaDetalleDepto() {
        if (anioInicio == null || anioFin == null || mesInicio == null || mesFin == null
                || anioInicio.length() == 0 || anioFin.length() == 0 || mesInicio.length() == 0 || mesFin.length() == 0) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>Debe especificar fecha de inicio y fin</p>\"]");
        }

        if (condominioId == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No especificó el condominio</p>\"]");
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal(bo.getFechaFinal("01", mesFin, anioFin));

        List<EstadoCuenta> saldos = bo.getSaldosDetalleDepto(dateInicial, dateFinal, condominioId);
        if (saldos == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No se encontraron pagos con la búsqueda seleccionada</p>\"]");
            return SUCCESS_JSON;
        }
        Double totalcargos = 0D;
        Double totalabonos = 0D;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (EstadoCuenta a : saldos) {
//            totalSaldo += a.getMonto();

            totalcargos += a.getCargos().doubleValue();
            totalabonos += a.getAbonos().doubleValue();
            
            getJsonResult().add("[\"" + a.getDepartamento().getCondominio().getNombre()
                    + "\", \"" + a.getDepartamento().getTorre().getNombre()
                    + "\", \"" + a.getDepartamento().getNombre()
                    + "\", \"" + UtilFile.dateToString(a.getFechaPeriodo(), "yyyy")
                    + "\", \"" + UtilFile.dateToString(a.getFechaPeriodo(), "MMMM")
                    + "\", \""+ (a.getCargos().intValue()==0?"$" + df.format(a.getCargos()):"<a onclick=detalle('"+anioInicio+"','"+mesInicio+"',"+a.getDepartamento().getId()+") >$" + df.format(a.getCargos()) + "</a>")
                    + "\", \""+ (a.getAbonos().intValue()==0?"$" + df.format(a.getAbonos()):"<a onclick=detalleAbono('"+anioInicio+"','"+mesInicio+"',"+a.getDepartamento().getId()+") >$" + df.format(a.getAbonos()) + "</a>")
                    + "\", \"$" + df.format(a.getSaldo())
                    + " \"]");
        }

        getJsonResult().add("[\""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \"Total:"
                + "\", \"$" + df.format(totalcargos)
                + "\", \"$" + df.format(totalabonos)
                + "\", \"$" + df.format(totalcargos-totalabonos)
                + " \"]");

        return SUCCESS_JSON;
    }
    
    
    public String reporteEstadisticaCargos() {
        if (anioInicio == null || anioFin == null || mesInicio == null || mesFin == null
                || anioInicio.length() == 0 || anioFin.length() == 0 || mesInicio.length() == 0 || mesFin.length() == 0) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>Debe especificar fecha de inicio y fin</p>\"]");
        }

        if (condominioId == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No especificó el condominio</p>\"]");
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal(bo.getFechaFinal("01", mesFin, anioFin));

        List<Cargo> cargos = bo.getCargosPorCondominio(dateInicial, dateFinal, condominioId);
        if (cargos == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No se encontraron cargos con la búsqueda seleccionada</p>\"]");
            return SUCCESS_JSON;
        }
        Double totalcargos = 0D;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (Cargo a : cargos) {
            totalcargos += a.getMonto();
            
            getJsonResult().add("[\"" + a.getDepartamento().getCondominio().getNombre()
                    + "\", \"" + a.getDepartamento().getTorre().getNombre()
                    + "\", \"" + a.getDepartamento().getNombre()
                    + "\", \"" + a.getConcepto().getNombre()
                    + "\", \"" + UtilFile.dateToString(a.getFechaPeriodo(), "dd-MMMM-yyyy")
                    + "\", \"$" + df.format(a.getMonto())
                    + " \"]");
        }

        getJsonResult().add("[\""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \"Total:"
                + "\", \"$" + df.format(totalcargos)
                + " \"]");

        return SUCCESS_JSON;
    }
    
    
    public String reporteEstadisticaAbonos() {
        if (anioInicio == null || anioFin == null || mesInicio == null || mesFin == null
                || anioInicio.length() == 0 || anioFin.length() == 0 || mesInicio.length() == 0 || mesFin.length() == 0) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>Debe especificar fecha de inicio y fin</p>\"]");
        }

        if (condominioId == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No especificó el condominio</p>\"]");
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date dateInicial = bo.getFechaInicial("01", mesInicio, anioInicio);
        Date dateFinal = bo.getFechaFinal(bo.getFechaFinal("01", mesFin, anioFin));

        List<Abono> abonos = bo.getAbonosPorCondominio(dateInicial, dateFinal, condominioId);
        if (abonos == null) {
            getJsonResult().add("[\"\",\"\",\"\",\"\",\"\",\"<p style='text-align: center;'>No se encontraron abonos con la búsqueda seleccionada</p>\"]");
            return SUCCESS_JSON;
        }
        Double totalabonos = 0D;

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        for (Abono a : abonos) {
            totalabonos += a.getMonto();
            
            getJsonResult().add("[\"" + a.getDepartamento().getCondominio().getNombre()
                    + "\", \"" + a.getDepartamento().getTorre().getNombre()
                    + "\", \"" + a.getDepartamento().getNombre()
                    + "\", \"" + a.getConcepto().getNombre()
                    + "\", \"" + UtilFile.dateToString(a.getFechaPago(), "dd-MMMM-yyyy")
                    + "\", \"$" + df.format(a.getMonto())
                    + " \"]");
        }

        getJsonResult().add("[\""
                + "\", \""
                + "\", \""
                + "\", \""
                + "\", \"Total:"
                + "\", \"$" + df.format(totalabonos)
                + " \"]");

        return SUCCESS_JSON;
    }

    /**
     * Lista de departamentos por torre y condominio
     *
     * @return
     */
    public String getDepartamentos() {
        List<Departamento> lista = getDaos().getDepartamentoDao().findBy(pkTorre, pkCondominio);
        if (lista == null) {
        } else {
            for (Departamento d : lista) {
//                getJsonResult().add("[\"" + d.getId()
//                        + "\", \"" + d.getNombre()
//                        + "\", \"" + d.getTipoDepartamento().getNombre()
//                        + "\", \"<a class='fancybox fancybox.iframe'  href='/estadodecuenta/consulta/consultaEstadoDeCuenta.action?departamentoId=" + d.getId() + "'>"
//                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
//                        + "<i class='fa fa-star fa-stack-1x fa-inverse'></i> "
//                        + "</span>"
//                        + "</a>"
//                        + " \"]");
                getJsonResult().add("[\"" + d.getId()
                        + "\", \"" + d.getNombre()
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String getIdentificadoresCarga() {
        if (opcionCarga == null) {

        } else //Abonos
        {
            if (opcionCarga == 1) {
                List<String> ids = getDaos().getAbonoDao().getIdentificadoresCarga(condominioId);
                if (ids == null) {

                } else {
                    for (String a : ids) {
                        getJsonResult().add("[\"" + a
                                + "\", \"" + a
                                + "\"]");
                    }
                }
            } else if (opcionCarga == 0) {
                List<String> ids = getDaos().getCargoDao().getIdentificadoresCarga(condominioId);
                if (ids == null) {

                } else {
                    for (String a : ids) {
                        getJsonResult().add("[\"" + a
                                + "\", \"" + a
                                + "\"]");
                    }
                }
            }
        }

        return SUCCESS_JSON;
    }

    public String getMovimientosPorIdentificadorCarga() {
        if (opcionCarga == null || idCarga == null || idCarga.isEmpty()) {

        }
        //Abonos
        if (opcionCarga == 1) {
            List<Abono> abonos = getDaos().getAbonoDao().abonos(idCarga, condominioId);
            if (abonos == null) {

            } else {
                for (Abono a : abonos) {
                    getJsonResult().add("[\"" + a.getDepartamento().getCondominio().getNombre()
                            + "\", \"" + a.getDepartamento().getTorre().getNombre()
                            + "\", \"" + a.getDepartamento().getNombre()
                            + "\", \"" + a.getConcepto().getNombre()
                            + "\", \"" + UtilFile.dateToString(a.getFechaPago(), "yyyy-MM-dd")
                            + "\", \"$" + a.getMontoFormato()
                            + "\", \"<a href='#listado' onclick='eliminaAbono(" + a.getId() + ")'>"
                            + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                            + "<i class='fa fa-trash-o  fa-stack-1x fa-inverse'></i> "
                            + "</span>"
                            + "</a>"
                            + " \"]");
                }
            }
        } else if (opcionCarga == 0) {
            List<Cargo> cargos = getDaos().getCargoDao().cargos(idCarga, condominioId);
            if (cargos == null) {

            } else {
                for (Cargo a : cargos) {
                    getJsonResult().add("[\"" + a.getDepartamento().getCondominio().getNombre()
                            + "\", \"" + a.getDepartamento().getTorre().getNombre()
                            + "\", \"" + a.getDepartamento().getNombre()
                            + "\", \"" + a.getConcepto().getNombre()
                            + "\", \"" + UtilFile.dateToString(a.getFechaPeriodo(), "yyyy-MM-dd")
                            + "\", \"$" + a.getMontoFormato()
                            + "\", \"<a href='#listado' onclick='eliminaCargo(" + a.getId() + ")'>"
                            + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                            + "<i class='fa fa-trash-o  fa-stack-1x fa-inverse'></i> "
                            + "</span>"
                            + "</a>"
                            + " \"]");
                }
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaMovimientosPorIdentificadorCarga() {
        if (idCarga == null || idCarga.isEmpty()) {
            //NO SE REALIZA NINGUN MOVIMIENTO
            return SUCCESS_JSON;
        }

        //Abonos
        if (opcionCarga == 1) {
            try {
                AbonosBO bo = new AbonosBO(getDaos());
                bo.revertirAbonos(idCarga);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                CargosBO bo = new CargosBO(getDaos());
                bo.revertirCargos(idCarga);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return SUCCESS_JSON;
    }

    public String eliminaCargo() {
        if (idMovimiento == null) {

        } else {
            Cargo c = getDaos().getCargoDao().findById(idMovimiento);
            if (c != null) {
                getDaos().getCargoDao().delete(c);
            }
        }

        return SUCCESS_JSON;
    }

    public String eliminaAbono() {
        if (idMovimiento == null) {

        } else {
            Abono a = getDaos().getAbonoDao().findById(idMovimiento);
            if (a != null) {
                getDaos().getAbonoDao().delete(a);
            }
        }

        return SUCCESS_JSON;
    }

    public String getAnioInicio() {
        return anioInicio;
    }

    public void setAnioInicio(String anioInicio) {
        this.anioInicio = anioInicio;
    }

    public String getMesInicio() {
        return mesInicio;
    }

    public void setMesInicio(String mesInicio) {
        this.mesInicio = mesInicio;
    }

    public String getAnioFin() {
        return anioFin;
    }

    public void setAnioFin(String anioFin) {
        this.anioFin = anioFin;
    }

    public String getMesFin() {
        return mesFin;
    }

    public void setMesFin(String mesFin) {
        this.mesFin = mesFin;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public Integer getLinkDetail() {
        return linkDetail;
    }

    public void setLinkDetail(Integer linkDetail) {
        this.linkDetail = linkDetail;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

    public Long getPkTorre() {
        return pkTorre;
    }

    public void setPkTorre(Long pkTorre) {
        this.pkTorre = pkTorre;
    }

    public Long getPkCondominio() {
        return pkCondominio;
    }

    public void setPkCondominio(Long pkCondominio) {
        this.pkCondominio = pkCondominio;
    }

    public Long getOpcionCarga() {
        return opcionCarga;
    }

    public void setOpcionCarga(Long opcionCarga) {
        this.opcionCarga = opcionCarga;
    }

    public String getIdCarga() {
        return idCarga;
    }

    public void setIdCarga(String idCarga) {
        this.idCarga = idCarga;
    }

    public Long getIdMovimiento() {
        return idMovimiento;
    }

    public void setIdMovimiento(Long idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public Integer getTipoAbono() {
        return tipoAbono;
    }

    public void setTipoAbono(Integer tipoAbono) {
        this.tipoAbono = tipoAbono;
    }

}
