package com.tallerwebi.dominio;

import com.tallerwebi.enums.TipoJugada;

public class Jugada {

    TipoJugada nombre;

    //Tipo varia dependiendo de que jugada sea. Si es carta indica la posicion
    //Si es envido indica que envido
    //Si es truco no hace nada
    Integer tipo;

    public Jugada(TipoJugada nombre, Integer tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }
}
