package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

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

        Mano cartasJugadasJugador = new Mano();
        Mano cartasJugadasIa = new Mano();
        repositorioPartida.guardarNuevaMano(cartasJugadasJugador);
        repositorioPartida.guardarNuevaMano(cartasJugadasIa);

        Mano manoDelJugador = new Mano(cartasDelJugador);
        Mano manoDeLaIa = new Mano(cartasDeLaIa);
        repositorioPartida.guardarNuevaMano(manoDelJugador);
        repositorioPartida.guardarNuevaMano(manoDeLaIa);

        partida.setManoDelJugador(manoDelJugador);
        partida.setManoDeLaIa(manoDeLaIa);
        partida.setCartasJugadasJugador(cartasJugadasJugador);
        partida.setCartasJugadasIa(cartasJugadasIa);
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
        ArrayList<Carta> cartasJugadasJugador = repositorioPartida.buscarCartasJugadasJugador(idPartida).getCartas();
        ArrayList<String> nombreCartasJugadasJugador = new ArrayList<String>();
        for (Carta carta : cartasJugadasJugador) {
            String nombre = carta.getPalo() + carta.getNumero();
            nombreCartasJugadasJugador.add(nombre);
        }
        return nombreCartasJugadasJugador;
    }

    @Override
    public ArrayList<String> getCartasJugadasIa(Long idPartida) {
        ArrayList<Carta> cartasJugadasIa = repositorioPartida.buscarCartasJugadasIa(idPartida).getCartas();
        ArrayList<String> nombreCartasJugadasIa = new ArrayList<String>();
        for (Carta carta : cartasJugadasIa) {
            String nombre = carta.getPalo() + carta.getNumero();
            nombreCartasJugadasIa.add(nombre);
        }
        return nombreCartasJugadasIa;
    }

    @Override
    public short getPuntosJugador(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getPuntosJugador();
    }

    @Override
    public short getPuntosIa(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getPuntosIa();
    }

    @Override
    public short getEstadoTruco(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getEstadoTruco();
    }

    @Override
    public short getEstadoEnvido(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getEstadoEnvido();
    }

    @Override
    public boolean getCantoTruco(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getCantoTruco();
    }

    @Override
    public boolean getCantoEnvido(Long idPartida) {
        return repositorioPartida.buscarPartidaPorId(idPartida).getCantoEnvido();
    }

    @Override
    public ModelMap getDetallesPartida(Long idPartida) {
        ModelMap model = new ModelMap();
        model.put("manoDelJugador", getManoDelJugador(idPartida));
        model.put("cartasJugadasIa", getCartasJugadasIa(idPartida));
        model.put("cartasJugadasJugador", getCartasJugadasJugador(idPartida));
        model.put("puntosJugador", getPuntosJugador(idPartida));
        model.put("puntosIa", getPuntosIa(idPartida));
        model.put("truco", getEstadoTruco(idPartida));
        model.put("cantoTruco", getCantoTruco(idPartida));
        model.put("envido", getEstadoEnvido(idPartida));
        model.put("cantoEnvido", getCantoEnvido(idPartida));
        return model;
    }
}


