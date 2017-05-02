package com.allinone.business;

import com.allinone.persistence.model.Condominio;
import com.allinone.persistence.model.Departamento;
import com.allinone.persistence.model.DepartamentoUsuario;
import com.allinone.persistence.model.Rol;
import com.allinone.persistence.model.TipoDepartamento;
import com.allinone.persistence.model.Torre;
import com.allinone.persistence.model.Usuario;
import com.allinone.persistence.model.UsuarioPrivilegio;
import com.allinone.service.Service;
import com.allinone.util.UtilFile;
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
        String torreStr = "";
        String departamentoStr;
        String usuarioStr = "";
        String pwdString = "";
        String estatusStr = "";
        Condominio condominio = new Condominio();
        Torre torre = new Torre();
        Departamento dpto = new Departamento();
        Usuario usuario = new Usuario();

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            try {

                //------------------------CONDOMINIO----------------------------
                if (row.getCell(0) != null && row.getCell(0).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 1;
                    condominioStr = row.getCell(0).getStringCellValue();
                    if (condominioStr != null && !condominioStr.isEmpty()) {
                        condominio = service.getCondominioDao().findUniqueByName(condominioStr);
                        if (condominio == null) {
                            condominio = new Condominio();
                            condominio.setClave(condominioStr.replaceAll(" ", ""));
                            condominio.setNombre(condominioStr);
                            condominio = service.getCondominioDao().save(condominio);
                        }
                    }
                }

                //--------------------------TORRE-------------------------------
                if (row.getCell(1) != null && row.getCell(1).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 2;
                    torreStr = row.getCell(1).getStringCellValue();
                    if (torreStr != null && !torreStr.isEmpty()) {
                        String claveTorre = condominio.getNombre().replaceAll(" ", "") + torreStr.replaceAll(" ", "");
                        torre = service.getTorreDao().findByClave(claveTorre);
                        if (torre == null) {
                            torre = new Torre();
                            torre.setClave(claveTorre);
                            torre.setNombre(torreStr);
                            torre = service.getTorreDao().save(torre);
                        }
                    }
                }

                //------------------------DEPARTAMENTO--------------------------
                if (row.getCell(2) != null && row.getCell(2).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 3;
                    try {
                        row.getCell(2).setCellType(Cell.CELL_TYPE_STRING);
                        departamentoStr = row.getCell(2).getStringCellValue();
                    } catch (Exception e) {
                        departamentoStr = row.getCell(2).getNumericCellValue() + "";
                    }

                    if (departamentoStr != null) {
                        if (departamentoStr.indexOf(".") >= 0) {
                            departamentoStr = departamentoStr.substring(0, departamentoStr.indexOf("."));
                        }

                        dpto = service.getDepartamentoDao().findBy(departamentoStr, torre.getClave(), condominio.getId());
                        if (dpto == null) {
                            dpto = new Departamento();
                            dpto.setCondominio(condominio);
                            dpto.setTorre(torre);
                            dpto.setNombre(departamentoStr);
                            dpto.setTipoDepartamento(new TipoDepartamento(1L));
                            dpto = service.getDepartamentoDao().save(dpto);
                        }
                    }
                }

                //------------------------USUARIO-------------------------------
                if (row.getCell(4) != null && row.getCell(4).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 5;
                    try {
                        row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                        usuarioStr = row.getCell(4).getStringCellValue();
                    } catch (Exception e) {
                        usuarioStr = row.getCell(4).getNumericCellValue() + "";
                    }

                }
                //------------------------PASSWORD-------------------------------
                if (row.getCell(5) != null && row.getCell(5).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 6;
                    try {
                        row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
                        pwdString = row.getCell(5).getStringCellValue();
                    } catch (Exception e) {
                        pwdString = row.getCell(5).getNumericCellValue() + "";
                    }

                }
                //------------------------ESTATUS-------------------------------
                if (row.getCell(6) != null && row.getCell(6).getCellType() != Cell.CELL_TYPE_BLANK) {
                    columna = 7;
                    try {
                        row.getCell(6).setCellType(Cell.CELL_TYPE_STRING);
                        estatusStr = row.getCell(6).getStringCellValue();
                    } catch (Exception e) {
                        estatusStr = row.getCell(6).getNumericCellValue() + "";
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

                //----------------RELACION USUARIO-DEPTO------------------------
                if (dpto != null && usuario != null) {
                    columna = 2000;
                    DepartamentoUsuario du = new DepartamentoUsuario();
                    du.setDepartamento(dpto);
                    du.setUsuario(usuario);
                    service.getDepartamentoUsuarioDao().save(du);
                }

                logScreen.add("<tr><td>" + (i + 1) + "</td><td>"
                        + dpto.getCondominio().getNombre() + "</td><td>"
                        + dpto.getTorre().getNombre()+ "</td><td>"
                        + dpto.getNombre() + "</td><td>"
                        + usuario.getUsuario() + "</td><td>"
                        + (usuario.isActivo() ? "Activo" : "Inactivo") + "</td>"
                        + "<td style='color:green'>OK</td></tr>");

            } catch (Exception e) {
                if (columna == 1000) {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" + "</td><td>" + ""
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR AL INTENTAR CREAR EL USUARIO " + usuarioStr + "</td></tr>");

                } else if (columna == 1000) {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" + "</td><td>" + ""
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR AL ESTABLECER LA RELACION USUARIO - DEPARTAMENTO </td></tr>");

                } else {
                    logScreen.add("<tr><td>" + (i + 1) + "</td><td>" + "" + "</td><td>" + "" + "</td><td>" + ""
                            + "</td><td>" + "" + "</td><td>" + "" + "</td><td style='color:red'>ERROR EN EL FORMATO DE LA COLUMNA: " + columna + "</td></tr>");
                }
                e.printStackTrace();
                continue;
            }
        }

        return logScreen;
    }

}
