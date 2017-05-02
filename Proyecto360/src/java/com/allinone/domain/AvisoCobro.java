package com.allinone.domain;

import com.allinone.persistence.model.Cargo;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciabenitez
 */
public class AvisoCobro {

    private Long id;
    private String departamento;
    private String condominio;
    private String torre;
    private String usuario;
    private String correo;

    private List<Cargo> cargos = new ArrayList<Cargo>();
    private BigDecimal cargosAnteriores;
    private BigDecimal abonosAnteriores;
    private BigDecimal cargosPeriodo;
    private BigDecimal abonosPeriodo;
    private String saldoInicial;
    private String saldoFinal;
    private String saldoFinalConDescuento;
    private String folioAvisoCobro;
    
    private String claveCondominio;
    private Long idCondominio;
    private String direccionCondominio;
    private String banco;
    private String beneficiario;
    private String cuenta;
    private String sucursal;
    private String clabe;
    private String referencia;
    private String contactoMail;
    
    private Double montoDescuento; //Suma de descuentos del mes si se realiza el pago antes del 10
    

    public AvisoCobro() {
    }

    public AvisoCobro(Long id, String departamento, String condominio, String torre, String usuario) {
        this.id = id;
        this.departamento = departamento;
        this.condominio = condominio;
        this.torre = torre;
        this.usuario = usuario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCondominio() {
        return condominio;
    }

    public void setCondominio(String condominio) {
        this.condominio = condominio;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<Cargo> getCargos() {
        return cargos;
    }

    public void setCargos(List<Cargo> cargos) {
        this.cargos = cargos;
    }

    public BigDecimal getCargosAnteriores() {
        return cargosAnteriores;
    }

    public void setCargosAnteriores(BigDecimal cargosAnteriores) {
        this.cargosAnteriores = cargosAnteriores;
    }

    public BigDecimal getAbonosAnteriores() {
        return abonosAnteriores;
    }

    public void setAbonosAnteriores(BigDecimal abonosAnteriores) {
        this.abonosAnteriores = abonosAnteriores;
    }

    public String getSaldoInicial() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (cargosAnteriores != null && abonosAnteriores != null) {
            return df.format(cargosAnteriores.subtract(abonosAnteriores));
        }
        return "0";
//        return saldoInicial;
    }

    public void setSaldoInicial(String saldoInicial) {
        this.saldoInicial = saldoInicial;
    }

    public BigDecimal getCargosPeriodo() {
        return cargosPeriodo;
    }

    public void setCargosPeriodo(BigDecimal cargosPeriodo) {
        this.cargosPeriodo = cargosPeriodo;
    }

    public BigDecimal getAbonosPeriodo() {
        return abonosPeriodo;
    }

    public void setAbonosPeriodo(BigDecimal abonosPeriodo) {
        this.abonosPeriodo = abonosPeriodo;
    }

    public String getSaldoFinal() {
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        if (cargosAnteriores != null && abonosAnteriores != null && cargosPeriodo!=null && abonosPeriodo!=null) {
            return df.format((cargosAnteriores.add(cargosPeriodo)).subtract(abonosAnteriores.add(abonosPeriodo)));
        }
        return "0";
    }

    public void setSaldoFinal(String saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public String getFolioAvisoCobro() {
        return folioAvisoCobro;
    }

    public void setFolioAvisoCobro(String folioAvisoCobro) {
        this.folioAvisoCobro = folioAvisoCobro;
    }

    public String getClaveCondominio() {
        return claveCondominio;
    }

    public void setClaveCondominio(String claveCondominio) {
        this.claveCondominio = claveCondominio;
    }

    public Long getIdCondominio() {
        return idCondominio;
    }

    public void setIdCondominio(Long idCondominio) {
        this.idCondominio = idCondominio;
    }

    public String getDireccionCondominio() {
        return direccionCondominio;
    }

    public void setDireccionCondominio(String direccionCondominio) {
        this.direccionCondominio = direccionCondominio;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getBeneficiario() {
        return beneficiario;
    }

    public void setBeneficiario(String beneficiario) {
        this.beneficiario = beneficiario;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getClabe() {
        return clabe;
    }

    public void setClabe(String clabe) {
        this.clabe = clabe;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getContactoMail() {
        return contactoMail;
    }

    public void setContactoMail(String contactoMail) {
        this.contactoMail = contactoMail;
    }

    public Double getMontoDescuento() {
        return montoDescuento;
    }

    public void setMontoDescuento(Double montoDescuento) {
        this.montoDescuento = montoDescuento;
    }

    public String getSaldoFinalConDescuento() {
        Double sFinal = getSaldoFinal()==null?0D:new Double(getSaldoFinal().replaceAll(",", ""));
        Double descuento = getMontoDescuento()==null?0D:getMontoDescuento();
        Double total = sFinal - descuento;
        
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        
        return df.format(total);
    }

    public void setSaldoFinalConDescuento(String saldoFinalConDescuento) {
        this.saldoFinalConDescuento = saldoFinalConDescuento;
    }

    
}
