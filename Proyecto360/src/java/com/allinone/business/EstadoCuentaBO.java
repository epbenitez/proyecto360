package com.allinone.business;

import com.allinone.domain.AvisoCobro;
import com.allinone.domain.EstadoCuenta;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.Cargo;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Usuario;
import com.allinone.service.Service;
import com.allinone.util.UtilFile;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Patricia Benitez
 */
public class EstadoCuentaBO {

    private Service service;

    public EstadoCuentaBO(Service s) {
        this.service = s;
    }

    public String formatoFolio(String claveCondominio, Long departamentoId, Long condominioId, Date d) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyMM");
        NumberFormat formatter = new DecimalFormat("00");

        return claveCondominio + dateFormat.format(d) + formatter.format(condominioId) + formatter.format(departamentoId);
    }

    public Departamento getDepartamento(Usuario u) {
        DepartamentoUsuario du = service.getDepartamentoUsuarioDao().findByUserId(u.getId());
        return du == null ? null : du.getDepartamento();
    }

    public Departamento getDepartamento(Long id) {
        DepartamentoUsuario du = service.getDepartamentoUsuarioDao().findByDepartamentoId(id);
        return du == null ? null : du.getDepartamento();
    }

    public DepartamentoUsuario getDepartamentoUsuario(Usuario u) {
        DepartamentoUsuario du = service.getDepartamentoUsuarioDao().findByUserId(u.getId());
        return du;
    }

    public DepartamentoUsuario getDepartamentoUsuario(Long id) {
        DepartamentoUsuario du = service.getDepartamentoUsuarioDao().findByDepartamentoId(id);
        return du;
    }

    /**
     * Obtiene los cargos/abonos del periodo seleccionado
     *
     * @param fechaInicial
     * @param fechaFinal
     * @param departamentoId
     * @return
     */
    public EstadoCuenta getCargosAbonosPeriodo(Date fechaInicial, Date fechaFinal, Long departamentoId) {
        EstadoCuenta ec = new EstadoCuenta();
        ec = service.getCargoDao().cargosPeriodo(ec, fechaInicial, fechaFinal, departamentoId);
        ec = service.getCargoDao().abonosPeriodo(ec, fechaInicial, fechaFinal, departamentoId);

        return ec;
    }

    public EstadoCuenta getCargosAbonosAnteriores(Date fechaInicial, Long departamentoId) {
        EstadoCuenta ec = new EstadoCuenta();
        ec = service.getCargoDao().cargosAnteriores(ec, fechaInicial, departamentoId);
        ec = service.getCargoDao().abonosAnteriores(ec, fechaInicial, departamentoId);

        return ec;
    }

    public Date getFechaInicial(String dia, String mes, String anio) {
        return UtilFile.strToDate(dia + "/" + mes + "/" + anio, "dd/MM/yyyy");
    }

    public Date getFechaInicial(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;

        return UtilFile.strToDate("01/" + month + "/" + year, "dd/MM/yyyy");
    }

    public Date getFechaFinal(String dia, String mes, String anio) {
        Integer mesIndex0 = new Integer(mes) - 1;
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, new Integer(anio));
        cal.set(Calendar.MONTH, mesIndex0); // FEBRUARY
        int maxDias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date d = UtilFile.strToDate(maxDias + "/" + mes + "/" + anio, "dd/MM/yyyy");
        return d;
    }

    public Date getFechaFinal(Date d) {
        System.out.println("Date:" + d);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int maxDias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        System.out.println("Date String:" + maxDias + "/" + month + "/" + year);
        Date date = UtilFile.strToDate(maxDias + "/" + month + "/" + year, "dd/MM/yyyy");
        System.out.println("Fecha Final:" + date);
        return date;
    }

    public AvisoCobro getDatosFinancieros(AvisoCobro ac) {

        List<Cargo> cargos = getCargos(getFechaInicial(new Date()), getFechaFinal(new Date()), ac.getId());
        ac.setCargos(cargos);

        //Determinamos la suma de descuentos dependiendo del cargo (sólo si es Mantenimiento)
        if (cargos != null && !cargos.isEmpty()) {
            for (Cargo c : cargos) {
                if (c.getConcepto().getId() == 1) {
                    if (ac.getMontoDescuento() == null) {
                        ac.setMontoDescuento(300D);
                    } else {
                        ac.setMontoDescuento(ac.getMontoDescuento() + 300D);
                    }
                }
            }
        }

        //Buscamos el saldo inicial
        EstadoCuenta anteriores = getCargosAbonosAnteriores(getFechaInicial(new Date()), ac.getId());
        EstadoCuenta periodo = getCargosAbonosPeriodo(getFechaInicial(new Date()), getFechaFinal(new Date()), ac.getId());

        BigDecimal cargosAnteriores = anteriores == null || anteriores.getCargos() == null ? new BigDecimal(0) : anteriores.getCargos();
        BigDecimal abonosAnteriores = anteriores == null || anteriores.getAbonos() == null ? new BigDecimal(0) : anteriores.getAbonos();
        BigDecimal cargosPeriodo = periodo == null || periodo.getCargos() == null ? new BigDecimal(0) : periodo.getCargos();
        BigDecimal abonosPeriodo = periodo == null || periodo.getAbonos() == null ? new BigDecimal(0) : periodo.getAbonos();

        ac.setCargosAnteriores(cargosAnteriores);
        ac.setAbonosAnteriores(abonosAnteriores);
        ac.setCargosPeriodo(cargosPeriodo);
        ac.setAbonosPeriodo(abonosPeriodo);

        ac.setFolioAvisoCobro(formatoFolio(ac.getClaveCondominio(), ac.getId(), ac.getIdCondominio(), new Date()));
        return ac;
    }

    public List<Cargo> getCargos(Date fechaInicial, Date fechaFinal, Long departamentoId) {
        List<Cargo> lista = service.getCargoDao().cargosPeriodoDetalle(fechaInicial, fechaFinal, departamentoId);
        return lista == null || lista.size() == 0 ? null : lista;
    }

    public List<Abono> getAbonos(Date fechaInicial, Date fechaFinal, Long departamentoId) {
        List<Abono> lista = service.getAbonoDao().abonosPeriodoDetalle(fechaInicial, fechaFinal, departamentoId);
        return lista == null || lista.size() == 0 ? null : lista;
    }

    public List<Abono> getAbonosPorCondominio(Date finicial, Date ffinal, Long condominioId, Integer tipoAbono) {
        return service.getAbonoDao().abonosPorCondominio(finicial, ffinal, condominioId, tipoAbono);
    }

    public List<Abono> getAbonosPorCondominio(Date finicial, Date ffinal, Long condominioId) {
        return service.getAbonoDao().abonosPorCondominio(finicial, ffinal, condominioId);
    }

    public List<Cargo> getCargosPorCondominio(Date finicial, Date ffinal, Long condominioId) {
        return service.getCargoDao().cargosPorCondominio(finicial, ffinal, condominioId);
    }

    public List<EstadoCuenta> getSaldos(Date finicial, Date ffinal, Long condominioId) {
        return service.getCargoDao().saldos(finicial, ffinal, condominioId);
    }

    public List<EstadoCuenta> getSaldosDetalleDepto(Date finicial, Date ffinal, Long condominioId) {
        return service.getCargoDao().saldosDetalleDepartamento(finicial, ffinal, condominioId);
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
