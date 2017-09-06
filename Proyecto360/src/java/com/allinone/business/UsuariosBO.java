package com.allinone.business;

import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.SolicitudesTipoInmueble;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioCondominio;
import com.allinone.persistence.model.UsuarioPrivilegio;
import com.allinone.service.Service;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author erikapatriciabenitezsoto
 */
public class UsuariosBO extends XlsLoaderBO {

    private Service service;

    public UsuariosBO(Service service) {
        this.service = service;
    }

    public List<String> _readXlsFile(FileInputStream xls) throws IOException {
        POIFSFileSystem fs = new POIFSFileSystem(xls);
        Workbook wb = new HSSFWorkbook(fs);
        try {
            return processFile(wb);
        } catch (Exception ex) {
            Logger.getLogger(CargosBO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public List<String> _readXlsxFile(FileInputStream xlsx) throws IOException {
        Workbook wb = new XSSFWorkbook(xlsx);
        try {
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
        String condominioStr = "";
        String tipoInmuebleStr = "";
        String departamentoStr;
        String usuarioStr = "";
        String pwdString = "";
        String estatusStr = "";
        Condominio condominio = new Condominio();
        SolicitudesTipoInmueble tipoInmueble = new SolicitudesTipoInmueble();
        Usuario usuario = new Usuario();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            try {

                //--------------------------ID INMUEBLE COLUMNA 2-------------------------------
                if (row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 2;
                    try {
                        tipoInmuebleStr = row.getCell(1).getStringCellValue();
                    } catch (Exception e) {
                        tipoInmuebleStr = row.getCell(1).getNumericCellValue() + "";
                    }

                    if (tipoInmuebleStr != null && !tipoInmuebleStr.isEmpty()) {
                        if (tipoInmuebleStr.indexOf(".") > 0) {
                            tipoInmuebleStr = tipoInmuebleStr.trim().substring(0, tipoInmuebleStr.indexOf("."));
                        }
                        tipoInmueble = service.getSolicitudesTipoInmuebleDao().findById(new Long(tipoInmuebleStr));
                        if (tipoInmueble == null) {
                            throw new Exception("Tipo de Inmueble no reconocido (" + tipoInmuebleStr + ")");
                        }
                    }
                }

                //------------------------CONDOMINIO COLUMNA 1----------------------------
                if (row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 1;
                    condominioStr = row.getCell(0).getStringCellValue();
                    if (condominioStr != null && !condominioStr.isEmpty()) {
                        condominio = service.getCondominioDao().findUniqueByName(condominioStr);
                        if (condominio == null) {
                            condominio = new Condominio();
                            condominio.setClave(condominioStr.replaceAll(" ", ""));
                            condominio.setNombre(condominioStr);
                            condominio.setTipoInmueble(tipoInmueble);
                            condominio = service.getCondominioDao().save(condominio);
                        }
                    }
                }

                //------------------------USUARIO COLUMNA 4-------------------------------
                if (row.getCell(3) != null && row.getCell(3).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 4;
                    try {
                        row.getCell(3).setCellType(Cell.CELL_TYPE_STRING);
                        usuarioStr = row.getCell(3).getStringCellValue();
                    } catch (Exception e) {
                        usuarioStr = row.getCell(3).getNumericCellValue() + "";
                    }

                }
                //------------------------PASSWORD-------------------------------
                if (row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 5;
                    try {
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        pwdString = row.getCell(4).getStringCellValue();
                    } catch (Exception e) {
                        pwdString = row.getCell(4).getNumericCellValue() + "";
                    }

                }
                //------------------------ESTATUS-------------------------------
                if (row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 6;
                    try {
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        estatusStr = row.getCell(5).getStringCellValue();
                    } catch (Exception e) {
                        estatusStr = row.getCell(5).getNumericCellValue() + "";
                    }

                }

                //------------------------USUARIO-------------------------------
                if (usuarioStr != null) {
                    columna = 1000;
                    usuario = service.getUsuarioDao().findById(usuarioStr);
                    if (usuario == null) {
                        usuario = new Usuario();
                        usuario.setUsuario(usuarioStr);
                        String[] arr = pwdString.split("\\.");
                        usuario.setPassword(arr[0]);
                        usuario.setActivo(estatusStr.equals("Activo") ? Boolean.TRUE : Boolean.FALSE);
                        Set<UsuarioPrivilegio> roles = new HashSet<UsuarioPrivilegio>();
                        UsuarioPrivilegio privilegio = new UsuarioPrivilegio();
                        privilegio.setPrivilegio(new Rol(new Long(2))); //ROLE_PROPIETARIO
                        privilegio.setUsuario(usuario);
                        roles.add(privilegio);
                        usuario.setPrivilegios(roles);
                        usuario = service.getUsuarioDao().save(usuario);
                    }
                }

                //-----RELACION CONDOMINIO-USUARIO------------------------------
                if (condominio != null && usuario != null) {
                    UsuarioCondominio uc = new UsuarioCondominio();
                    uc.setCondominio(condominio);
                    uc.setUsuario(usuario);
                    service.getUsuarioCondominioDao().save(uc);
                }

//                //----------------RELACION USUARIO-DEPTO------------------------
//                  ---YA NO APLICA--- 2017-09-04
//                if (dpto != null && usuario != null) {
//                    columna = 2000;
//                    DepartamentoUsuario du = new DepartamentoUsuario();
//                    du.setDepartamento(dpto);
//                    du.setUsuario(usuario);
//                    service.getDepartamentoUsuarioDao().save(du);
//                }
                logScreen.add("<tr><td>" + (i + 1) + "</td><td>"
                        + condominio.getNombre() + "</td><td>"
                        + tipoInmueble.getNombre() + "</td><td>"
                        + usuario.getUsuario() + "</td><td>"
                        + (usuario.isActivo() ? "Activo" : "Inactivo") + "</td>"
                        + "<td style='color:green'>OK</td></tr>");

            } catch (Exception e) {
                if (columna == 1000) {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" 
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR AL INTENTAR CREAR EL USUARIO " + usuarioStr + "</td></tr>");

                } else if (tipoInmueble == null) {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" 
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR EN LA COLUMNA: " + columna + " ( "+ e.getMessage()+ ")</td></tr>");
                }else {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" 
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR EN EL FORMATO DE LA COLUMNA: " + columna + "</td></tr>");
                }
                e.printStackTrace();
                continue;
            }
        }

        return logScreen;
    }

}
