package com.tallerwebi.dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.tallerwebi.dominio.excepcion.JugadaInvalidaException;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;
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

    //Sistema de la Partida

    @Override
    public Long iniciarPartida(){
        Partida partida = new Partida();
        nuevaRonda(partida);
        return repositorioPartida.guardarNuevaPartida(partida);
    }

    @Override
    public void nuevaRonda(Partida partida){
        reiniciarRonda(partida);
        repartirCartas(partida);
        partida.cambiarQuienEsMano();
    }

    @Override
    public void reiniciarRonda(Partida partida) {
        Ronda ronda = new Ronda();
        repositorioPartida.guardarNuevaRonda(ronda);
        partida.setRonda(ronda);
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

    @Override
    public void actualizarCambiosDePartida(Long idPartida, Jugada jugada, Jugador jugador) throws JugadaInvalidaException {
        TipoJugada tipoJugada = jugada.getTipoJugada();
        Integer index = jugada.getIndex();

        if(tipoJugada == TipoJugada.ENVIDO){
            calcularCambiosEnvido(idPartida, index);
        }
        else if(tipoJugada == TipoJugada.TRUCO){
            calcularCambiosTruco(idPartida);
        }
        else if(tipoJugada == TipoJugada.RESPUESTA){
            calcularCambiosRespuesta(idPartida, index, jugador);
        }
        else if(tipoJugada == TipoJugada.CARTA){
            calcularCambiosCarta(idPartida, index, jugador);
        }
        else if(tipoJugada == TipoJugada.MAZO){
            calcularCambiosMazo(idPartida, jugador);
        }
        else{
            throw new JugadaInvalidaException("El tipo de jugada realizada no existe");
        }

        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);
        partida.chequearSiHayUnGanador();
    }

    







    //Getters y métodos auxiliares

    private void calcularCambiosMazo(Long idPartida, Jugador jugador) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);

        if(partida.getEstadoEnvido() == 0 && noSeJugoNingunaCarta(partida)){
            if(jugador == Jugador.IA){
                partida.setPuntosJugador(partida.getPuntosJugador() + 1);
            }
            else if(jugador == Jugador.J1){
                partida.setPuntosIa(partida.getPuntosIa() + 1);
            }
        }
        else if(partida.getEstadoEnvido() != -1){
            if(jugador == Jugador.IA){
                partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoEnvido());
            }
            else if(jugador == Jugador.J1){
                partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoEnvido());
            }
        }
        
        if(jugador == Jugador.IA){
            partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoTruco());
        }
        else if(jugador == Jugador.J1){
            partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoTruco());
        }

        nuevaRonda(partida);
    }

    private void calcularCambiosCarta(Long idPartida, Integer index, Jugador jugador) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);
        int tiradaActual = partida.getTiradaActual();
        Mano manoDelJugador;
        Mano cartasJugadasDelJugador;
        Mano cartasJugadasDelRival;
        if(jugador == Jugador.IA){
            manoDelJugador = partida.getManoDeLaIa();
            cartasJugadasDelJugador = partida.getCartasJugadasIa();
            cartasJugadasDelRival = partida.getCartasJugadasJugador();
        }
        else{
            manoDelJugador = partida.getManoDelJugador();
            cartasJugadasDelJugador = partida.getCartasJugadasJugador();
            cartasJugadasDelRival = partida.getCartasJugadasIa();
        }

        cartasJugadasDelJugador.setCarta(tiradaActual, manoDelJugador.getCarta(index));
        manoDelJugador.setCarta(index, null);

        if(cartasJugadasDelRival.getCarta(tiradaActual) != null){
            partida.calcularGanadorTirada(tiradaActual);
            Jugador ganadorRonda = partida.hayGanadorDeLaRonda();

            if(ganadorRonda == Jugador.NA){
                partida.setTiradaActual(tiradaActual++);
            }
            else if(ganadorRonda == Jugador.IA){
                partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoTruco());
                nuevaRonda(partida);
                    
            }
            else if(ganadorRonda == Jugador.J1){
                partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoTruco());
                nuevaRonda(partida);
            }    
        }
    }

    private void calcularCambiosRespuesta(Long idPartida, Integer index, Jugador jugador) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);

        if(index == 0){
            if(partida.getCantoEnvido()){
                if(jugador == Jugador.IA){
                    partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoEnvido());
                }
                else if(jugador == Jugador.J1){
                    partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoEnvido());
                }
                partida.setCantoEnvido(false);
                partida.setEstadoEnvido(-1);
            }
            else if(partida.getCantoTruco()){
                if(jugador == Jugador.IA){
                    partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoTruco());
                }
                else if(jugador == Jugador.J1){
                    partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoTruco());
                }
                nuevaRonda(partida);
            }
        }
        else{
            if(partida.getCantoEnvido()){
                Jugador ganadorEnvido = partida.getGanadorEnvido();

                if(partida.getCantoFaltaEnvido()){
                    partida.setEstadoEnvido(partida.getEnvidoAQuerer());
                }
                else{
                    partida.setEstadoEnvido(partida.getEstadoEnvido() + partida.getEnvidoAQuerer());
                }
                
                if(ganadorEnvido == Jugador.IA){
                    partida.setPuntosJugador(partida.getPuntosJugador() + partida.getEstadoEnvido());
                }
                else if(ganadorEnvido == Jugador.J1){
                    partida.setPuntosIa(partida.getPuntosIa() + partida.getEstadoEnvido());
                }

                partida.setEnvidoAQuerer(0);
                partida.setCantoEnvido(false);
                partida.setEstadoEnvido(-1);
                
            }
            else if(partida.getCantoTruco()){
                partida.setEstadoTruco(partida.getEstadoTruco() + partida.getTrucoAQuerer());
                partida.setTrucoAQuerer(0);
                partida.setCantoTruco(false);
            }
               
        }
    }

    private void calcularCambiosEnvido(Long idPartida, Integer index) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);
        if(partida.getCantoEnvido()){
            partida.setEstadoEnvido(partida.getEstadoEnvido() + partida.getEnvidoAQuerer());
        }
        else{
            partida.setCantoEnvido(true);
        }

        if(index < 4){
            //Envido o Real Envido
            partida.setEnvidoAQuerer(index.intValue());
        }
        else{
            //Falta Envido
            partida.setCantoFaltaEnvido(true);
            partida.setEnvidoAQuerer(partida.getLimitePuntos() - partida.getPuntosGanador());
        }
    }

    private void calcularCambiosTruco(Long idPartida) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);
        if(partida.getCantoTruco()){
            partida.setEstadoTruco(partida.getEstadoTruco() + partida.getTrucoAQuerer());
        }
        partida.setTrucoAQuerer(1);
    }

    private boolean noSeJugoNingunaCarta(Partida partida) {
        if(partida.getCartasJugadasIa().getCarta(1) == null && partida.getCartasJugadasJugador().getCarta(1) == null){
            return true;
        }
        else{
            return false;
        } 
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
            if(carta != null){
                String nombre = carta.getPalo() + carta.getNumero();
                nombreCartasDelJugador.add(nombre);
            }
            
        }
        return nombreCartasDelJugador;
    }

    @Override
    public ArrayList<String> getCartasJugadasJugador(Long idPartida) {
        ArrayList<Carta> cartasJugadasJugador = repositorioPartida.buscarCartasJugadasJugador(idPartida).getCartas();
        ArrayList<String> nombreCartasJugadasJugador = new ArrayList<String>();
        for (Carta carta : cartasJugadasJugador){
            if(carta != null){
                String nombre = carta.getPalo() + carta.getNumero();
                nombreCartasJugadasJugador.add(nombre);
            }
            
        }
        return nombreCartasJugadasJugador;
    }

    @Override
    public ArrayList<String> getCartasJugadasIa(Long idPartida) {
        ArrayList<Carta> cartasJugadasIa = repositorioPartida.buscarCartasJugadasIa(idPartida).getCartas();
        ArrayList<String> nombreCartasJugadasIa = new ArrayList<String>();
        for (Carta carta : cartasJugadasIa){
            if(carta != null){
                String nombre = carta.getPalo() + carta.getNumero();
                nombreCartasJugadasIa.add(nombre);
            }
            
        }
        return nombreCartasJugadasIa;
    }

    @Override
    public ModelMap getDetallesPartida(Long idPartida) {
        Partida partida = repositorioPartida.buscarPartidaPorId(idPartida);
        ModelMap model = new ModelMap();
        model.put("manoDelJugador", getManoDelJugador(idPartida));
        model.put("cartasJugadasIa", getCartasJugadasIa(idPartida));
        model.put("cartasJugadasJugador", getCartasJugadasJugador(idPartida));
        model.put("puntosJugador", partida.getPuntosJugador());
        model.put("puntosIa", partida.getPuntosIa());
        model.put("truco", partida.getEstadoTruco());
        model.put("trucoAQuerer", partida.getTrucoAQuerer());
        model.put("cantoTruco", partida.getCantoTruco());
        model.put("envido", partida.getEstadoEnvido());
        model.put("envidoAQuerer", partida.getEnvidoAQuerer());
        model.put("cantoEnvido", partida.getCantoEnvido());
        model.put("cantoFaltaEnvido", partida.getCantoFaltaEnvido());
        model.put("ganador", partida.getGanador());
        return model;
    }
}


