package com.tallerwebi.dominio;

public interface ServicioIA {


    public Jugada tirarCartaAleatoria(Long idPartida);

    public Jugada cantarTruco(Long idPartida);

    public Jugada cantarEnvido(Long idPartida);

    public Jugada tirarMejorCarta(Long idPartida);

    public Jugada tirarPeorCarta(Long idPartida);

    public Jugada aceptarTruco(Long idPartida);

    public Jugada rechazarTruco(Long idPartida);

    public Jugada aceptarEnvido(Long idPartida);

    public Jugada rechazarEnvido(Long idPartida);
}
