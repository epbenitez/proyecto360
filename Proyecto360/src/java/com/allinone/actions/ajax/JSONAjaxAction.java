
package com.allinone.actions.ajax;

import com.allinone.actions.BaseAction;
import java.util.ArrayList;
import java.util.List;

/**
 * Accion base con las propiedades requeridas para completar acciones con uso de JSON.
 *
 * @author Patricia Benitez
 */
public abstract class JSONAjaxAction extends BaseAction {

    private List<String> jsonResult;
    private String jsonTotalRecords;
    private String jsonDisplayRecords;
    private String jsonDraw;
    

    /** La ejecución de la acción fue satisfactoria, mostrar el resultado JSON. */
    protected static final String SUCCESS_JSON = "success_json";
    protected static final String ERROR_JSON = "error_json";
    /**
     * Inicializa el objeto <code>JSONAjaxAction</code>.
     */
    public JSONAjaxAction() {
        jsonResult = new ArrayList<String>();
        jsonTotalRecords = new String();
        jsonDisplayRecords = new String();
        jsonDraw = new String();
    }

    /**
     * Obtiene el valor de  jsonResult
     *
     * @return el valor de jsonResult
     */
    public List<String> getJsonResult() {
        return jsonResult;
    }

    /**
     * Establece el valor de  jsonResult
     *
     * @param jsonResult nuevo valor de jsonResult
     */
    public void setJsonResult(List<String> jsonResult) {
        this.jsonResult = jsonResult;
    }

    /**
     *
     * @return
     */
    public String getJsonTotalRecords() {
        return jsonTotalRecords;
    }

    /**
     *
     * @param jsonTotalRecords
     */
    public void setJsonTotalRecords(String jsonTotalRecords) {
        this.jsonTotalRecords = jsonTotalRecords;
    }

    public String getJsonDisplayRecords() {
        return jsonDisplayRecords;
    }

    public void setJsonDisplayRecords(String jsonDisplayRecords) {
        this.jsonDisplayRecords = jsonDisplayRecords;
    }

    public String getJsonDraw() {
        return jsonDraw;
    }

    public void setJsonDraw(String jsonDraw) {
        this.jsonDraw = jsonDraw;
    }

}