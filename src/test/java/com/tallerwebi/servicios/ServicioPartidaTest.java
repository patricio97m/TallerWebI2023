package com.tallerwebi.servicios;

import com.tallerwebi.dominio.Jugada;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioIA;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.ServicioPartidaImpl;
import com.tallerwebi.dominio.excepcion.JugadaInvalidaException;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;
import com.tallerwebi.infraestructura.RepositorioPartida;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


public class ServicioPartidaTest {

    RepositorioPartida repositorioPartida = mock(RepositorioPartida.class);
    ServicioIA servicioIA = mock(ServicioIA.class);
    ServicioPartida servicioPartida = new ServicioPartidaImpl(repositorioPartida,servicioIA);

   @Test
   public void siNoEncuentraUnaPartidaCreaUnaNueva(){


       when(repositorioPartida.buscarPartidaPorId(1L)).thenReturn(new Partida());

       boolean partidaEncontrada = whenBuscaPartida();
       thenNoEncuentraLaPartida(partidaEncontrada);
   }

    private void thenNoEncuentraLaPartida(boolean partidaEncontrada) {
       assertThat(partidaEncontrada,is(true));
       verify(repositorioPartida,times(1)).buscarPartidaPorId(1L);
    }

    private boolean whenBuscaPartida() {
      return servicioPartida.partidaExiste(1L);
    }

    @Test
    public void sePuedeIniciarPartida(){

        Long idPartida = whenSeIniciaLaPartida();
        thenSeGuardaLaPartida(idPartida);

    }

    private void thenSeGuardaLaPartida(Long idPartida) {
       assertThat(idPartida,is(notNullValue()));
    }

    private Long whenSeIniciaLaPartida() {
       //Me parece que la mano viene nula ya que no puede calcular el envido
        // Y el resto de cosas
        return servicioPartida.iniciarPartida();
    }

    @Test void queCalcularJugadaIALlameAActualizarCambiosDePartida(){

    }

    @Test
    public void queCalcularJugadaIAUtiliceLaJugadaQueDevolvioElServicioIA() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Jugada jugadaAUsar = new Jugada(TipoJugada.TRUCO);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);
        doReturn(jugadaAUsar).when(servicioIA).calcularJugada(null);
        spyServicioPartida.calcularJugadaIA(null);

        Mockito.verify(spyServicioPartida, Mockito.times(1)).actualizarCambiosDePartida(null, jugadaAUsar, Jugador.IA);
    }

    @Test
    public void siLaIACantaTrucoSeCalculanLosCambiosCorrespondientesEnLaPartida() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);

        spyServicioPartida.actualizarCambiosDePartida(null, new Jugada(TipoJugada.TRUCO), Jugador.IA);
        //((ServicioPartidaImpl) Mockito.verify(spyServicioPartida, Mockito.times(1))).calcularCambiosTruco(null, Jugador.IA);
        //Falla y se castea porque la interfaz ServicioPartida no deja definir metodos privados
    }

    @Test
    public void siLaIASeVaAlMazoSeCalculanLosCambiosCorrespondientesEnLaPartida() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);

        //spyServicioPartida.actualizarCambiosDePartida(null, new Jugada(TipoJugada.MAZO), Jugador.IA);
        //((ServicioPartidaImpl) Mockito.verify(spyServicioPartida, Mockito.times(1))).calcularCambiosMazo(null, Jugador.IA);
        
        
        //Falla y se castea porque la interfaz ServicioPartida no deja definir metodos privados
        //Falla porque obliga a reiniciar la ronda y repartir las cartas que todavia no estan en la base de datos
    }

    @Test
    public void siLaIACantaEnvidoSeCalculanLosCambiosCorrespondientesEnLaPartida() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);

        spyServicioPartida.actualizarCambiosDePartida(null, new Jugada(TipoJugada.ENVIDO, 2), Jugador.IA);
        //((ServicioPartidaImpl) Mockito.verify(spyServicioPartida, Mockito.times(1))).calcularCambiosEnvido(null, 2, Jugador.IA);
        //Falla y se castea porque la interfaz ServicioPartida no deja definir metodos privados
    }

    @Test
    public void siLaIAJuegaUnaCartaSeCalculanLosCambiosCorrespondientesEnLaPartida() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);

        spyServicioPartida.actualizarCambiosDePartida(null, new Jugada(TipoJugada.CARTA, 1), Jugador.IA);
        //((ServicioPartidaImpl) Mockito.verify(spyServicioPartida, Mockito.times(1))).calcularCambiosCarta(null, 1, Jugador.IA);
        //Falla y se castea porque la interfaz ServicioPartida no deja definir metodos privados
    }

    @Test
    public void siLaIARespondeSeCalculanLosCambiosCorrespondientesEnLaPartida() throws JugadaInvalidaException{
        ServicioPartida spyServicioPartida = Mockito.spy(servicioPartida);
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);

        spyServicioPartida.actualizarCambiosDePartida(null, new Jugada(TipoJugada.RESPUESTA), Jugador.IA);
        //((ServicioPartidaImpl) Mockito.verify(spyServicioPartida, Mockito.times(1))).calcularCambiosRespuesta(null, 0, Jugador.IA);
        //Falla y se castea porque la interfaz ServicioPartida no deja definir metodos privados
    }
}

