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
    
    
}


