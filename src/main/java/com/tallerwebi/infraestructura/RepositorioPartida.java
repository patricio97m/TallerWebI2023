package com.tallerwebi.infraestructura;

import java.util.ArrayList;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Partida;

public interface RepositorioPartida {

    Long guardarNuevaPartida(Partida partida);
    Carta buscarCartaPorId(Long id);
    Partida buscarPartidaPorId(Long id);
    ArrayList<Carta> buscarCartasDelJugador(Long idPartida);
    ArrayList<Carta> buscarCartasDeLaIa(Long idPartida);
    void asignarCartasAlJugador(Long idPartida, ArrayList<Carta> cartasDelJugador);
    void asignarCartasALaIa(Long idPartida, ArrayList<Carta> cartasDeLaIA);
}
