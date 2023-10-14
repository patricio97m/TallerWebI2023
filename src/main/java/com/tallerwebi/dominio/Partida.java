package com.tallerwebi.dominio;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

    private short puntosJugador;
    private short puntosIa;
    private short limitePuntos;
    private Jugador quienEsMano;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    
    public short getEstadoTruco() {
        return ronda.getEstadoTruco();
    }

    public void setEstadoTruco(short estadoTruco){
        ronda.setEstadoTruco(estadoTruco);
    }

    public short getEstadoEnvido() {
        return ronda.getEstadoEnvido();
    }

    public void setEstadoEnvido(short estadoEnvido){
        ronda.setEstadoEnvido(estadoEnvido);
    }
    
    public short getPuntosJugador() {
        return puntosJugador;
    }
    public void setPuntosJugador(short puntosJugador) {
        this.puntosJugador = puntosJugador;
    }
    public short getPuntosIa() {
        return puntosIa;
    }
    public void setPuntosIa(short puntosIa) {
        this.puntosIa = puntosIa;
    }
    public boolean getCantoEnvido() {
        return ronda.getCantoEnvido();
    }
    public boolean getCantoTruco() {
        return ronda.getCantoTruco();
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

    public short getLimitePuntos() {
        return limitePuntos;
    }
    public void setLimitePuntos(short limitePuntos) {
        this.limitePuntos = limitePuntos;
    }

    public Jugador getQuienEsMano() {
        return quienEsMano;
    }

    public void setQuienEsMano(Jugador quienEsMano) {
        this.quienEsMano = quienEsMano;
    }

}


