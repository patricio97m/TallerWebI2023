package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Carta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String palo;
    private short numero;
    private short valorTruco;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getPalo() {
        return palo;
    }
    public void setPalo(String palo) {
        this.palo = palo;
    }
    public short getNumero() {
        return numero;
    }
    public void setNumero(short numero) {
        this.numero = numero;
    }
    public short getValorTruco() {
        return valorTruco;
    }
    public void setValorTruco(short valorTruco) {
        this.valorTruco = valorTruco;
    }
    
    
}

