package com.tallerwebi.dominio;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Partida{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private ArrayList<Carta> cartasDelJugador = new ArrayList<Carta>();
    private ArrayList<Carta> cartasDeLaIA = new ArrayList<Carta>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public ArrayList<Carta> getCartasDelJugador() {
        return cartasDelJugador;
    }
    public void setCartasDelJugador(ArrayList<Carta> cartasDelJugador) {
        this.cartasDelJugador = cartasDelJugador;
    }
    public ArrayList<Carta> getCartasDeLaIa() {
        return cartasDeLaIA;
    }
    public void setCartasDeLaIa(ArrayList<Carta> cartasDeLaIA) {
        this.cartasDeLaIA = cartasDeLaIA;
    }
    
    
}


