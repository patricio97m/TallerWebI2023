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
        return repositorioPartida.guardarNuevaPartida(partida);
    }

    @Override
    public ArrayList<Carta> getCartas(Long idPartida){
        return repositorioPartida.buscarCartasDelJugador(idPartida);
    }

    @Override
    public void repartirCartas(Long idPartida){
        ArrayList<Long> numerosDeCartas = generarNumerosDeCartas(6);
        ArrayList<Carta> cartasDelJugador = new ArrayList<Carta>();
        ArrayList<Carta> cartasDeLaIa = new ArrayList<Carta>();
        int index = 0;
        for (Long id : numerosDeCartas) {
            if(index < 3){
                cartasDelJugador.add(repositorioPartida.buscarCartaPorId(id));
            }
            else{
                cartasDeLaIa.add(repositorioPartida.buscarCartaPorId(id));
            }
            index++;
        }

        repositorioPartida.asignarCartasAlJugador(idPartida, cartasDelJugador);
        repositorioPartida.asignarCartasALaIa(idPartida, cartasDeLaIa);
    }

    private ArrayList<Long> generarNumerosDeCartas(int count){
        ArrayList<Long> randomNumbers = new ArrayList<>();
        Random random = new Random();

        while (randomNumbers.size() < count) {
            Long randomNumber = random.nextLong(40);
            if (!randomNumbers.contains(randomNumber)) {
                randomNumbers.add(randomNumber);
            }
        }

        return randomNumbers;
    }
}


