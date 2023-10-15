package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Mano{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "carta1_id") 
    private Carta carta1;

    @ManyToOne
    @JoinColumn(name = "carta2_id")
    private Carta carta2;

    @ManyToOne
    @JoinColumn(name = "carta3_id")
    private Carta carta3;

    private short valorEnvido;

    public Mano(ArrayList<Carta> cartasDelJugador) {
        setCartas(cartasDelJugador);
    }

    public Mano() {
        carta1 = null;
        carta2 = null;
        carta3 = null;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Carta getCarta(int i){
        if(i == 1){
            return carta1;
        }
        else if(i == 2){
            return carta2;
        }
        else{
            return carta3;
        }
    }

    public void setCarta(int i, Carta carta){
        if(i == 1){
            carta1 = carta;
        }
        else if(i == 2){
            carta2 = carta;
        }
        else{
            carta3 = carta;
        }
    }

    public ArrayList<Carta> getCartas(){
        ArrayList<Carta> cartas = new ArrayList<Carta>();
        cartas.add(carta1);
        cartas.add(carta2);
        cartas.add(carta3);
        return cartas;
    }

    public short getValorEnvido() {
        return valorEnvido;
    }

    public void setValorEnvido(short valorEnvido) {
        this.valorEnvido = valorEnvido;
    }

    public void setCartas(ArrayList<Carta> cartas){
        int index = 1;
        for (Carta carta : cartas) {
            setCarta(index, carta);
            index++;
        }
        valorEnvido = calcularEnvido();
    }

    private short calcularEnvido() {
        short valorEnvido = 0;
        if(carta1.getPalo() == carta2.getPalo()){
            valorEnvido += 20;
            if(carta1.getPalo() == carta3.getPalo()){
                short[] listaValores = new short[3];
                listaValores[0] = carta1.getValorEnvido();
                listaValores[1] = carta2.getValorEnvido();
                listaValores[2] = carta3.getValorEnvido();

                Arrays.sort(listaValores);
                valorEnvido += listaValores[2] + listaValores[1];
            }
            else{
                valorEnvido += (carta1.getValorEnvido() + carta2.getValorEnvido());
            }
        }
        else if(carta1.getPalo() == carta3.getPalo()){
            valorEnvido += 20;
            valorEnvido += (carta1.getValorEnvido() + carta3.getValorEnvido());  
        }
        else if(carta2.getPalo() == carta3.getPalo()){
            valorEnvido += 20;
            valorEnvido += (carta2.getValorEnvido() + carta3.getValorEnvido());
        }
        else{
            short[] listaValores = new short[3];
            listaValores[0] = carta1.getValorEnvido();
            listaValores[1] = carta2.getValorEnvido();
            listaValores[2] = carta3.getValorEnvido();

            Arrays.sort(listaValores);
            valorEnvido = listaValores[2];
        }
        return valorEnvido;
    }
    
}



