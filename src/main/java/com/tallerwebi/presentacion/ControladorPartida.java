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
        
        ArrayList<String> manoDelJugador = servicioPartida.getManoDelJugador(idPartida);
        ArrayList<String> cartasJugadasIa = servicioPartida.getCartasJugadasIa(idPartida);
        ArrayList<String> cartasJugadasJugador = servicioPartida.getCartasJugadasJugador(idPartida);
        short puntosJugador = servicioPartida.getPuntosJugador(idPartida);
        short puntosIa = servicioPartida.getPuntosJugador(idPartida);
        short estadoTruco = servicioPartida.getEstadoTruco(idPartida);
        short estadoEnvido = servicioPartida.getEstadoEnvido(idPartida);

        ModelMap model = new ModelMap();
        model.put("manoDelJugador", manoDelJugador);
        model.put("cartasJugadasIa", cartasJugadasIa);
        model.put("cartasJugadasJugadas", cartasJugadasJugador);
        model.put("puntosJugador", puntosJugador);
        model.put("puntosIa", puntosIa);
        model.put("truco", estadoTruco);
        model.put("envido", estadoEnvido);
        return new ModelAndView("partida", model);
    }
}
