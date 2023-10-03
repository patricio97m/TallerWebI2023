package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorTorneo {

    @RequestMapping("/torneo")
    public ModelAndView irATorneo() {

        return new ModelAndView("torneo");
    }
}
