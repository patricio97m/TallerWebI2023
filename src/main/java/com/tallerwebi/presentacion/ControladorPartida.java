package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Jugada;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.excepcion.JugadaInvalidaException;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControladorPartida {

    private ServicioPartida servicioPartida;

    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida) {
        this.servicioPartida = servicioPartida;
    }

    @RequestMapping("/partida")
    public ModelAndView irAPartida(HttpServletRequest request, HttpServletResponse response) {


        Long idPartida = obtenerIdPartidaDesdeCookie(request);

        if (idPartida == null || !servicioPartida.partidaExiste(idPartida)) {
            idPartida = servicioPartida.iniciarPartida();
            guardarIdPartidaEnCookie(idPartida, response);
        }

        return new ModelAndView("partida", servicioPartida.getDetallesPartida(idPartida));
    }

    @PostMapping("/enviarJugada")
    @ResponseBody
    public String enviarJugada(@RequestParam("tipoJugada") String tipoJugada, @RequestParam("indice") int indice, @RequestParam("idPartida") Long idPartida) {
        if(Objects.equals(tipoJugada, "Truco")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.TRUCO), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Envido")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.ENVIDO, indice), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Mazo")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.MAZO), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Carta")){
            System.out.println("Carta");
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.CARTA, indice), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Quiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 1), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "NoQuiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 0), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Potenciador")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.POTENCIADOR, indice), Jugador.J1);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
    
        return servicioPartida.getDetallesPartidaJSON(idPartida);
    }

    @PostMapping("/recibirCambios")
    @ResponseBody
    public String recibirCambios(@RequestParam("idPartida") Long idPartida){
        servicioPartida.calcularJugadaIA(idPartida);
        return servicioPartida.getDetallesPartidaJSON(idPartida);
    }

    private Long obtenerIdPartidaDesdeCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("idPartida".equals(cookie.getName())) {
                    return Long.parseLong(cookie.getValue());
                }
            }
        }
        return null;
    }

    private void guardarIdPartidaEnCookie(Long idPartida, HttpServletResponse response) {
        Cookie cookie = new Cookie("idPartida", idPartida.toString());
        cookie.setMaxAge(1800); // La cookie desaparece en media hora

        response.addCookie(cookie);
    }
}
