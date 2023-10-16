package com.tallerwebi.presentacion;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.ServicioPartida;

@Controller
public class ControladorPartida {

    private ServicioPartida servicioPartida;

    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida){
        this.servicioPartida = servicioPartida;
    }

    @RequestMapping("/partida")
    public ModelAndView irAPartida() {
        if(cookieIdPartida != null){
            Long idPartida = cookieIdPartida;
        }
        else{
            Long idPartida = servicioPartida.iniciarPartida();
        }
        
        return new ModelAndView("partida", servicioPartida.getDetallesPartida(idPartida));
    }
}
