package com.tallerwebi.dominio;

import com.tallerwebi.enums.TipoJugada;

import java.util.Objects;

public class Jugada {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugada jugada = (Jugada) o;
        return tipoJugada == jugada.tipoJugada && Objects.equals(index, jugada.index);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoJugada, index);
    }

    TipoJugada tipoJugada;

    //Tipo varia dependiendo de que jugada sea. Si es carta indica la posicion
    //Si es envido indica que envido
    //Si es truco no hace nada
    Integer index;

    public Jugada(TipoJugada tipoJugada, Integer index) {
        this.tipoJugada = tipoJugada;
        this.index = index;
    }

    public Jugada(TipoJugada tipoJugada) {
        this.tipoJugada = tipoJugada;
        this.index = 0;
    }

    public TipoJugada getTipoJugada() {
        return tipoJugada;
    }

    public void setTipoJugada(TipoJugada tipoJugada) {
        this.tipoJugada = tipoJugada;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
