package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AbonoDao;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.AbonoDatosPago;
import com.allinone.persistence.model.AbonoTipo;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Banco;
import com.allinone.persistence.model.Condominio;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Query;

/**
 *
 * @author Patricia Benitez
 */
public class AbonoJpaDao extends JpaDaoBase<Abono, Long> implements AbonoDao {

    public AbonoJpaDao() {
        super(Abono.class);
    }

    /**
     * Elimina los registros que coincidan con el identificador especificado
     *
     * @param identificador
     */
    @Override
    public void elimina(String identificador) {
        if (identificador != null) {
            String jpql = "delete from Abono where identificadorcarga like ?1";
            Query query = getEntityManager().createQuery(jpql);
            query.setParameter(1, identificador);
            query.executeUpdate();
        }
    }

    @Override
    public List<Abono> abonosPeriodoDetalle(Date fechaInicial, Date fechaFinal, Long departamentoId) {
        String jpql = "select   c  from Abono c "
                + "where c.fechaPago between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + "and c.departamento.id = ?1 and  c.fechaAdmon is null";

        List<Abono> cargos = executeQuery(jpql, departamentoId);

        return cargos;
    }
    
    @Override
    public List<Abono> abonosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId, Integer tipoAbono) {
        String sql = "select   a.id, a.fechapago, a.monto, c.nombre concepto, d.nombre departamento, t.nombre torre, "
                + "  at.nombre, adp.numero, adp.fechaemision,adp.titular, b.nombre, a.fechaadmon  "
                + " from ent_abonos a "
                + "left join ent_abonos_datos_pago adp on adp.id = a.datospago_id "
                + "inner join cat_conceptos c on a.concepto_id = c.id "
                + "inner join ent_departamento d on a.departamento_id = d.id "
                + "inner join ent_torre t on d.torre_id = t.id "
                + "left join cat_bancos b on b.id = adp.banco_id "
                + " left join cat_abono_tipo at on at.id = adp.abonotipo_id "
                + "where a.fechapago BETWEEN '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + (tipoAbono==null?"":(tipoAbono==0?"":(tipoAbono==1? " and a.fechaAdmon is not null ":" and a.fechaAdmon is null ")))
                + "and d.condominio_id = " + condominioId + "  order by 2 asc";
        List<Abono> listaAbonos = new ArrayList<Abono>();
        List<Object[]> abonos = executeNativeQuery(sql);
        if(abonos==null || abonos.isEmpty()){
            return null;
        }else{
            for(Object[] o : abonos){
                Abono a = new Abono();
                a.setId(new Long(o[0]==null?null:o[0].toString()));
                a.setFechaPago(o[1]==null?null:UtilFile.strToDate(o[1].toString(),"yyyy-MM-dd"));
                a.setFechaAdmon(o[11]==null?null:UtilFile.strToDate(o[11].toString(),"yyyy-MM-dd"));
                a.setMonto(Double.valueOf(o[2]==null?null:o[2].toString()));
                Concepto c = new Concepto();
                c.setNombre(o[3]==null?null:o[3].toString());
                a.setConcepto(c);
                Departamento d = new Departamento();
                d.setNombre(o[4]==null?null:o[4].toString());
                Torre t = new Torre();
                t.setNombre(o[5]==null?null:o[5].toString());
                d.setTorre(t);
                a.setDepartamento(d);
                
                AbonoDatosPago dp = new AbonoDatosPago();
                AbonoTipo at = new AbonoTipo();
                at.setNombre(o[6]==null?null:o[6].toString());
                dp.setAbonoTipo(at);
                dp.setNumero(o[7]==null?null:o[7].toString());
                dp.setFechaEmision(o[8]==null?null:UtilFile.strToDate(o[8].toString(),"yyyy-MM-dd"));
                dp.setTitular(o[9]==null?null:o[9].toString());
                
                Banco b = new Banco();
                b.setNombre(o[10]==null?null:o[10].toString());
                dp.setBanco(b);
                
                a.setDatosPago(dp);
                
                listaAbonos.add(a);
            }
        }

        return listaAbonos;
    }
    
    @Override
    public List<Abono> abonosPorCondominio(Date fechaInicial, Date fechaFinal, Long condominioId) {
        String jpql = "select   c  from Abono c "
                + "where c.fechaPago between '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + " and c.departamento.condominio.id = ?1";

        List<Abono> cargos = executeQuery(jpql, condominioId);

        return cargos;
    }
    
    @Override
    public List<String> getIdentificadoresCarga(Long condominioId){
        String sql = "SELECT distinct(a.identificadorcarga),1 FROM ent_abonos a "
                + " inner join ent_departamento d on d.ID = a.DEPARTAMENTO_ID "
                + " inner join ent_condominio c on c.id = d.condominio_id  "
                + " where a.identificadorcarga not like 'M%'"
                + " and d.CONDOMINIO_ID =  " + condominioId ;
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
    public List<Abono> abonos(String identificadorcarga, Long condominioId) {
        String sql = "SELECT  a.id, a.fechapago,a.monto, con.nombre, d.nombre,t.nombre, c.nombre"
                + " FROM ent_abonos a  "
                + " inner join ent_departamento d on d.ID = a.DEPARTAMENTO_ID "
                + " inner join ent_condominio c on c.id = d.condominio_id "
                + " inner join ent_torre t on t.id = d.torre_id "
                + " inner join cat_conceptos con on con.id = a.concepto_id "
                + " WHERE a.IDENTIFICADORCARGA = '"+identificadorcarga+"' "
                + " AND d.CONDOMINIO_ID =  " + condominioId ;

        List<Abono> listaAbonos = new ArrayList<Abono>();
        List<Object[]> abonos = executeNativeQuery(sql);
        if(abonos==null || abonos.isEmpty()){
            return null;
        }else{
            for(Object[] o : abonos){
                Abono a = new Abono();
                a.setId(new Long(o[0].toString()));
                a.setFechaPago(o[1]==null?new Date():UtilFile.strToDate(o[1].toString(),"yyyy-MM-dd"));
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
                
                listaAbonos.add(a);
            }
        }

        return listaAbonos;
    }
    
    @Override
    public List<Abono> abonos( Long datosPagoId) {
        List<Abono> abonos = new ArrayList<Abono>();
        String jpql = "SELECT  NEW com.allinone.persistence.model.Abono(a.id, a.concepto, a.monto)  FROM Abono a WHERE  a.datosPago.id = ?1 ";
        abonos = executeQuery(jpql, datosPagoId);
        return abonos==null || abonos.isEmpty()?null:abonos;
    }
}
