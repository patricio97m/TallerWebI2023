package com.tallerwebi.dominio;

public class Ronda {

    short estadoTruco;
    boolean cantoTruco;
    short estadoEnvido;
    boolean cantoEnvido;
    short tiradaActual;
    Jugador resultadoTirada1;
    Jugador resultadoTirada2;
    Jugador resultadoTirada3;

    public Ronda(){
        estadoTruco = 1;
        estadoEnvido = 0;
        cantoTruco = false;
        cantoEnvido = false;
        tiradaActual = 1;
        resultadoTirada1 = Jugador.NA;
        resultadoTirada2 = Jugador.NA;
        resultadoTirada3 = Jugador.NA;
    }

    public short getTiradaActual() {
        return tiradaActual;
    }

    public void setTiradaActual(short tiradaActual) {
        this.tiradaActual = tiradaActual;
    }

    public short getEstadoTruco() {
        return estadoTruco;
    }

    public void setEstadoTruco(short estadoTruco) {
        this.estadoTruco = estadoTruco;
    }

    public short getEstadoEnvido() {
        return estadoEnvido;
    }

    public void setEstadoEnvido(short estadoEnvido) {
        this.estadoEnvido = estadoEnvido;
    }

    public boolean getCantoTruco() {
        return cantoTruco;
    }

    public void setCantoTruco(boolean cantoTruco) {
        this.cantoTruco = cantoTruco;
    }

    public boolean getCantoEnvido() {
        return cantoEnvido;
    }

    public void setCantoEnvido(boolean cantoEnvido) {
        this.cantoEnvido = cantoEnvido;
    }
      
    public Jugador getResultadoTirada1() {
        return resultadoTirada1;
    }

    public void setResultadoTirada1(Jugador resultadoTirada1) {
        this.resultadoTirada1 = resultadoTirada1;
    }

    public Jugador getResultadoTirada2() {
        return resultadoTirada2;
    }

    public void setResultadoTirada2(Jugador resultadoTirada2) {
        this.resultadoTirada2 = resultadoTirada2;
    }

    public Jugador getResultadoTirada3() {
        return resultadoTirada3;
    }

    public void setResultadoTirada3(Jugador resultadoTirada3) {
        this.resultadoTirada3 = resultadoTirada3;
    }
}
