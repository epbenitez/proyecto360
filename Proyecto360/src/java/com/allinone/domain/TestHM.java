package com.allinone.domain;

import com.allinone.util.UtilFile;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author patriciabenitez
 */
public class TestHM {

    public static void main(String[] args) {

        HashMap<String, EstadoCuenta> hm = new HashMap<String, EstadoCuenta>();
        List<String[]> lista = new ArrayList<String[]>();
        String[] dato = new String[4];
        dato[0] = "2016";
        dato[1] = "1";
        dato[2] = "1100947.46044922";
        dato[3] = null;
        lista.add(dato);

        dato = new String[4];
        dato[0] = "2016";
        dato[1] = "1";
        dato[2] = null;
        dato[3] = "1032822.25";
        lista.add(dato);

        dato = new String[4];
        dato[0] = "2016";
        dato[1] = "2";
        dato[2] = "5400";
        dato[3] = null;
        lista.add(dato);

        dato = new String[4];
        dato[0] = "2016";
        dato[1] = "3";
        dato[2] = null;
        dato[3] = "2300";
        lista.add(dato);

        for (String[] res : lista) {
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
                if(ec.getCargos()==null){
                    ec.setCargos(cargo);
                }
                ec.setSaldo(ec.getCargos().subtract(abono));
            }
            if (res[3] == null) {
                //COMO ABONO VIENE EN NULL, SE TRATA DE UN CARGO
                cargo = res[2] == null ? new BigDecimal(BigInteger.ZERO) : new BigDecimal(res[2].toString());
                ec.setCargos(cargo);
                if(ec.getAbonos()==null){
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
        
        for(EstadoCuenta e: saldos){
            System.out.println(e);
        }

    }
}
