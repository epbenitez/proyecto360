package com.allinone.persistence.dao.jpa;

import com.allinone.domain.EstadoCuenta;
import com.allinone.persistence.dao.CargoDao;
import com.allinone.persistence.model.Cargo;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Torre;
import com.allinone.util.UtilFile;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Query;

/**
 *
 * @author Patricia Benitez
 */
public class CargoJpaDao extends JpaDaoBase<Cargo, Long> implements CargoDao {

    public CargoJpaDao() {
        super(Cargo.class);
    }

    @Override
    public EstadoCuenta cargosPeriodo(EstadoCuenta ec, Date fechaInicial, Date fechaFinal, Long departamentoId) {
        String sql = "select   c.fechaperiodo, sum(c.monto) cargosMes "
                + "from ent_cargos c "
                + "where c.fechaperiodo between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "' "
                + "and c.departamento_id = " + departamentoId;

        List<Object[]> lista = executeNativeQuery(sql);
        for (Object[] res : lista) {
            if (res[0] == null && res[0] == null && res[0] == null) {
                break;
            }
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(UtilFile.strToDate(res[0].toString(), "yyyy-MM-dd"));
            BigDecimal bd = new BigDecimal(res[1].toString());
            ec.setCargos(bd);

        }

        if (lista.isEmpty()) {
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(fechaInicial);
            BigDecimal bd = new BigDecimal(0);
            ec.setCargos(bd);
        }
        return ec;
    }

    @Override
    public EstadoCuenta abonosPeriodo(EstadoCuenta ec, Date fechaInicial, Date fechaFinal, Long departamentoId) {
        String sql = "select   a.fechapago, sum(a.monto) abonosMes "
                + "from ent_abonos a "
                + "where a.fechapago between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "' "
                + " and  a.fechaAdmon is null and a.departamento_id =  " + departamentoId;

        List<Object[]> lista = executeNativeQuery(sql);
        for (Object[] res : lista) {
            if (res[0] == null) {
                break;
            }
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(UtilFile.strToDate(res[0].toString(), "yyyy-MM-dd"));
            BigDecimal bd = new BigDecimal(res[1].toString());
            ec.setAbonos(bd);

        }

        if (lista.isEmpty()) {
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(fechaInicial);
            BigDecimal bd = new BigDecimal(0);
            ec.setAbonos(bd);
        }
        return ec;
    }

    @Override
    public EstadoCuenta cargosAnteriores(EstadoCuenta ec, Date fechaInicial, Long departamentoId) {
        if (departamentoId == null) {
            return null;
        }
        String sql = "select   c.fechaperiodo,  "
                + "sum(c.monto) cargosAnteriores  "
                + "from ent_cargos c  "
                + "where c.fechaperiodo < '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' "
                + "and c.departamento_id =  " + departamentoId;

        List<Object[]> lista = executeNativeQuery(sql);
        for (Object[] res : lista) {
            if (res[0] == null && res[0] == null && res[0] == null) {
                break;
            }
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(UtilFile.strToDate(res[0].toString(), "yyyy-MM-dd"));
            BigDecimal bd = new BigDecimal(res[1].toString());
            ec.setCargos(bd);
        }

        if (lista.isEmpty()) {
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(fechaInicial);
            BigDecimal bd = new BigDecimal(0);
            ec.setCargos(bd);
        }
        return ec;
    }

    @Override
    public EstadoCuenta abonosAnteriores(EstadoCuenta ec, Date fechaInicial, Long departamentoId) {
        if (departamentoId == null) {
            return null;
        }
        String sql = "select   a.fechapago,  "
                + "sum(a.monto) abonosAnteriores  "
                + "from ent_abonos a  "
                + "where a.fechapago < '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' "
                + " and  a.fechaAdmon is null "
                + " and a.departamento_id =" + departamentoId;

        List<Object[]> lista = executeNativeQuery(sql);
        for (Object[] res : lista) {
            if (res[0] == null) {
                break;
            }
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(UtilFile.strToDate(res[0].toString(), "yyyy-MM-dd"));
            BigDecimal bd = new BigDecimal(res[1].toString());
            ec.setAbonos(bd);

        }

        if (lista.isEmpty()) {
            ec = ec == null ? new EstadoCuenta() : ec;
            ec.setFechaPeriodo(fechaInicial);
            BigDecimal bd = new BigDecimal(0);
            ec.setAbonos(bd);
        }
        return ec;
    }

    /**
     * Elimina los registros que coincidan con el identificador especificado
     *
     * @param identificador
     */
    @Override
    public void elimina(String identificador) {
        String jpql = "delete from Cargo where identificadorcarga like ?1";
        Query query = getEntityManager().createQuery(jpql);
        query.setParameter(1, identificador);
        query.executeUpdate();

    }

    @Override
    public List<Cargo> cargosPeriodoDetalle(Date fechaInicial, Date fechaFinal, Long departamentoId) {
        String jpql = "select   c  from Cargo c "
                + "where c.fechaPeriodo between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + " and c.departamento.id = ?1";

        List<Cargo> cargos = executeQuery(jpql, departamentoId);

        return cargos;
    }
    
    @Override
    public List<Cargo> cargosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId) {
        String jpql = "select   c  from Cargo c "
                + "where c.fechaPeriodo between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + " and c.departamento.condominio.id = ?1";

        List<Cargo> cargos = executeQuery(jpql, condominioId);

        return cargos;
    }

    @Override
    public List<String> getIdentificadoresCarga(Long condominioId) {
        String sql = "SELECT distinct(a.identificadorcarga),1 FROM ent_cargos a"
                + " inner join ent_departamento d on d.ID = a.DEPARTAMENTO_ID "
                + " inner join ent_condominio c on c.id = d.condominio_id "
                + " where d.CONDOMINIO_ID =  " + condominioId;
        List<Object[]> lista = executeNativeQuery(sql);
        List<String> identificadores = new ArrayList<String>();
        for (Object[] res : lista) {
            if (res[0] == null) {
                break;
            }
            String s = res[0].toString();
            identificadores.add(s);
        }

        return identificadores;
    }

    @Override
    public List<Cargo> cargos(String identificadorcarga, Long condominioId) {
        String sql = "SELECT  a.id, a.fechaperiodo,a.monto, con.nombre, d.nombre,t.nombre, c.nombre"
                + " FROM ent_cargos a  "
                + " inner join ent_departamento d on d.ID = a.DEPARTAMENTO_ID "
                + " inner join ent_condominio c on c.id = d.condominio_id "
                + " inner join ent_torre t on t.id = d.torre_id "
                + " inner join cat_conceptos con on con.id = a.concepto_id "
                + " WHERE a.IDENTIFICADORCARGA = '" + identificadorcarga + "' "
                + " AND d.CONDOMINIO_ID =  " + condominioId;

        List<Cargo> listaCargos = new ArrayList<Cargo>();
        List<Object[]> cargos = executeNativeQuery(sql);
        if (cargos == null || cargos.isEmpty()) {
            return null;
        } else {
            for (Object[] o : cargos) {
                Cargo a = new Cargo();
                a.setId(new Long(o[0].toString()));
                a.setFechaPeriodo(o[1] == null ? new Date() : UtilFile.strToDate(o[1].toString(), "yyyy-MM-dd"));
                a.setMonto(Double.valueOf(o[2].toString()));
                Concepto con = new Concepto();
                con.setNombre(o[3].toString());
                a.setConcepto(con);
                Departamento d = new Departamento();
                d.setNombre(o[4].toString());
                Torre t = new Torre();
                t.setNombre(o[5].toString());
                d.setTorre(t);
                a.setDepartamento(d);
                Condominio c = new Condominio();
                c.setNombre(o[3].toString());
                d.setCondominio(c);

                listaCargos.add(a);

            }
        }

        return listaCargos;
    }

    public List<EstadoCuenta> saldos(Date fechaInicial, Date fechaFinal, Long condominioId) {
        String sql = "(select  YEAR(c.fechaperiodo), MONTH(c.fechaperiodo),sum(c.monto) cargo, null abono from ent_cargos c \n"
                + " inner join ent_departamento d on d.id = c.departamento_id\n"
                + " where \n"
                + " c.fechaperiodo between '"+UtilFile.dateToString(fechaInicial, "yyyy-MM-dd")+"' and '"+UtilFile.dateToString(fechaFinal, "yyyy-MM-dd")+"'\n"
                + "  and d.condominio_id = " + condominioId + "\n"
                + " GROUP BY YEAR(c.fechaperiodo), MONTH(c.fechaperiodo) DESC)\n"
                + " union \n"
                + " (select YEAR(a.fechapago), MONTH(a.fechapago),null cargo, sum(a.monto) abono from ent_abonos a \n"
                + " inner join ent_departamento d on d.id = a.departamento_id\n"
                + " where \n"
                + " a.`fechapago` between '"+UtilFile.dateToString(fechaInicial, "yyyy-MM-dd")+"' and '"+UtilFile.dateToString(fechaFinal, "yyyy-MM-dd")+"'\n"
                + "  and d.condominio_id = " + condominioId + "\n"
                + " GROUP BY YEAR(a.fechapago), MONTH(a.fechapago) DESC)\n"
                + " order by 2,3\n";

        List<Object[]> lista = executeNativeQuery(sql);
        HashMap<String, EstadoCuenta> hm = new HashMap<String, EstadoCuenta>();

        for (Object[] res : lista) {
            if (res[0] == null) {
                break;
            }
            EstadoCuenta ec = new EstadoCuenta();
            String fecha = res[0].toString() + "-" + res[1].toString() + "-01";

            if (hm.containsKey(fecha)) {
                ec = hm.get(fecha);
            } else {
                hm.put(fecha, ec);
            }

            ec.setFechaPeriodo(UtilFile.strToDate(fecha, "yyyy-MM-dd"));
            BigDecimal cargo = new BigDecimal(BigInteger.ZERO);
            BigDecimal abono = new BigDecimal(BigInteger.ZERO);
            if (res[2] == null) {
                //COMO CARGO VIENE EN NULL, SE TRATA DE UN ABONO
                abono = res[3] == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(res[3].toString());
                ec.setAbonos(abono);
                if (ec.getCargos() == null) {
                    ec.setCargos(cargo);
                }
                ec.setSaldo(ec.getCargos().subtract(abono));
            }
            if (res[3] == null) {
                //COMO ABONO VIENE EN NULL, SE TRATA DE UN CARGO
                cargo = res[2] == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(res[2].toString());
                ec.setCargos(cargo);
                if (ec.getAbonos() == null) {
                    ec.setAbonos(abono);
                }
                ec.setSaldo(cargo.subtract(ec.getAbonos()));
            }
        }

        List<EstadoCuenta> saldos = new ArrayList<EstadoCuenta>();
        for (Map.Entry<String, EstadoCuenta> entry : hm.entrySet()) {
//            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
            saldos.add(entry.getValue());
        }
        Collections.sort(saldos);
        for (EstadoCuenta e : saldos) {
            System.out.println(e);
        }
        return saldos;
    }

    public List<EstadoCuenta> saldosDetalleDepartamento(Date fechaInicial, Date fechaFinal, Long condominioId) {
        String sql = "(select  YEAR(c.fechaperiodo), MONTH(c.fechaperiodo),sum(c.monto) cargo, null abono, c.departamento_id, d.nombre, t.id, t.nombre, co.id, co.nombre from ent_cargos c \n"
                + " inner join ent_departamento d on d.id = c.departamento_id\n"
                + " inner join ent_torre t on t.id = d.torre_id\n"
                + " inner join ent_condominio co on co.id = d.condominio_id"
                + " where \n"
                + " c.fechaperiodo between '"+UtilFile.dateToString(fechaInicial, "yyyy-MM-dd")+"' and '"+UtilFile.dateToString(fechaFinal, "yyyy-MM-dd")+"'\n"
                + "  and d.condominio_id = " + condominioId + "\n"
                + " GROUP BY YEAR(c.fechaperiodo), MONTH(c.fechaperiodo), c.departamento_id DESC)\n"
                + " union \n"
                + " (select YEAR(a.fechapago), MONTH(a.fechapago),null cargo, sum(a.monto) abono, a.departamento_id, d.nombre, t.id, t.nombre, co.id, co.nombre from ent_abonos a \n"
                + " inner join ent_departamento d on d.id = a.departamento_id\n"
                + " inner join ent_torre t on t.id = d.torre_id\n"
                + " inner join ent_condominio co on co.id = d.condominio_id"
                + " where \n"
                + " a.`fechapago` between '"+UtilFile.dateToString(fechaInicial, "yyyy-MM-dd")+"' and '"+UtilFile.dateToString(fechaFinal, "yyyy-MM-dd")+"'\n"
                + "  and d.condominio_id = " + condominioId + "\n"
                + " GROUP BY YEAR(a.fechapago), MONTH(a.fechapago), a.departamento_id DESC)\n"
                + " order by 5,1,2\n";

        List<Object[]> lista = executeNativeQuery(sql);
        HashMap<String, EstadoCuenta> hm = new HashMap<String, EstadoCuenta>();

        for (Object[] res : lista) {
            if (res[0] == null) {
                break;
            }
            EstadoCuenta ec = new EstadoCuenta();
            String fecha = res[0].toString() + "-" + res[1].toString() + "-01";

            String key = fecha + res[4].toString();
            if (hm.containsKey(key)) {
                ec = hm.get(key);
            } else {
                hm.put(key, ec);
            }

            ec.setFechaPeriodo(UtilFile.strToDate(fecha, "yyyy-MM-dd"));
            BigDecimal cargo = new BigDecimal(BigInteger.ZERO);
            BigDecimal abono = new BigDecimal(BigInteger.ZERO);
            if (res[2] == null) {
                //COMO CARGO VIENE EN NULL, SE TRATA DE UN ABONO
                abono = res[3] == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(res[3].toString());
                ec.setAbonos(abono);
                if (ec.getCargos() == null) {
                    ec.setCargos(cargo);
                }
                ec.setSaldo(ec.getCargos().subtract(abono));
            }
            if (res[3] == null) {
                //COMO ABONO VIENE EN NULL, SE TRATA DE UN CARGO
                cargo = res[2] == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(res[2].toString());
                ec.setCargos(cargo);
                if (ec.getAbonos() == null) {
                    ec.setAbonos(abono);
                }
                ec.setSaldo(cargo.subtract(ec.getAbonos()));
            }
            Departamento d = new Departamento();
            if (res[4] != null) {
                d.setId(new Long(res[4].toString()));
                d.setNombre(res[5].toString());
            }
            if (res[6] != null) {
                Torre t = new Torre();
                t.setId(new Long(res[6].toString()));
                t.setNombre(res[7].toString());
                d.setTorre(t);
            }
            if (res[8] != null) {
                Condominio c = new Condominio();
                c.setId(new Long(res[8].toString()));
                c.setNombre(res[9].toString());
                d.setCondominio(c);
            }
            
            ec.setDepartamento(d);
        }

        List<EstadoCuenta> saldos = new ArrayList<EstadoCuenta>();
        for (Map.Entry<String, EstadoCuenta> entry : hm.entrySet()) {
//            System.out.println("clave=" + entry.getKey() + ", valor=" + entry.getValue());
            saldos.add(entry.getValue());
        }
        Collections.sort(saldos);
        for (EstadoCuenta e : saldos) {
            System.out.println(e);
        }
        return saldos;
    }
    
}
