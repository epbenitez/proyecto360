/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.allinone.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 *
 * @author Patricia Benitez
 */
public class ExcelExport {
    private InputStream excelStream;
    
    public InputStream construyeExcel(String[] encabezado, List<Object[]> matriz){
        try {
            Integer limite = 65534;
            
            int contadorHojas = 0;
            int registros = 0;
            boolean bandera = true;
            
            HSSFWorkbook libro = new HSSFWorkbook();
            
            while(true){
                int renglonNuevaHoja = 1;
                if(registros>=matriz.size()){
                    break;
                }
                
                HSSFSheet hoja = libro.createSheet("Hoja "+contadorHojas);
                
                HSSFRow titulos = hoja.createRow(0);
                
                for(int a=0; a<encabezado.length; a++){    
                    titulos.createCell(a).setCellValue(encabezado[a]);
                }
                
                for(int k=registros; k<matriz.size(); k++){
                    if(registros!=0 && bandera){
                        if(registros % limite == 0){
                            contadorHojas++;
                            bandera = false;
                            break;
                        }
                    }
                    bandera = true;
                    
                    HSSFRow renglon = hoja.createRow(renglonNuevaHoja);
                    Object[] elemento = matriz.get(k);
                    for(int j=0; j<elemento.length; j++){
                        renglon.createCell(j).setCellValue(elemento[j]==null?"":elemento[j].toString());
                    }
                    registros++;
                    renglonNuevaHoja++;
                }
                
//                contadorHojas++;
            }
            
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            libro.write(baos);
            excelStream = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        return excelStream;
    }
    
    public InputStream construyeExcelDosEncabezados(String[] encabezado,String[] encabezado2, List<Object[]> matriz){
        try {
            Integer limite = 65534;
            
            int contadorHojas = 0;
            int registros = 0;
            boolean bandera = true;
            
            HSSFWorkbook libro = new HSSFWorkbook();
            
            while(true){
                int renglonNuevaHoja = 2;
                if(registros>=matriz.size()){
                    break;
                }
                
                HSSFSheet hoja = libro.createSheet("Hoja "+contadorHojas);
                
                HSSFRow titulos = hoja.createRow(0);
                
                for(int a=0; a<encabezado.length; a++){    
                    titulos.createCell(a).setCellValue(encabezado[a]);
                }
                
                titulos = hoja.createRow(1);
                
                for(int a=0; a<encabezado2.length; a++){    
                    titulos.createCell(a).setCellValue(encabezado2[a]);
                }
                
                for(int k=registros; k<matriz.size(); k++){
                    if(registros!=0 && bandera){
                        if(registros % limite == 0){
                            contadorHojas++;
                            bandera = false;
                            break;
                        }
                    }
                    bandera = true;
                    
                    HSSFRow renglon = hoja.createRow(renglonNuevaHoja);
                    Object[] elemento = matriz.get(k);
                    for(int j=0; j<elemento.length; j++){
                        renglon.createCell(j).setCellValue(elemento[j]==null?"":elemento[j].toString());
                    }
                    registros++;
                    renglonNuevaHoja++;
                }
                
                contadorHojas++;
            }
            
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            libro.write(baos);
            excelStream = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        return excelStream;
    }
}
