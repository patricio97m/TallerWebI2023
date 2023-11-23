package com.tallerwebi.presentacion;

import java.io.IOException;
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
import com.tallerwebi.dominio.Usuario;
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
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");

        if (idPartida == null || !servicioPartida.partidaExiste(idPartida)) {
            idPartida = servicioPartida.iniciarPartida();
            
            request.getSession().setAttribute("idPartida", idPartida);
        }
        return new ModelAndView("partida", servicioPartida.getDetallesPartida(idPartida, usuario));
    }

    @PostMapping("/enviarJugada")
    @ResponseBody
    public String enviarJugada(HttpServletRequest request, HttpServletResponse response, @RequestParam("tipoJugada") String tipoJugada, @RequestParam("indice") int indice) {
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
        if(Objects.equals(tipoJugada, "Truco")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.TRUCO), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Envido")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.ENVIDO, indice), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Mazo")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.MAZO), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Carta")){
            System.out.println("Carta");
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.CARTA, indice), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Quiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 1), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "NoQuiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 0), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Potenciador")){
            try {
                if(usuario != null){
                    servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.POTENCIADOR, indice), Jugador.J1, usuario);
                }
                else{
                    // Devuelve la URL de redirección
                    return "{\"redirect\": \"/spring/login\"}";
                }    
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
    
        return servicioPartida.getDetallesPartidaJSON(idPartida, usuario);
    }

    @PostMapping("/recibirCambios")
    @ResponseBody
    public String recibirCambios(HttpServletRequest request){
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
        servicioPartida.calcularJugadaIA(idPartida);
        return servicioPartida.getDetallesPartidaJSON(idPartida, usuario);
    }
}
