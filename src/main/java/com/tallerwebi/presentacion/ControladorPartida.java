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
        long idPartida = servicioPartida.iniciarPartida();
        servicioPartida.repartirCartas(idPartida);
        ModelMap model = new ModelMap();
        ArrayList<Carta> Cartas = servicioPartida.getCartas(idPartida);
        ArrayList<String> nombreCartas = new ArrayList<String>();
        for (Carta carta : Cartas) {
            String nombre = carta.getPalo() + carta.getNumero();
            nombreCartas.add(nombre);
        }
        model.put("Cartas", nombreCartas);
        return new ModelAndView("partida", model);
    }
}
