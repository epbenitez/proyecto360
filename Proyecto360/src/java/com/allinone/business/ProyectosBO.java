package com.allinone.business;

/**
 *
 * @author patriciabenitez
 */
public class ProyectosBO {
    
    
    public String[] getColor(Long dias, String estatus) {
        
        String[] colorMasTooltip = new String[2];
        String color = "blue";
        
        if(estatus!=null && estatus.equals("Cerrado")){
            colorMasTooltip[0] = "blue";
            colorMasTooltip[1] = "Cerrado";
            return colorMasTooltip;
        }
        
        if(dias>=0 && dias<=7){
            colorMasTooltip[0] = "yellow";
            colorMasTooltip[1] = "Atendido";
        }
        
        if(dias>=8){
            colorMasTooltip[0] = "green";
            colorMasTooltip[1] = "En tiempo";
        }
        
        if(dias<0){
            colorMasTooltip[0] = "red";
            colorMasTooltip[1] = "¡Atención urgente!";
        }
        
        

        return colorMasTooltip;
    }
}
