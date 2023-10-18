package com.tallerwebi.dominio;

public interface ServicioIA {

    public Jugada calcularJugada(Long idPartida);
    public Jugada respuestaAleatoria();
    public Jugada tirarCartaAleatoria(Long idPartida);
    public boolean verificarTruco(Long idPartida);
    public Jugada cantarTruco();

    public Jugada cantarEnvido(Long idPartida,short indice);

    public short calcularEnvido(Long idPartida);
    public Jugada tirarMejorCarta(Long idPartida);

    public Jugada tirarPeorCarta(Long idPartida);

    public Jugada aceptarTruco(Long idPartida);

    public Jugada rechazarTruco(Long idPartida);

    public Jugada aceptarEnvido(Long idPartida);

    public Jugada rechazarEnvido(Long idPartida);
}
