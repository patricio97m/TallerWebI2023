package com.tallerwebi.dominio;

import java.util.ArrayList;

import org.springframework.ui.ModelMap;

public interface ServicioPartida {

    Long iniciarPartida();
    void repartirCartas(Partida partida);
    ArrayList<String> getManoDelJugador(Long idPartida);
    ArrayList<String> getCartasJugadasJugador(Long idPartida);
    ArrayList<String> getCartasJugadasIa(Long idPartida);
    short getPuntosJugador(Long idPartida);
    short getPuntosIa(Long idPartida);
    short getEstadoTruco(Long idPartida);
    boolean getCantoTruco(Long idPartida);
    short getEstadoEnvido(Long idPartida);
    boolean getCantoEnvido(Long idPartida);
    ModelMap getDetallesPartida(Long idPartida);

    
}
