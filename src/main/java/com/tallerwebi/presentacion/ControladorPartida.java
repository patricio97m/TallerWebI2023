package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPartida {
    @RequestMapping("/partida")
    public ModelAndView irAPartida() {

        return new ModelAndView("partida");
    }

    @RequestMapping("/crear-sala")
    public ModelAndView crearSala(){
        return new ModelAndView("crear-sala");
    }

    @RequestMapping("/unirse-a-sala")
    public ModelAndView unirseASala(){
        return new ModelAndView("crear-sala");
    }
}
