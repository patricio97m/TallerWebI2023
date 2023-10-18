package com.tallerwebi.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;

@Entity
public class Partida{

    public Partida(){
        manoDelJugador = new Mano();
        manoDeLaIA = new Mano();
        puntosIa = 0;
        puntosJugador = 0;
        cartasJugadasJugador = new Mano();
        cartasJugadasIa = new Mano();
        limitePuntos = 15;
        quienEsMano = Jugador.J1;
        ronda = new Ronda();
        hayCambios = false;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manoDelJugador_id")
    private Mano manoDelJugador;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "manoDeLaIa_id")
    private Mano manoDeLaIA;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cartasJugadasJugador_id")
    private Mano cartasJugadasJugador;
    
    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cartasJugadasIa_id")
    private Mano cartasJugadasIa;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "ronda_id")
    private Ronda ronda;

    private int puntosJugador;
    private int puntosIa;
    private int limitePuntos;
    private Jugador quienEsMano;
    private Jugador ganador;
    private boolean hayCambios;
    private String ultimaJugada;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUltimaJugada() {
        return ultimaJugada;
    }

    public void setUltimaJugada(String ultimaJugada) {
        this.ultimaJugada = ultimaJugada;
    }

    public void setUltimaJugada(Jugada ultimaJugada) {
        TipoJugada tipoJugada = ultimaJugada.getTipoJugada();
        int indice = ultimaJugada.getIndex().intValue();

        if(tipoJugada == TipoJugada.ENVIDO){
            if(indice == 2){
                this.ultimaJugada = "Envido";
            }
            else if(indice == 3){
                this.ultimaJugada = "Real Envido";
            }
            else if(indice >= 4){
                this.ultimaJugada = "Falta Envido";
            }
            else{
                this.ultimaJugada = "Canto Invalido de Envido";
            }
        }
        else if(tipoJugada == TipoJugada.TRUCO){
            int estadoTruco = getEstadoTruco();
            if(estadoTruco == 1){
                this.ultimaJugada = "Truco";
            }
            else if(estadoTruco == 2){
                this.ultimaJugada = "Retruco";
            }
            else if(estadoTruco == 3){
                this.ultimaJugada = "Vale Cuatro";
            }
            else{
                this.ultimaJugada = "Canto Invalido de Truco";
            } 
        }
        else if(tipoJugada == TipoJugada.MAZO){
            this.ultimaJugada = "Me voy al Mazo";
        }
        else if(tipoJugada == TipoJugada.RESPUESTA){
            if(indice == 1){
                this.ultimaJugada = "Quiero";
            }
            else if(indice == 0){
                this.ultimaJugada = "No Quiero";
            }
            else{
                this.ultimaJugada = "Canto Invalido de Respuesta";
            }
        }
        else if(tipoJugada == TipoJugada.CARTA){
            this.ultimaJugada = null;
        }
        else{
            this.ultimaJugada = "Error al leer Ultima Jugada";
        }

    }

    public boolean hayCambios() {
        return hayCambios;
    }

    public void setHayCambios(boolean hayCambios) {
        this.hayCambios = hayCambios;
    }

    public Mano getManoDelJugador() {
        return manoDelJugador;
    }
    public void setManoDelJugador(Mano manoDelJugador) {
        this.manoDelJugador = manoDelJugador;
    }
    public Mano getManoDeLaIa() {
        return manoDeLaIA;
    }
    public void setManoDeLaIa(Mano manoDeLaIA) {
        this.manoDeLaIA = manoDeLaIA;
    }
    
    public int getEstadoTruco() {
        return ronda.getEstadoTruco();
    }

    public void setEstadoTruco(int estadoTruco){
        ronda.setEstadoTruco(estadoTruco);
    }

    public int getEstadoEnvido() {
        return ronda.getEstadoEnvido();
    }

    public void setEstadoEnvido(int estadoEnvido){
        ronda.setEstadoEnvido(estadoEnvido);
    }
    
    public int getPuntosJugador() {
        return puntosJugador;
    }
    public void setPuntosJugador(int puntosJugador) {
        this.puntosJugador = puntosJugador;
    }
    public int getPuntosIa() {
        return puntosIa;
    }
    public void setPuntosIa(int puntosIa) {
        this.puntosIa = puntosIa;
    }
    public boolean getCantoEnvido() {
        return ronda.getCantoEnvido();
    }

    public void setCantoEnvido(boolean cantoEnvido){
        ronda.setCantoEnvido(cantoEnvido);
    }

    public boolean getCantoTruco() {
        return ronda.getCantoTruco();
    }

    public void setCantoTruco(boolean cantoTruco){
        ronda.setCantoTruco(cantoTruco);
    }

    public Mano getCartasJugadasJugador() {
        return cartasJugadasJugador;
    }
    public void setCartasJugadasJugador(Mano cartasJugadasJugador) {
        this.cartasJugadasJugador = cartasJugadasJugador;
    }
    public Mano getCartasJugadasIa() {
        return cartasJugadasIa;
    }
    public void setCartasJugadasIa(Mano cartasJugadasIa) {
        this.cartasJugadasIa = cartasJugadasIa;
    }

    public int getLimitePuntos() {
        return limitePuntos;
    }
    public void setLimitePuntos(int limitePuntos) {
        this.limitePuntos = limitePuntos;
    }

    public Jugador getQuienEsMano() {
        return quienEsMano;
    }

    public void cambiarQuienEsMano() {
        if(quienEsMano == Jugador.IA){
            quienEsMano = Jugador.J1;
        }
        else if(quienEsMano == Jugador.J1){
            quienEsMano = Jugador.IA;
        }
    }

    public int getTrucoAQuerer() {
        return ronda.getTrucoAQuerer();
    }

    public void setTrucoAQuerer(int i){
        ronda.setTrucoAQuerer(i);
    }

    public int getEnvidoAQuerer() {
        return ronda.getEnvidoAQuerer();
    }

    public void setEnvidoAQuerer(int i){
        ronda.setEnvidoAQuerer(i);
    }

    public boolean getCantoFaltaEnvido() {
        return ronda.getCantoFaltaEnvido();
    }

    public void setCantoFaltaEnvido(boolean cantoEnvido){
        ronda.setCantoFaltaEnvido(cantoEnvido);
    }

    public int getPuntosGanador() {
        return Math.max(puntosIa, puntosJugador);
    }

    public void setRonda(Ronda ronda) {
        this.ronda = ronda;
    }

    public int getTiradaActual(){
        return ronda.getTiradaActual();
    }

    public void setTiradaActual(int i) {
        ronda.setTiradaActual(i);
    }

    public Jugador getGanadorEnvido(){
        if(getManoDeLaIa().getValorEnvido() > getManoDelJugador().getValorEnvido()){
            return Jugador.IA;
        }
        else if(getManoDelJugador().getValorEnvido() > getManoDeLaIa().getValorEnvido()){
            return Jugador.J1;
        }
        else{
            return quienEsMano;
        }
    }

    public void calcularGanadorTirada(int tiradaActual){
       if(cartasJugadasIa.getCarta(tiradaActual).getValorTruco() > cartasJugadasJugador.getCarta(tiradaActual).getValorTruco()){
            ronda.setResultadoTirada(tiradaActual, Jugador.IA);
        }
        else if(cartasJugadasJugador.getCarta(tiradaActual).getValorTruco() > cartasJugadasIa.getCarta(tiradaActual).getValorTruco()){
            ronda.setResultadoTirada(tiradaActual, Jugador.J1);
        }
        else{
            ronda.setResultadoTirada(tiradaActual, Jugador.Empate);
        } 
    }

    public Jugador hayGanadorDeLaRonda(){
        if(ronda.getTiradaActual() == 3){
            Jugador jugadorGanador = ganoDosTiradas();
            if(jugadorGanador != Jugador.NA){
                return jugadorGanador;
            }
            else if(ronda.getResultadoTirada(1) == Jugador.Empate){
                jugadorGanador = ganoSegundaOTercera();
                if(jugadorGanador != Jugador.NA){
                    return jugadorGanador;
                }
                else{
                    return quienEsMano;
                }
            }  
            else{
                return quienEsMano;
            }
        }
        else if(ronda.getTiradaActual() == 2){
            Jugador jugadorGanador = ganoDosTiradas();
            if(jugadorGanador != Jugador.NA){
                return jugadorGanador;
            }
            else if(ronda.getResultadoTirada(2) == Jugador.Empate){
                if(ronda.getResultadoTirada(1) != Jugador.Empate){
                    return ronda.getResultadoTirada(1);
                }
                else{
                    return Jugador.NA;
                }
            }
            else{
                return Jugador.NA;
            }
        } 
        else{
            return Jugador.NA;
        }     
    }

    private Jugador ganoSegundaOTercera() {
        if(ronda.getResultadoTirada(2) != Jugador.Empate){
            return ronda.getResultadoTirada(2);
        }
        else if(ronda.getResultadoTirada(3) != Jugador.Empate){
            return ronda.getResultadoTirada(3);
        }
        else return Jugador.NA;
    }

    private Jugador ganoDosTiradas() {
        if(ronda.getTiradaActual() >= 2){
            if(ronda.getResultadoTirada(1) == ronda.getResultadoTirada(2)){
                return ronda.getResultadoTirada(1);
            }
        }
        if(ronda.getTiradaActual() == 3){
            if(ronda.getResultadoTirada(3) == Jugador.Empate){
                return Jugador.NA;
            }

            if(ronda.getResultadoTirada(1) == ronda.getResultadoTirada(3)){
                return ronda.getResultadoTirada(1);
            }
            else if(ronda.getResultadoTirada(2) == ronda.getResultadoTirada(3)){
                return ronda.getResultadoTirada(2);
            }
            else{
                return Jugador.NA;
            } 
            
        }
        else{
            return Jugador.NA;
        }
    }

    public Jugador getGanador(){
        return ganador;
    } 

    public void setGanador(Jugador ganador){
        this.ganador = ganador;
    }

    public void chequearSiHayUnGanador(){
        if(puntosIa >= limitePuntos){
            ganador = Jugador.IA;
        }
        else if(puntosJugador >= limitePuntos){
            ganador = Jugador.J1;
        }
    }

}


