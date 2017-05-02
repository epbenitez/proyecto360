package com.allinone.actions.estadodecuenta;

import com.allinone.actions.ajax.JSONAjaxAction;
import com.allinone.business.AbonosBO;
import com.allinone.business.CargosBO;
import com.allinone.business.EstadoCuentaBO;
import com.allinone.domain.AvisoCobro;
import com.allinone.domain.ConfiguracionExcel;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.ConfiguracionCargaMasiva;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.CantLetras;
import com.allinone.util.UtilFile;
import com.opensymphony.xwork2.ActionContext;
import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class EstadoCuentaAction extends JSONAjaxAction implements MensajesEstadoCuenta {

    private DepartamentoUsuario deptoUsuario;
    private List<Condominio> condominios = new ArrayList<Condominio>();
    private Long condominioId;
    private String anioInicio;
    private String mesInicio;
    private List<Concepto> conceptos = new ArrayList<Concepto>();
    private Long conceptoId;
    private List<ConfiguracionExcel> configuracion;
    private Integer columnaFecha;
    private Integer columnaMonto;

    private File upload;
    private String uploadFileName;
    private String uploadContentType;

    private List<String> log = new LinkedList<String>();

    private String identificadorCarga;
    private String folioAvisoCobro;
    private String fechaAvisoCobro;
    private String fecha;

    private List<Abono> abonos = new ArrayList<Abono>();
    private String importeConLetra;
    private String sumatoria;

    private Boolean cargo;

    private List<Condominio> condominiolist = new ArrayList<Condominio>();
    private Long departamentoId;
    private AvisoCobro avisoCobro;

    public EstadoCuentaAction() {
        if (configuracion == null || configuracion.isEmpty()) {
            configuracion = new ArrayList<ConfiguracionExcel>();
        }
    }

    public String form() {

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        deptoUsuario = bo.getDepartamentoUsuario(u);

        return INPUT;
    }

    /**
     * Llena los combos de condominios y conceptos para dejarlos disponibles
     * para el formulario de cargos
     *
     * @return
     */
    public String cargos() {
        configuracion.clear();
        condominios = getDaos().getCondominioDao().findAll();
        conceptos = getDaos().getConceptoDao().findAll();

        return INPUT;
    }

    public String abonos() {
        return cargos();
    }

    /**
     * Agrega un objeto de configuración de acuerdo a lo que se especifica en el
     * formulario
     *
     * @return
     */
    public String configuracion() {

        if (cargo == null || condominioId == null || conceptoId == null || columnaFecha == null || columnaMonto == null) {
            getJsonResult().add("");
        } else {
            try {

                ConfiguracionCargaMasiva ccm = new ConfiguracionCargaMasiva();
                ccm.setCondominio(getDaos().getCondominioDao().findById(condominioId));
                ccm.setConcepto(getDaos().getConceptoDao().findById(conceptoId));
                ccm.setColumnaFecha(columnaFecha);
                ccm.setColumnaMonto(columnaMonto);
                ccm.setCargo(cargo);

                guardaConfiguracion(ccm);

            } catch (Exception e) {
                e.printStackTrace();
                return SUCCESS_JSON;
            }

            ConfiguracionExcel ce = new ConfiguracionExcel();
            Concepto concepto = getDaos().getConceptoDao().findById(conceptoId);
            ce.setConcepto(concepto);
            ce.setColumnaFecha(columnaFecha);
            ce.setColumnaMonto(columnaMonto);

            configuracion.add(ce);

            for (ConfiguracionExcel c : configuracion) {
                getJsonResult().add("[\"" + c.getConcepto().getNombre()
                        + "\", \"" + c.getColumnaFecha()
                        + "\", \"" + c.getColumnaMonto()
                        + " \"]");
            }

        }

        return SUCCESS_JSON;
    }

    private void guardaConfiguracion(ConfiguracionCargaMasiva ccm) {
        getDaos().getConfiguracionCargaMasivaDao().save(ccm);
    }

    /**
     * Limpia el arreglo que contiene la configuración de las columnas
     *
     * @return
     */
    public String limpiaConfiguracion() {
        if (cargo == null || condominioId == null) {
            getJsonResult().add("");
            return SUCCESS_JSON;
        } else {
            List<ConfiguracionCargaMasiva> lista = getDaos().getConfiguracionCargaMasivaDao().getConfiguraciones(condominioId, cargo);
            for (ConfiguracionCargaMasiva cm : lista) {
                getDaos().getConfiguracionCargaMasivaDao().delete(cm);
            }
            configuracion.clear();
            return SUCCESS_JSON;
        }
    }

    public String configuracionInicial() {

        if (cargo == null || condominioId == null) {
            getJsonResult().add("");
        } else {
            List<ConfiguracionCargaMasiva> lista = getDaos().getConfiguracionCargaMasivaDao().getConfiguraciones(condominioId, cargo);
            if (lista != null && !lista.isEmpty()) {
                for (ConfiguracionCargaMasiva cm : lista) {
                    ConfiguracionExcel ce = new ConfiguracionExcel();
                    ce.setConcepto(cm.getConcepto());
                    ce.setColumnaFecha(cm.getColumnaFecha());
                    ce.setColumnaMonto(cm.getColumnaMonto());
                    configuracion.add(ce);

                    getJsonResult().add("[\"" + cm.getConcepto().getNombre()
                            + "\", \"" + cm.getColumnaFecha()
                            + "\", \"" + cm.getColumnaMonto()
                            + " \"]");
                }
            }
        }

        return SUCCESS_JSON;
    }

    /**
     * Carga el archivo que contiene los cargos por departamento
     *
     * @return
     */
    public String cargaConfiguracion() {
        LOG.info(String.format("%s : fileUpload", getClass().getName()));
        CargosBO bo = new CargosBO(getDaos());
        identificadorCarga = bo.getIdentificador();

        if (getUpload() != null) {

            Condominio condominio = getDaos().getCondominioDao().findById(condominioId);

            // Excel 2003 Format
            if ("application/vnd.ms-excel".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2003 Format");
                LOG.debug("calling readXmlFile(File xls) method...");

                try {
                    //INVOCA PROCESA ARCHIVO
                    log = bo.readXlsFile(configuracion, condominio, identificadorCarga, new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema")); // + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Excel 2007 Format
            else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2007 Format");
                LOG.debug("calling readXmlxFile(File xlsx) method...");

                try {
                    //
                    log = bo.readXlsxFile(configuracion, condominio, identificadorCarga, new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema"));// + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Si no es el content type deseado
            else {
                addActionError(getText("carga.archivo.errorFmto"));
                return INPUT;
            }

        } else {
            addActionError(getText("carga.archivo.errorFile"));
            return INPUT;

        }
        return INPUT;
    }

    /**
     * Realiza la carga del archivo excel que contiene los abonos para los
     * departamentos especificados
     *
     * @return
     */
    public String cargaAbonosConfiguracion() {
        LOG.info(String.format("%s : fileUpload", getClass().getName()));
        AbonosBO bo = new AbonosBO(getDaos());
        identificadorCarga = bo.getIdentificador();

        if (getUpload() != null) {

            Condominio condominio = getDaos().getCondominioDao().findById(condominioId);

            // Excel 2003 Format
            if ("application/vnd.ms-excel".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2003 Format");
                LOG.debug("calling readXmlFile(File xls) method...");

                try {
                    //INVOCA PROCESA ARCHIVO
                    log = bo.readXlsFile(configuracion, condominio, identificadorCarga, new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }
                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema")); // + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Excel 2007 Format
            else if ("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet".equalsIgnoreCase(getUploadContentType())) {
                LOG.debug("Excel 2007 Format");
                LOG.debug("calling readXmlxFile(File xlsx) method...");

                try {
                    //
                    log = bo.readXlsxFile(configuracion, condominio, identificadorCarga, new FileInputStream(getUpload()));

                    for (String ce : log) {
                        System.out.println(":>:" + ce);
                    }

                } catch (NumberFormatException nfe) {
                    nfe.printStackTrace();
                    addActionError(getText("carga.archivo.errorFmto"));
                    return INPUT;
                } catch (java.lang.Exception le) {
                    addActionError(getText("carga.archivo.error.sistema"));// + hideError(le));
                    le.printStackTrace();
                    return INPUT;
                }
            } // Si no es el content type deseado
            else {
                addActionError(getText("carga.archivo.errorFmto"));
                return INPUT;
            }

        } else {
            addActionError(getText("carga.archivo.errorFile"));
            return INPUT;

        }
        return INPUT;
    }

    public String revertirCargos() {

        if (this.identificadorCarga == null || this.identificadorCarga.equals("")) {
            addActionError(getText("revertir.error"));
            return INPUT;
        }
        CargosBO bo = new CargosBO(getDaos());
        bo.revertirCargos(identificadorCarga);
        addActionMessage(getText("revertir.ok"));
        return INPUT;
    }

    public String revertirAbonos() {

        if (this.identificadorCarga == null || this.identificadorCarga.equals("")) {
            addActionError(getText("revertir.error"));
            return INPUT;
        }
        AbonosBO bo = new AbonosBO(getDaos());
        bo.revertirAbonos(identificadorCarga);
        addActionMessage(getText("revertir.ok"));
        return INPUT;
    }

    public String avisoCobro() {
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        deptoUsuario = bo.getDepartamentoUsuario(u);

        folioAvisoCobro = bo.formatoFolio(deptoUsuario.getDepartamento().getCondominio().getClave(), deptoUsuario.getDepartamento().getId(), deptoUsuario.getDepartamento().getCondominio().getId(), new Date());
        fechaAvisoCobro = UtilFile.dateToString(new Date(), "MMMM yyyy");
        
        //Esta lista debe regresar sólo un registro
        List<AvisoCobro> acList = getDaos().getDepartamentoUsuarioDao().encuentraDatosDepartamentoUsuario(deptoUsuario.getDepartamento().getTorre().getId(), deptoUsuario.getDepartamento().getCondominio().getId(),deptoUsuario.getDepartamento().getId());
        if(acList!=null && !acList.isEmpty()){
            avisoCobro = acList.get(0);
            avisoCobro = bo.getDatosFinancieros(avisoCobro);
        }
        
        
        return INPUT;
    }

    public String reciboPago() {
        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        deptoUsuario = bo.getDepartamentoUsuario(u);

        folioAvisoCobro = bo.formatoFolio(deptoUsuario.getDepartamento().getCondominio().getClave(), deptoUsuario.getDepartamento().getId(), deptoUsuario.getDepartamento().getCondominio().getId(), new Date());
        fechaAvisoCobro = UtilFile.dateToString(new Date(), "MMMM yyyy");
        fecha = UtilFile.dateToString(new Date(), "dd-MM-yyyy");

        abonos = bo.getAbonos(bo.getFechaInicial(new Date()), bo.getFechaFinal(new Date()), bo.getDepartamento(u).getId());
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);

        if (abonos == null) {
        } else {

            Double suma = 0d;

            for (Abono c : abonos) {
                suma += c.getMonto();
            }

            importeConLetra = CantLetras.convertNumberToLetter(suma);
            sumatoria = df.format(suma);
        }

        return INPUT;
    }

    /**
     * Formulario de búsqueda de departamento
     *
     * @return
     */
    public String busquedaDepartamento() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return INPUT;
    }

    /**
     * Consulta de estado de cuenta
     *
     * @return
     */
    public String consulta() {

        if (departamentoId == null || departamentoId.equals("")) {
            addActionError("No se ha proporcionado el departamento");
            return INPUT;
        }

        EstadoCuentaBO bo = new EstadoCuentaBO(getDaos());
        deptoUsuario = bo.getDepartamentoUsuario(departamentoId);

        return INPUT;
    }

    public String reporteAbonos() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return INPUT;
    }
    
    /**
     * Pantalla principal del reporte de Cargos Abonos
     * @return 
     */
    public String reporteEstadistica() {
        Usuario u = (Usuario) ActionContext.getContext().getSession().get("usuario");
        if (isAdministrator()) {
            condominiolist = getDaos().getCondominioDao().findAll();
        } else if (isPropietario()) {
            DepartamentoUsuario du = getDaos().getDepartamentoUsuarioDao().findByUserId(u.getId());
            Condominio condominio = du.getDepartamento().getCondominio();
            condominiolist.add(condominio);
        } else {
            condominiolist = getDaos().getCondominioDao().condominiosPorUsuario(u.getId());
        }
        return SUCCESS;
    }
    
    /**
     * Detalle por departamentos, de Cargos y Abonos
     * @return 
     */
    public String reporteEstadisticaDetalleDepto() {
        return SUCCESS;
    }
    
    public String reporteEstadisticaCargos() {
        return SUCCESS;
    }
    
    
    public String reporteEstadisticaAbonos() {
        return SUCCESS;
    }
    

    /**
     * Lista los identificadores de carga relacionados a cargos o abonos
     *
     * @return
     */
    public String cargasPrevias() {
        condominios = getDaos().getCondominioDao().findAll();
        return INPUT;
    }

    public DepartamentoUsuario getDeptoUsuario() {
        return deptoUsuario;
    }

    public void setDeptoUsuario(DepartamentoUsuario deptoUsuario) {
        this.deptoUsuario = deptoUsuario;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public List<Concepto> getConceptos() {
        return conceptos;
    }

    public void setConceptos(List<Concepto> conceptos) {
        this.conceptos = conceptos;
    }

    public Long getCondominioId() {
        return condominioId;
    }

    public void setCondominioId(Long condominioId) {
        this.condominioId = condominioId;
    }

    public Long getConceptoId() {
        return conceptoId;
    }

    public void setConceptoId(Long conceptoId) {
        this.conceptoId = conceptoId;
    }

    public List<ConfiguracionExcel> getConfiguracion() {
        return configuracion;
    }

    public void setConfiguracion(List<ConfiguracionExcel> configuracion) {
        this.configuracion = configuracion;
    }

    public Integer getColumnaFecha() {
        return columnaFecha;
    }

    public void setColumnaFecha(Integer columnaFecha) {
        this.columnaFecha = columnaFecha;
    }

    public Integer getColumnaMonto() {
        return columnaMonto;
    }

    public void setColumnaMonto(Integer columnaMonto) {
        this.columnaMonto = columnaMonto;
    }

    public File getUpload() {
        return upload;
    }

    public void setUpload(File upload) {
        this.upload = upload;
    }

    public String getUploadFileName() {
        return uploadFileName;
    }

    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    public String getUploadContentType() {
        return uploadContentType;
    }

    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    public List<String> getLog() {
        return log;
    }

    public void setLog(List<String> log) {
        this.log = log;
    }

    public String getIdentificadorCarga() {
        return identificadorCarga;
    }

    public void setIdentificadorCarga(String identificadorCarga) {
        this.identificadorCarga = identificadorCarga;
    }

    public String getFolioAvisoCobro() {
        return folioAvisoCobro;
    }

    public void setFolioAvisoCobro(String folioAvisoCobro) {
        this.folioAvisoCobro = folioAvisoCobro;
    }

    public String getFechaAvisoCobro() {
        return fechaAvisoCobro;
    }

    public void setFechaAvisoCobro(String fechaAvisoCobro) {
        this.fechaAvisoCobro = fechaAvisoCobro;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public List<Abono> getAbonos() {
        return abonos;
    }

    public void setAbonos(List<Abono> abonos) {
        this.abonos = abonos;
    }

    public String getImporteConLetra() {
        return importeConLetra;
    }

    public void setImporteConLetra(String importeConLetra) {
        this.importeConLetra = importeConLetra;
    }

    public String getSumatoria() {
        return sumatoria;
    }

    public void setSumatoria(String sumatoria) {
        this.sumatoria = sumatoria;
    }

    public Boolean getCargo() {
        return cargo;
    }

    public void setCargo(Boolean cargo) {
        this.cargo = cargo;
    }

    public List<Condominio> getCondominiolist() {
        return condominiolist;
    }

    public void setCondominiolist(List<Condominio> condominiolist) {
        this.condominiolist = condominiolist;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
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

    public AvisoCobro getAvisoCobro() {
        return avisoCobro;
    }

    public void setAvisoCobro(AvisoCobro avisoCobro) {
        this.avisoCobro = avisoCobro;
    }

}
