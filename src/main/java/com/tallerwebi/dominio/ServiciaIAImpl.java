package com.tallerwebi.dominio;

import com.tallerwebi.enums.TipoJugada;
import com.tallerwebi.infraestructura.RepositorioPartida;

import java.util.ArrayList;

public class ServiciaIAImpl implements ServicioIA {

    private RepositorioPartida repositorioPartida;

    @Override
    public Jugada tirarCartaAleatoria(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }
        ArrayList<Carta> cartasDeLaIA = manoIA.getCartas();
        int numero = (int)(Math.random()*3);

        if(numero == 0){
            return new Jugada(TipoJugada.CARTA, 1);
        }
        if(numero == 1){
            return new Jugada(TipoJugada.CARTA, 2);
        }
        if(numero == 2){
            return new Jugada(TipoJugada.CARTA, 3);
        }

        return null;
    }

    @Override
    public Jugada tirarMejorCarta(Long idPartida){


        return null;
    }
    @Override
    public Jugada tirarPeorCarta(Long idPartida){
        return null;
    }

    @Override
    public Jugada cantarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada cantarEnvido(Long idPartida) {
        return null;
    }

    @Override
    public Jugada aceptarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada rechazarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada aceptarEnvido(Long idPartida) {
        return null;
    }

    @Override
    public Jugada rechazarEnvido(Long idPartida) {
        return null;
    }


}
