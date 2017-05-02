
package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.AbonoDatosPagoDao;
import com.allinone.persistence.model.AbonoDatosPago;
import com.allinone.persistence.model.AbonoTipo;
import com.allinone.persistence.model.Banco;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Patricia Benítez
 */
public class AbonoDatosPagoJpaDao extends JpaDaoBase<AbonoDatosPago,Long> implements AbonoDatosPagoDao{

    public AbonoDatosPagoJpaDao(){
        super(AbonoDatosPago.class);
    }
    
    @Override
    public List<AbonoDatosPago> abonosPorCondominioTorreDepartamento(Long condominioId, Long torreId, Long departamentoId, Date fechaInicial, Date fechaFinal) {
        String sql = "select   adp.id, adp.numero,adp.titular, adp.fechaemision, adp.fechapago, b.nombre,at.nombre, a.monto, u.usuario,"
                + " d.nombre departamento, t.nombre torre "
                + " from ent_abonos a "
                + " inner join ent_abonos_datos_pago adp on adp.id = a.datospago_id "
                + " inner join ent_departamento d on a.departamento_id = d.id "
                + " inner join ent_torre t on d.torre_id = t.id "
                + " inner join cat_bancos b on b.id = adp.banco_id "
                + " inner join cat_abono_tipo at on at.id = adp.abonotipo_id "
                + " inner join ent_usuario u on u.id = adp.usuario_id  "
                + "where 1=1 "
                + " and a.fechapago BETWEEN '" + UtilFile.dateToString(fechaInicial, "yyyy-MM-dd") + "' and '" + UtilFile.dateToString(fechaFinal, "yyyy-MM-dd") + "'"
                + (condominioId==null?"": " and d.condominio_id = " + condominioId )
                + (torreId==null?"": " and d.torre_id = " + torreId )
                + (departamentoId==null?"": " and d.id = " + departamentoId )
                + "  order by 1 desc";
        List<AbonoDatosPago> listaAbonos = new ArrayList<AbonoDatosPago>();
        List<Object[]> abonos = executeNativeQuery(sql);
        if(abonos==null || abonos.isEmpty()){
            return null;
        }else{
            for(Object[] o : abonos){
                AbonoDatosPago adp = new AbonoDatosPago();
                adp.setId(new Long(o[0].toString()));
                adp.setNumero(o[1]==null?"":o[1].toString());
                adp.setTitular(o[2]==null?"":o[2].toString());
                adp.setFechaEmision(o[3]==null?null:UtilFile.strToDate(o[3].toString(),"yyyy-MM-dd"));
                adp.setFechaPago(o[4]==null?null:UtilFile.strToDate(o[4].toString(),"yyyy-MM-dd"));
                
                Banco b = new Banco();
                b.setNombre(o[5].toString());
                adp.setBanco(b);
                
                AbonoTipo at = new AbonoTipo();
                at.setNombre(o[6].toString());
                adp.setAbonoTipo(at);
                
                adp.setMonto(o[7]==null?null:Double.valueOf(o[7].toString()));
                
                Usuario u = new Usuario();
                u.setUsuario(o[8]==null?"":o[8].toString());
                adp.setUsuario(u);
                
                Departamento d = new Departamento();
                d.setNombre(o[9]==null?"":o[9].toString());
                Torre t = new Torre();
                t.setNombre(o[10]==null?"":o[10].toString());
                d.setTorre(t);
                adp.setDepartamento(d);
                
                listaAbonos.add(adp);
            }
        }

        return listaAbonos;
    }
}
