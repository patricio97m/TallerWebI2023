package com.tallerwebi.dominio;

public class Ronda {

    short estadoTruco;
    boolean cantoTruco;
    short estadoEnvido;
    boolean cantoEnvido;
    short numeroRonda;

    public Ronda(){
        estadoTruco = 1;
        estadoEnvido = 0;
        cantoTruco = false;
        cantoEnvido = false;
        numeroRonda = 1;
    }

    public short getNumeroRonda() {
        return numeroRonda;
    }

    public void setNumeroRonda(short numeroRonda) {
        this.numeroRonda = numeroRonda;
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
      

}
