package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.infraestructura.RepositorioPartida;

import java.util.ArrayList;
import java.util.Random;

import javax.transaction.Transactional;

@Service("servicioPartida")
@Transactional
public class ServicioPartidaImpl implements ServicioPartida{

    private RepositorioPartida repositorioPartida;

    @Autowired
    public ServicioPartidaImpl(RepositorioPartida repositorioPartida){
        this.repositorioPartida = repositorioPartida;
    }

    @Override
    public Long iniciarPartida(){
        Partida partida = new Partida();
        repartirCartas(partida);
        return repositorioPartida.guardarNuevaPartida(partida);
    }

    @Override
    public Mano getMano(Long idPartida){
        return repositorioPartida.buscarCartasDelJugador(idPartida);
    }

    @Override
    public void repartirCartas(Partida partida){
        ArrayList<Long> numerosDeCartas = generarNumerosDeCartas(6);
        ArrayList<Carta> cartasDelJugador = new ArrayList<Carta>();
        ArrayList<Carta> cartasDeLaIa = new ArrayList<Carta>();
        int index = 0;
        for (Long id : numerosDeCartas) {
            Carta carta = repositorioPartida.buscarCartaPorId(id);
            if(index < 3){
                cartasDelJugador.add(carta);
            }
            else{
                cartasDeLaIa.add(carta);
            }
            index++;
        }

        Mano manoDelJugador = new Mano();
        Mano manoDeLaIa = new Mano();
        manoDelJugador.setCartas(cartasDelJugador);
        manoDeLaIa.setCartas(cartasDeLaIa);
        repositorioPartida.guardarNuevaMano(manoDelJugador);
        repositorioPartida.guardarNuevaMano(manoDeLaIa);

        partida.setManoDelJugador(manoDelJugador);
        partida.setManoDeLaIa(manoDeLaIa);
    }

    private ArrayList<Long> generarNumerosDeCartas(int count){
        ArrayList<Long> randomNumbers = new ArrayList<>();
        Random random = new Random();

        while (randomNumbers.size() < count) {
            Long randomNumber = (long) random.nextInt(40);
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
            }
        }

        return randomNumbers;
    }
}


