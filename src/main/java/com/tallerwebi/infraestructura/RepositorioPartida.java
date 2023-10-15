package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Ronda;

public interface RepositorioPartida {

    Long guardarNuevaPartida(Partida partida);
    Long guardarNuevaMano(Mano mano);
    Long guardarNuevaRonda(Ronda ronda);

    Carta buscarCartaPorId(Long id);
    Partida buscarPartidaPorId(Long id);
    Mano buscarCartasDelJugador(Long idPartida);
    Mano buscarCartasDeLaIa(Long idPartida);
    
    void asignarCartasAlJugador(Long idPartida, Mano cartasDelJugador);
    void asignarCartasALaIa(Long idPartida, Mano cartasDeLaIA);
	Mano buscarCartasJugadasJugador(Long idPartida);
    Mano buscarCartasJugadasIa(Long idPartida);
}
