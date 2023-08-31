package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPrueba {

    /*
    *  definir URL
    *  asociar a un método
    * */


    @RequestMapping("/saludo")
    public ModelAndView irASaludo(){

        ModelMap modelo = new ModelMap();
        modelo.put("nombre", "Patricio");
        modelo.put("apellido", "Miranda");
        return new ModelAndView("prueba", modelo);

    }

    @RequestMapping("/despedida")
    public ModelAndView irADespedida(){

        return new ModelAndView("despedida");

    }


}
