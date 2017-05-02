package com.allinone.persistence.dao.jpa;

import com.allinone.persistence.dao.DepartamentoContactoDao;
import com.allinone.persistence.model.AbonoDatosPago;
import com.allinone.persistence.model.AbonoTipo;
import com.allinone.persistence.model.Banco;
import com.allinone.persistence.model.Concepto;
import com.allinone.persistence.model.Contacto;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoContacto;
import com.allinone.persistence.model.Torre;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.List;
import static oracle.net.aso.a.a;

/**
 *
 * @author patriciabenitez
 */
public class DepartamentoContactoJpaDao extends JpaDaoBase<DepartamentoContacto, Long>
        implements DepartamentoContactoDao {

    public DepartamentoContactoJpaDao() {
        super(DepartamentoContacto.class);
    }

    @Override
    public List<DepartamentoContacto> getContactos(Long grupoId) {
        String sql = "select d.id,d.nombre,c.nombre,c.correoelectronico from ent_notificaciones_grupos_depto ngd \n"
                + "inner join ent_departamento d on d.id = ngd.departamento_id\n"
                + " inner join rmm_departamento_contacto dc on dc.departamento_id=d.id\n"
                + " inner join cat_contactos c on c.id = dc.contacto_id\n"
                + " where ngd.grupo_id = " + grupoId;
        List<DepartamentoContacto> listaDC = new ArrayList<DepartamentoContacto>();
        List<Object[]> lista = executeNativeQuery(sql);
        if (lista == null || lista.isEmpty()) {
            return null;
        } else {
            for (Object[] o : lista) {
                DepartamentoContacto dc = new DepartamentoContacto();
                Departamento d = new Departamento();
                d.setId(new Long(o[0] == null ? null : o[0].toString()));
                d.setNombre(o[1] == null ? null : o[1].toString());
                
                Contacto c = new Contacto();
                c.setNombre(o[2] == null ? null : o[2].toString());
                c.setCorreoElectronico(o[3] == null ? null : o[3].toString());
                
                dc.setContacto(c);
                dc.setDepartamento(d);

                listaDC.add(dc);
            }
        }

        return listaDC;
    }
}
