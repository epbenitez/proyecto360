package com.allinone.util;

import java.util.LinkedHashMap;
import java.util.Map;
import com.allinone.domain.Genero;
import java.util.Calendar;

/**
 * Servicio que obtiene los valores de los enums para desplegarlos en selects
 *
 * @author Patricia Benitez
 * @version $Rev: 1208 $
 * @since 1.0
 */
public class AmbienteEnums {

    private static AmbienteEnums service = new AmbienteEnums();

    /**
     * Crea una nueva instancia de <code>AmbienteEnums</code>
     */
    private AmbienteEnums() {
    }

    /**
     * Singleton Pattern, permite un solo objeto del tipo
     *
     * @return AmbienteEnums
     */
    static public AmbienteEnums getInstance() {
        return service;
    }

    /**
     * Regresa la lista de valores espuesta determinante
     *
     * @return Tipos de respuestas (Si/True,No/false)
     */
    public Map<String, String> getRespuestaBoolean() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("true", "Sí");
        respuestaBoolean.put("false", "No");

        return respuestaBoolean;
    }

    /**
     * Regresa la lista de valores espuesta determinante
     *
     * @return Tipos de respuestas (Si/True,No/false)
     */
    public Map<Integer, String> getRespuestaInteger() {
        Map<Integer, String> respuestaBoolean = new LinkedHashMap<Integer, String>();
        respuestaBoolean.put(1, "Sí");
        respuestaBoolean.put(2, "No");

        return respuestaBoolean;
    }

    public Genero[] getSexo() {
        return Genero.values();
    }

    /**
     * Lista para opciones de seleccion entre tickets enviados por la sep, o
     * recibidos por los alumnos
     *
     * @return
     */
    public Map<String, String> getRespuestaEnviadosRecibidos() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("1", "Enviados");
        respuestaBoolean.put("0", "Recibidos");

        return respuestaBoolean;
    }

    /**
     * Lista para opciones de seleccion entre tickets enviados por los alumnos,
     * o recibidos por los usuarios del CAD
     *
     * @return
     */
    public Map<String, String> getRespuestaEnviadosRecibidosCad() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("2", "Enviados");
        respuestaBoolean.put("1", "Recibidos");

        return respuestaBoolean;
    }

    /**
     * Lista para opciones de seleccion entre tickets enviados por el cinvestav,
     * o recibidos por la sep
     *
     * @return
     */
    public Map<String, String> getRespuestaEnviadosRecibidos2n() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("2", "Enviados");
        respuestaBoolean.put("1", "Recibidos");

        return respuestaBoolean;
    }

    public Map<String, String> getRespuestaTipoCalificacion() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("1", "Ordinarias");
        respuestaBoolean.put("2", "Extraordinarias");

        return respuestaBoolean;
    }

    public Map<String, String> getAperturaCierreModuloOpciones() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("0", "Cierre");
        respuestaBoolean.put("1", "Apertura");

        return respuestaBoolean;
    }

    public Map<String, String> getCambioDeRolAprobadosOpciones() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("0", "Cambio de rol aprobados");
        return respuestaBoolean;
    }

    public Map<String, String> getCierreFacilitadoresOpciones() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("0", "Normal");
        respuestaBoolean.put("1", "En Recuperación");

        return respuestaBoolean;
    }

    public Map<String, String> getEstatus() {
        Map<String, String> respuestaBoolean = new LinkedHashMap<String, String>();
        respuestaBoolean.put("true", "Activo");
        respuestaBoolean.put("false", "Inactivo");

        return respuestaBoolean;
    }

    public Map<Integer, String> getBajas() {
        Map<Integer, String> respuestaBoolean = new LinkedHashMap<Integer, String>();
        respuestaBoolean.put(4, "Incumplimiento");
        respuestaBoolean.put(5, "Pasantia");
        respuestaBoolean.put(6, "Diversas");

        return respuestaBoolean;
    }

    public Map<String, String> getMesesAnio() {
        Map<String, String> meses = new LinkedHashMap<String, String>();
        meses.put("1", "Enero");
        meses.put("2", "Febrero");
        meses.put("3", "Marzo");
        meses.put("4", "Abril");
        meses.put("5", "Mayo");
        meses.put("6", "Junio");
        meses.put("7", "Julio");
        meses.put("8", "Agosto");
        meses.put("9", "Septiembre");
        meses.put("10", "Octubre");
        meses.put("11", "Noviembre");
        meses.put("12", "Diciembre");

        return meses;
    }

    public Map<Integer, String> getAnios() {
        Calendar now = Calendar.getInstance();   // Gets the current date and time
        int anio = now.get(Calendar.YEAR);

        Map<Integer, String> anios = new LinkedHashMap<Integer, String>();
        for (int i = 0; i <= 10; i++) {
            System.out.println("anio" + anio);
            anios.put(anio - i, (anio - i) + "");
        }

        return anios;
    }

    public Map<Integer, String> getNombreColumnasExcel() {

        Map<Integer, String> columnas = new LinkedHashMap<Integer, String>();
        columnas.put(0, "A");
        columnas.put(1, "B");
        columnas.put(2, "C");
        columnas.put(3, "D");
        columnas.put(4, "E");
        columnas.put(5, "F");
        columnas.put(6, "G");
        columnas.put(7, "H");
        columnas.put(8, "I");
        columnas.put(9, "J");
        columnas.put(10, "K");
        columnas.put(11, "L");
        columnas.put(12, "M");
        columnas.put(13, "N");
        columnas.put(14, "O");
        columnas.put(15, "P");
        columnas.put(16, "Q");
        columnas.put(17, "R");
        columnas.put(18, "S");
        columnas.put(19, "T");
        columnas.put(20, "U");
        columnas.put(21, "V");
        columnas.put(22, "W");
        columnas.put(23, "X");
        columnas.put(24, "Y");
        columnas.put(25, "Z");
        columnas.put(26, "AA");
        columnas.put(27, "AB");
        columnas.put(28, "AC");
        columnas.put(29, "AD");
        columnas.put(30, "AE");
        columnas.put(31, "AF");
        columnas.put(32, "AG");
        columnas.put(33, "AH");
        columnas.put(34, "AI");
        columnas.put(35, "AJ");
        columnas.put(36, "AK");
        columnas.put(37, "AL");
        columnas.put(38, "AM");
        columnas.put(39, "AN");
        columnas.put(40, "AO");
        columnas.put(41, "AP");
        columnas.put(42, "AQ");
        columnas.put(43, "AR");
        columnas.put(44, "AS");
        columnas.put(45, "AT");
        columnas.put(46, "AU");
        columnas.put(47, "AV");
        columnas.put(48, "AW");
        columnas.put(49, "AX");
        columnas.put(50, "AY");
        columnas.put(51, "AZ");

        return columnas;
    }

    public Map<String, String> getTipoPermiso() {

        Map<String, String> columnas = new LinkedHashMap<String, String>();
        columnas.put("a", "Atender");
        columnas.put("i", "Ingresar");
        return columnas;
    }

    public Map<String, String> getOpcionesCargas() {

        Map<String, String> columnas = new LinkedHashMap<String, String>();
        columnas.put("1", "Abonos");
        columnas.put("0", "Cargos");
        return columnas;
    }

    public Map<Integer, String> getTipoAbonos() {
        Map<Integer, String> respuesta = new LinkedHashMap<Integer, String>();
        respuesta.put(0, "Todos los abonos");
        respuesta.put(1, "Abonos en Administración");
        respuesta.put(2, "Abonos de Cargas Masivas");

        return respuesta;
    }

    public Map<Integer, String> getMaxPersonas() {
        Map<Integer, String> respuesta = new LinkedHashMap<Integer, String>();
        respuesta.put(100, "100");
        respuesta.put(500, "500");
        respuesta.put(1000, "1000");

        return respuesta;
    }

    public Map<Integer, String> getDiasReserva() {
        Map<Integer, String> respuesta = new LinkedHashMap<Integer, String>();
        respuesta.put(8, "8");
        respuesta.put(15, "15");
        respuesta.put(30, "30");
        respuesta.put(60, "60");
        respuesta.put(90, "90");

        return respuesta;
    }

    public Map<Integer, String> getUnidadReserva() {
        Map<Integer, String> respuesta = new LinkedHashMap<Integer, String>();
        respuesta.put(1, "30 minutos");
        respuesta.put(2, "1 hora");
        respuesta.put(3, "2 horas");
        respuesta.put(4, "3 horas");
        respuesta.put(5, "4 horas");
        respuesta.put(6, "5 horas");
        respuesta.put(7, "6 horas");
        respuesta.put(8, "7 horas");
        respuesta.put(9, "8 horas");
        respuesta.put(10, "1 día");

        return respuesta;
    }

    public Map<String, String> getHorarioAreaDias() {
        Map<String, String> respuesta = new LinkedHashMap<String, String>();
        respuesta.put("Lunes", "Lunes");
        respuesta.put("Martes", "Martes");
        respuesta.put("Miércoles", "Miércoles");
        respuesta.put("Jueves", "Jueves");
        respuesta.put("Viernes", "Viernes");
        respuesta.put("Sábado", "Sábado");
        respuesta.put("Domingo", "Domingo");
        return respuesta;
    }

    public Map<String, String> getHorarioAreaHoras() {
        Map<String, String> respuesta = new LinkedHashMap<String, String>();
        respuesta.put("1:00:00 a.m.", "1:00:00 a.m.");
        respuesta.put("2:00:00 a.m.", "2:00:00 a.m.");
        respuesta.put("3:00:00 a.m.", "3:00:00 a.m.");
        respuesta.put("4:00:00 a.m.", "4:00:00 a.m.");
        respuesta.put("5:00:00 a.m.", "5:00:00 a.m.");
        respuesta.put("6:00:00 a.m.", "6:00:00 a.m.");
        respuesta.put("7:00:00 a.m.", "7:00:00 a.m.");
        respuesta.put("8:00:00 a.m.", "8:00:00 a.m.");
        respuesta.put("9:00:00 a.m.", "9:00:00 a.m.");
        respuesta.put("10:00:00 a.m.", "10:00:00 a.m.");
        respuesta.put("11:00:00 a.m.", "11:00:00 a.m.");
        respuesta.put("12:00:00 p.m.", "12:00:00 p.m.");
        respuesta.put("1:00:00 p.m.", "1:00:00 p.m.");
        respuesta.put("2:00:00 p.m.", "2:00:00 p.m.");
        respuesta.put("3:00:00 p.m.", "3:00:00 p.m.");
        respuesta.put("4:00:00 p.m.", "4:00:00 p.m.");
        respuesta.put("5:00:00 p.m.", "5:00:00 p.m.");
        respuesta.put("6:00:00 p.m.", "6:00:00 p.m.");
        respuesta.put("7:00:00 p.m.", "7:00:00 p.m.");
        respuesta.put("8:00:00 p.m.", "8:00:00 p.m.");
        respuesta.put("9:00:00 p.m.", "9:00:00 p.m.");
        respuesta.put("10:00:00 p.m.", "10:00:00 p.m.");
        respuesta.put("11:00:00 p.m.", "11:00:00 p.m.");
        respuesta.put("12:00:00 a.m.", "12:00:00 a.m.");
        return respuesta;
    }
}
