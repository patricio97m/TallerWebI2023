package com.tallerwebi.presentacion;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

public class ControladorRegistro {
    public ModelAndView registrar(String email) {
        ModelMap modelo = new ModelMap();
        modelo = new ModelMap();
        String nombreVista = "";
        if (email.isEmpty()){
            modelo.put("mensaje", "El registro falló");
            nombreVista = "registro";
        }
        else {
            modelo.put("mensaje", "El registro fue exitoso");
            nombreVista = "login";
        }
        return new ModelAndView(nombreVista, modelo);
    }
}
