package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorTorneo {

    @RequestMapping("/torneo")
    public ModelAndView irATorneo(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");

        model.put("usuario", usuarioAutenticado);

        return new ModelAndView("torneo",model);
    }
}
