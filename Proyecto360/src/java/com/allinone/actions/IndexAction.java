package com.allinone.actions;

public class IndexAction extends BaseAction {

    @Override
    public String execute() {

//        super.setVariablesPersonalizadas();
        System.out.println("isAuthenticated()" + isAuthenticated());
        if (isAuthenticated()) {

            String nombre = getPrincipalFullName();
            
            if (nombre != null) {
                return SUCCESS;
            } else {
                return "sinacceso";
            }


        } else {
            return ERROR;
        }
    }
    
    /**
     * Método que redirecciona al jsp que hace el login al portal de allinone
     * @return 
     */
    public String portal(){
        return "portal";
    }

    
}
