package com.tallerwebi.presentacion;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.ServicioPartida;
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
