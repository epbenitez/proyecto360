package com.allinone.business;

import com.allinone.domain.ConfiguracionExcel;
import com.allinone.persistence.model.Abono;
import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.service.Service;
import com.allinone.util.UtilFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Patricia Benitez
 */
public class AbonosBO extends XlsLoaderBO {

    private Service service;

    public AbonosBO(Service service) {
        this.service = service;
    }

    private List<ConfiguracionExcel> configuracionExcel;
    private Condominio condominio;
    private String identificador;

    public String getIdentificador() {
        Date d = new Date();
        return "A" + UtilFile.dateToString(d, "yyyyMMddhhmmss");
    }

    public List<String> readXlsFile(List<ConfiguracionExcel> configuracion, Condominio c, String identificadorCarga, FileInputStream xls) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(xls);
        Workbook wb = new HSSFWorkbook(fs);
        try {
            this.configuracionExcel = configuracion;
            this.condominio = c;
            this.identificador = identificadorCarga;
            return processFile(wb);
        } catch (Exception ex) {
            Logger.getLogger(CargosBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<String> readXlsxFile(List<ConfiguracionExcel> configuracion, Condominio c, String identificadorCarga, FileInputStream xlsx) throws IOException {
        Workbook wb = new XSSFWorkbook(xlsx);
        try {
            this.configuracionExcel = configuracion;
            this.condominio = c;
            this.identificador = identificadorCarga;
            return processFile(wb);
        } catch (Exception ex) {
            Logger.getLogger(CargosBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
    public List<String> processFile(Workbook wb) throws Exception {

        List<String> logScreen = new LinkedList<String>();

        Sheet sheet = wb.getSheetAt(0);

        Integer columna = 0;
        String torreStr = "";
        String departamentoStr;
        Departamento dpto = new Departamento();
        String identificadorCarga = this.getIdentificador();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            try {

                if (row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 1;
                    torreStr = row.getCell(1).getStringCellValue();
                }

                if (row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 2;
                    try {
                        row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                        departamentoStr = row.getCell(2).getStringCellValue();
                    } catch (Exception e) {
                        departamentoStr = row.getCell(2).getNumericCellValue() + "";
                    }
                    if (departamentoStr != null) {
                        dpto = service.getDepartamentoDao().findBy(departamentoStr, torreStr, condominio.getId());
                    }
                }

                for (ConfiguracionExcel ce : this.configuracionExcel) {
                    Integer columnaFecha = ce.getColumnaFecha();
                    Integer columnaMonto = ce.getColumnaMonto();
                    Date fecha = null;
                    Double monto = null;

                    if (row.getCell(columnaFecha) != null && row.getCell(columnaFecha).getCellType() != Cell.CELL_TYPE_BLANK) {
                        columna = columnaFecha;
                        try {
                            if (HSSFDateUtil.isCellDateFormatted(row.getCell(columnaFecha))) {
                                System.out.println("Row No.: " + row.getRowNum() + " "
                                        + row.getCell(columnaFecha).getDateCellValue());
                                fecha = row.getCell(columnaFecha).getDateCellValue();
                            }
                        } catch (Exception e) {
                            fecha = UtilFile.strToDate(row.getCell(columnaFecha) + "", "dd/MM/yyyy");
                        }
                    }
                    if (row.getCell(columnaMonto) != null && row.getCell(columnaMonto).getCellType() != Cell.CELL_TYPE_BLANK) {
                        columna = columnaMonto;
                        System.out.println("Row No.: " + row.getRowNum());
                        monto = row.getCell(columnaMonto).getNumericCellValue();
                    }

                    if (monto != null && monto.intValue() != 0) {
                        Abono abono = new Abono();
                        abono.setConcepto(ce.getConcepto());
                        abono.setDepartamento(dpto);
                        abono.setFechaPago(fecha);
                        abono.setMonto(monto);
                        abono.setIdentificadorcarga(this.identificador);

                        service.getAbonoDao().save(abono);

                        logScreen.add("<tr><td>" + (i + 1) + "</td><td>"
                                + dpto.getTorre().getClave() + "</td><td>"
                                + dpto.getNombre() + "</td><td>"
                                + ce.getConcepto().getNombre() + "</td><td>"
                                + UtilFile.dateToString(fecha, "dd/MM/yyyy") + "</td><td>"
                                + monto + "</td><td style='color:green'>OK</td></tr>");
                    }

                }
            } catch (Exception e) {
                logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" + "</td><td>" + ""
                        + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR EN EL FORMATO DE LA COLUMNA: " + columna + "</td></tr>");
                e.printStackTrace();
                continue;
            }
        }

        return logScreen;
    }

    public void revertirAbonos(String identificadorCarga) {
        service.getAbonoDao().elimina(identificadorCarga);
    }

    public List<ConfiguracionExcel> getConfiguracionExcel() {
        return configuracionExcel;
    }

    public void setConfiguracionExcel(List<ConfiguracionExcel> configuracionExcel) {
        this.configuracionExcel = configuracionExcel;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

}
