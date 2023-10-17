package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.tallerwebi.enums.Jugador;

@Entity
public class Ronda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    int estadoTruco;
    int trucoAQuerer;
    boolean cantoTruco;
    int estadoEnvido;
    int envidoAQuerer;
    boolean cantoFaltaEnvido;
    boolean cantoEnvido;

    int tiradaActual;
    Jugador resultadoTirada1;
    Jugador resultadoTirada2;
    Jugador resultadoTirada3;

    public Ronda(){
        estadoTruco = 1;
        estadoEnvido = 0;
        cantoTruco = false;
        cantoEnvido = false;
        trucoAQuerer = 0;
        envidoAQuerer = 0;
        cantoFaltaEnvido = false;
        tiradaActual = 1;
        resultadoTirada1 = Jugador.NA;
        resultadoTirada2 = Jugador.NA;
        resultadoTirada3 = Jugador.NA;
    }

    public int getTiradaActual() {
        return tiradaActual;
    }

    public void setTiradaActual(int tiradaActual) {
        this.tiradaActual = tiradaActual;
        if(this.tiradaActual > 1){
            estadoEnvido = -1;
        }
    }

    public int getEstadoTruco() {
        return estadoTruco;
    }

    public void setEstadoTruco(int estadoTruco) {
        this.estadoTruco = estadoTruco;
    }

    public int getEstadoEnvido() {
        return estadoEnvido;
    }

    public void setEstadoEnvido(int estadoEnvido) {
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

    public int getTrucoAQuerer() {
        return trucoAQuerer;
    }

    public void setTrucoAQuerer(int trucoAQuerer) {
        this.trucoAQuerer = trucoAQuerer;
    }

    public int getEnvidoAQuerer() {
        return envidoAQuerer;
    }

    public void setEnvidoAQuerer(int envidoAQuerer) {
        this.envidoAQuerer = envidoAQuerer;
    }

    public boolean getCantoFaltaEnvido() {
        return cantoFaltaEnvido;
    }

    public void setCantoFaltaEnvido(boolean cantoFaltaEnvido) {
        this.cantoFaltaEnvido = cantoFaltaEnvido;
    }

    public Jugador getResultadoTirada(int tiradaActual){
        if(tiradaActual == 1){
            return resultadoTirada1;
        }
        else if(tiradaActual == 2){
            return resultadoTirada2;
        }
        else{
            return resultadoTirada3;
        }
    }

    public void setResultadoTirada(int tiradaActual, Jugador jugador){
        if(tiradaActual == 1){
            resultadoTirada1 = jugador;
        }
        else if(tiradaActual == 2){
            resultadoTirada2 = jugador;
        }
        else{
            resultadoTirada3 = jugador;
        }
    }
}
