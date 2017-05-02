
package com.allinone.business;

import com.allinone.service.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Definición de acciones que hagan uso de la carga de archivos XLS o XLSX
 *
 */
public abstract class XlsLoaderBO extends BaseBO {

    public XlsLoaderBO(Service service) {
        super(service);
    }

    public XlsLoaderBO() {
    }

    public Workbook readXlsFile(FileInputStream xls) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(xls);
        Workbook wb = new HSSFWorkbook(fs);
        return wb;
    }

    public Workbook readXlsxFile(FileInputStream xlsx) throws IOException {
        Workbook wb = new XSSFWorkbook(xlsx);
        return wb;
    }

    public abstract <T> List<T> processFile(Workbook wb) throws Exception;

}