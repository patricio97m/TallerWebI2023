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

        Mano manoDelJugador = new Mano(cartasDelJugador);
        Mano manoDeLaIa = new Mano(cartasDeLaIa);
        repositorioPartida.guardarNuevaMano(manoDelJugador);
        repositorioPartida.guardarNuevaMano(manoDeLaIa);

        partida.setManoDelJugador(manoDelJugador);
        partida.setManoDeLaIa(manoDeLaIa);
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

    @Override
    public ArrayList<String> getManoDelJugador(Long idPartida) {
        ArrayList<Carta> cartasDelJugador = repositorioPartida.buscarCartasDelJugador(idPartida).getCartas();
        ArrayList<String> nombreCartasDelJugador = new ArrayList<String>();
        for (Carta carta : cartasDelJugador) {
            String nombre = carta.getPalo() + carta.getNumero();
            nombreCartasDelJugador.add(nombre);
        }
        return nombreCartasDelJugador;
    }

    @Override
    public ArrayList<String> getCartasJugadasJugador(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartasJugadasJugador'");
    }

    @Override
    public ArrayList<String> getCartasJugadasIa(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCartasJugadasIa'");
    }

    @Override
    public short getPuntosJugador(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPuntosJugador'");
    }

    @Override
    public short getPuntosIa(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPuntosIa'");
    }

    @Override
    public short getEstadoTruco(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstadoTruco'");
    }

    @Override
    public short getEstadoEnvido(Long idPartida) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstadoEnvido'");
    }
}


