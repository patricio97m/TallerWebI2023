package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorCrearSala {

    @RequestMapping("/crear-sala")
    public ModelAndView irACrearSala(){
        return new ModelAndView("crear-sala");
    }




}
