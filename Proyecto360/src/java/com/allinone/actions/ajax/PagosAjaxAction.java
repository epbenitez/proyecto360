package com.allinone.actions.ajax;

import static com.allinone.actions.ajax.JSONAjaxAction.SUCCESS_JSON;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.AbonoDatosPago;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class PagosAjaxAction extends JSONAjaxAction {

    private Long pkTorre;
    private Long pkCondominio;
    private Abono abono;
    private String fechaEmision;
    private String fechaPago;

    /**
     * Datos del pago *
     */
    private Long departamentoId;
    private List<Abono> distribucionPago = new ArrayList<Abono>();
    private Long conceptoId;
    private Double monto;

    private Long abonoDatosPagoId;

    public PagosAjaxAction() {
        distribucionPago = new ArrayList<Abono>();
    }

    public String getDepartamentos() {
        List<Departamento> lista = getDaos().getDepartamentoDao().findBy(pkTorre, pkCondominio);
        if (lista == null) {
        } else {
            for (Departamento d : lista) {
//                getJsonResult().add("[\"" + d.getId()
//                        + "\", \"" + d.getNombre()
//                        + "\", \"" + d.getTipoDepartamento().getNombre()
//                        + "\", \"<a class='fancybox fancybox.iframe'  href='/pagos/registroPagoPagos.action?departamentoId=" + d.getId() + "'>"
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

    public String guardaMonto() {
        System.out.println("abono: " + abono);
        if (abono.getDepartamento().getId() == null
                || abono.getDatosPago().getAbonoTipo().getId() == null
                || abono.getDatosPago().getBanco().getId() == null
                || this.getFechaEmision() == null
                || this.getFechaPago() == null
                || abono.getDatosPago().getNumero().isEmpty()
                || abono.getDatosPago().getTitular().isEmpty()
                || abono.getDatosPago().getMonto().isNaN()) {
            getJsonResult().add("[\"No se ha podido guardar el pago, debido a que no se envió la información completa.\"]");
            return SUCCESS_JSON;
        }

        if (distribucionPago == null || distribucionPago.isEmpty()) {
            getJsonResult().add("[\"No se ha podido guardar el pago, pues necesita indicar la distribución del pago.\"]");
            return SUCCESS_JSON;
        } else {
            //validamos que el montoTotal coincida con la suma de los montos de los conceptos por agregar:
            Double totalConceptos = 0D;
            for (Abono a : distribucionPago) {
                totalConceptos += a.getMonto();
            }
            Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");

            //Guardamos si el monto total coincide con la suma de los montos
            if (abono.getDatosPago().getMonto().compareTo(totalConceptos) == 0) {
                abono.getDatosPago().setFechaEmision(UtilFile.strToDate(fechaEmision, "dd/MM/yyyy"));
                abono.getDatosPago().setFechaPago(new Date());
                abono.getDatosPago().setUsuario(u);
                AbonoDatosPago datosPago = getDaos().getAbonoDatosPagoDao().save(abono.getDatosPago());
                for (Abono a : distribucionPago) {
                    a.setDatosPago(datosPago);
                    a.setIdentificadorcarga(getIdentificador());
                    a.setFechaPago(UtilFile.strToDate(fechaPago, "dd/MM/yyyy"));
                    getDaos().getAbonoDao().save(a);
                }
            } else {
                getJsonResult().add("[\"No se ha podido guardar el pago, pues no se ha distribuido en su totalidad el monto del pago.\"]");
                return SUCCESS_JSON;
            }
        }

        getJsonResult().add("[\"Se ha registrado el pago exitósamente.\"]");
        distribucionPago = new ArrayList<Abono>();
        return SUCCESS_JSON;
    }

    public String getIdentificador() {
        Date d = new Date();
        return "M" + UtilFile.dateToString(d, "yyyyMMddhhmmss");
    }

    public String distribuyeMonto() {
        if (departamentoId == null || departamentoId.toString().isEmpty()
                || conceptoId == null || conceptoId.toString().isEmpty()
                || monto == null || monto.toString().isEmpty()
                || abono.getDatosPago().getMonto() == null || abono.getDatosPago().getMonto().toString().isEmpty()) {
            getJsonResult().add("[\"No se ha podido agregar este concepto, pues no se proporcionó la información necesaria.\"]");
            return SUCCESS_JSON;
        }

        //VERIFICAMOS QUE LA SUMATORIA NO EXCEDA EL MONTO TOTAL DEL PAGO
        Double sumatoriaMontoTotal = 0D;
        if (distribucionPago != null && !distribucionPago.isEmpty()) {

            for (Abono a : distribucionPago) {
                sumatoriaMontoTotal += a.getMonto();
            }
            sumatoriaMontoTotal += monto;
        }

        if (sumatoriaMontoTotal > abono.getDatosPago().getMonto()) {
            getJsonResult().add("[\"No se pudo agregar este concepto, debido a que se sobrepasa el monto total del pago por registrar.\"]");
            return SUCCESS_JSON;
        }

        Concepto concepto = getDaos().getConceptoDao().findById(conceptoId);
        if (concepto == null) {
            getJsonResult().add("[\"No se ha encontrado el concepto especificado, por favor, verifique.\"]");
            return SUCCESS_JSON;
        }
        Departamento departamento = getDaos().getDepartamentoDao().findById(departamentoId);
        if (departamento == null) {
            getJsonResult().add("[\"No se ha encontrado el departamento especificado, por favor, verifique.\"]");
            return SUCCESS_JSON;
        }

        Abono abono = new Abono();
        abono.setConcepto(concepto);
        abono.setMonto(monto);
        abono.setDepartamento(departamento);
        abono.setFechaAdmon(new Date());
        abono.setFechaPago(new Date());

        distribucionPago.add(abono);

        getJsonResult().add("[\"Se ha agregado el concepto exitósamente.\"]");
        return SUCCESS_JSON;

    }

    public String getDistribucionMonto() {
        if (distribucionPago != null && !distribucionPago.isEmpty()) {
            for (Abono a : distribucionPago) {
                getJsonResult().add("[\"" + a.getConcepto().getNombre()
                        + "\", \"" + a.getMonto()
                        + "\", \"<a class='fancybox fancybox.iframe'  href='#distribucionDiv' onclick='eliminaConcepto(" + a.getConcepto().getId() + "," + a.getMonto() + ")'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaConceptoMonto() {
        if (conceptoId == null || conceptoId.toString().isEmpty()
                || monto == null || monto.toString().isEmpty()) {
            getJsonResult().add("[\"No se ha podido eliminar este concepto, pues no se proporcionó la información necesaria.\"]");
            return SUCCESS_JSON;
        }

        if (distribucionPago != null && !distribucionPago.isEmpty()) {
            for (Abono a : distribucionPago) {
                if (monto.compareTo(a.getMonto()) == 0 && a.getConcepto().getId().compareTo(conceptoId) == 0) {
                    distribucionPago.remove(a);
                    getJsonResult().add("[\"Se ha eliminado el concepto exitósamente.\"]");
                    break;
                }
            }
        }

        getJsonResult().add("[\"No se ha encontrado el concepto indicado.\"]");
        return SUCCESS_JSON;
    }

    public String validaCambioMonto() {
        if (monto == null || monto.toString().isEmpty()) {
            getJsonResult().add("[\"No se ha podido realizar esta validación, pues no se proporcionó la información necesaria.\"]");
            return SUCCESS_JSON;
        }

        Double totalConceptos = 0D;
        if (distribucionPago != null && !distribucionPago.isEmpty()) {
            for (Abono a : distribucionPago) {
                totalConceptos += a.getMonto();
            }
        }

        if (monto < totalConceptos) {
            getJsonResult().add("[\"" + totalConceptos + "\"]");
        } else {
            getJsonResult().add("[\"OK\"]");
        }

        return SUCCESS_JSON;
    }

    public String administra() {
        if (pkCondominio == null || pkCondominio.toString().isEmpty()) {
            return ERROR_JSON;
        }
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Date fechaInicial = bo.getFechaInicial(new Date());
        Date fechaFinal = bo.getFechaFinal(new Date());
        

        List<AbonoDatosPago> adpList = getDaos().getAbonoDatosPagoDao().abonosPorCondominioTorreDepartamento(pkCondominio,pkTorre,departamentoId,fechaInicial,fechaFinal);
        if (adpList != null && !adpList.isEmpty()) {
            for (AbonoDatosPago adp : adpList) {
                getJsonResult().add("[\""+ adp.getDepartamento().getNombre()
                        + "\", \"" + adp.getDepartamento().getTorre().getNombre()
                        + "\", \"" + adp.getNumero()
                        + "\", \"" + adp.getTitular()
                        + "\", \"" + UtilFile.dateToString(adp.getFechaEmision(), "yyyy-MM-dd")
                        + "\", \"" + UtilFile.dateToString(adp.getFechaPago(), "yyyy-MM-dd")
                        + "\", \"" + adp.getBanco().getNombre()
                        + "\", \"" + adp.getAbonoTipo().getNombre()
                        + "\", \"<a class='fancybox fancybox.iframe'  href='/pagos/desglosePagos.action?abonoDatosPagoId=" + adp.getId() + "'>$" + adp.getMonto() + "</a>"
                        + "\", \"" + adp.getUsuario().getUsuario()
                        + "\", \"<a href='#listado' onclick='eliminaAbonoDatosPago(" + adp.getId() + ")'>"
                        + "<span class='fa-stack'><i class='fa fa-square fa-stack-2x'></i> "
                        + "<i class='fa fa-trash-o fa-stack-1x fa-inverse'></i> "
                        + "</span>"
                        + "</a>"
                        + " \"]");
            }
        }
        return SUCCESS_JSON;
    }

    public String eliminaAbonoDatos() {
        if (abonoDatosPagoId == null || abonoDatosPagoId.toString().isEmpty()) {
            return ERROR_JSON;
        }

        AbonoDatosPago adp = getDaos().getAbonoDatosPagoDao().findById(abonoDatosPagoId);
        if (adp != null) {
            List<Abono> abonos = getDaos().getAbonoDao().abonos(abonoDatosPagoId);
            if (abonos != null) {
                for (Abono a : abonos) {
                    getDaos().getAbonoDao().delete(a);
                }
            }
        }
        
        getDaos().getAbonoDatosPagoDao().delete(adp);
                
        return SUCCESS_JSON;
    }
    
    public String desglose(){
        if (abonoDatosPagoId == null || abonoDatosPagoId.toString().isEmpty()) {
            return ERROR_JSON;
        }
        
        AbonoDatosPago adp = getDaos().getAbonoDatosPagoDao().findById(abonoDatosPagoId);
        if (adp != null) {
            List<Abono> abonos = getDaos().getAbonoDao().abonos(abonoDatosPagoId);
            if (abonos != null) {
                for (Abono a : abonos) {
                    getJsonResult().add("[\"" + a.getConcepto().getNombre()
                        + "\", \"$" + a.getMontoFormato()
                        + " \"]");
                }
            }
        }
        return SUCCESS_JSON;
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

    public Abono getAbono() {
        return abono;
    }

    public void setAbono(Abono abono) {
        this.abono = abono;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(String fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public String getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(String fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public List<Abono> getDistribucionPago() {
        return distribucionPago;
    }

    public void setDistribucionPago(List<Abono> distribucionPago) {
        this.distribucionPago = distribucionPago;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public Double getMonto() {
        return monto;
    }

    public void setMonto(Double monto) {
        this.monto = monto;
    }

    public Long getAbonoDatosPagoId() {
        return abonoDatosPagoId;
    }

    public void setAbonoDatosPagoId(Long abonoDatosPagoId) {
        this.abonoDatosPagoId = abonoDatosPagoId;
    }

}
