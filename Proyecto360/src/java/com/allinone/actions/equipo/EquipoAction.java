package com.allinone.actions.equipo;

import com.allinone.actions.BaseAction;
import com.allinone.persistence.model.Condominio;
import com.allinone.util.UtilFile;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author patriciabenitez
 */
public class EquipoAction extends BaseAction{
    
    private List<Condominio> condominios = new ArrayList<Condominio>();
    private Map<String, String>  anios = new LinkedHashMap<String, String>();
    
    public String reporteEjecutivo(){
        condominios = getDaos().getCondominioDao().findAll();
        return INPUT;
    }

    public List<Condominio> getCondominios() {
        return condominios;
    }

    public void setCondominios(List<Condominio> condominios) {
        this.condominios = condominios;
    }

    public Map<String, String> getAnios() {
        Integer anioIni = 2000;
        Integer anioFin = new Integer(UtilFile.dateToString(new Date(), "yyyy"));
        
        for(Integer i = anioFin;i>=anioIni; i--){
            anios.put(i.toString(),i.toString());
        }
        return anios;
    }

    public void setAnios(Map<String, String> anios) {
        this.anios = anios;
    }

    
    
}
