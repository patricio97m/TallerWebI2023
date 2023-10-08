package com.tallerwebi.dominio;

public interface ServicioPartida {

    Long iniciarPartida();
    Mano getMano(Long idPartida);
    void repartirCartas(Partida partida);
}
