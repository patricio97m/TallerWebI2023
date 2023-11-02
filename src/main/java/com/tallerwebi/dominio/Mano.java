package com.tallerwebi.dominio;

import java.util.ArrayList;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger logger = LogManager.getLogger(Mano.class);

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
        logger.error("Se calcula el envido");
        logger.error("La primer carta es: " + carta1.getNumero() + " de " + carta1.getPalo());
        logger.error("La segunda carta es: " + carta2.getNumero() + " de " + carta2.getPalo());
        logger.error("La tercer carta es: " + carta3.getNumero() + " de " + carta3.getPalo());

        logger.error("El palo de Carta 1 es igual al palo de Carta 2: " + (carta1.getPalo() == carta2.getPalo()));
        logger.error("El palo de Carta 2 es igual al palo de Carta 3: " + (carta2.getPalo() == carta3.getPalo()));
        logger.error("El palo de Carta 1 es igual al palo de Carta 3: " + (carta1.getPalo() == carta3.getPalo()));
        logger.error("El palo de Carta 1 es igual al palo de Carta 1: " + (carta1.getPalo() == carta1.getPalo()));
        short valorEnvido = 0;
        if(carta1.getPalo().equals(carta2.getPalo())){
            valorEnvido += 20;
            if(carta1.getPalo().equals(carta3.getPalo())){
                logger.error("El palo de las 3 cartas es el mismo");
                short[] listaValores = new short[3];
                listaValores[0] = carta1.getValorEnvido();
                listaValores[1] = carta2.getValorEnvido();
                listaValores[2] = carta3.getValorEnvido();

                Arrays.sort(listaValores);
                valorEnvido += listaValores[2] + listaValores[1];
            }
            else{
                logger.error("El palo de la carta 1 y 2 es el mismo");
                valorEnvido += (carta1.getValorEnvido() + carta2.getValorEnvido());
            }
        }
        else if(carta1.getPalo().equals(carta3.getPalo())){
            valorEnvido += 20;
            valorEnvido += (carta1.getValorEnvido() + carta3.getValorEnvido());  
        }
        else if(carta2.getPalo().equals(carta3.getPalo())){
            logger.error("El palo de la carta 2 y 3 es el mismo");
            valorEnvido += 20;
            valorEnvido += (carta2.getValorEnvido() + carta3.getValorEnvido());
        }
        else{
            logger.error("El palo de las 3 cartas es distinto");
            short[] listaValores = new short[3];
            listaValores[0] = carta1.getValorEnvido();
            listaValores[1] = carta2.getValorEnvido();
            listaValores[2] = carta3.getValorEnvido();

            Arrays.sort(listaValores);
            valorEnvido = listaValores[2];
        }
        logger.error("El valorEnvido final es: " + valorEnvido);
        return valorEnvido;
    }
    
}



