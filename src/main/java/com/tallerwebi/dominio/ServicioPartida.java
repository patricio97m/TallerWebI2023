package com.tallerwebi.dominio;

import java.util.ArrayList;

public interface ServicioPartida {

    Long iniciarPartida();
    ArrayList<Carta> getCartas(Long idPartida);
    void repartirCartas(Long idPartida);
}
