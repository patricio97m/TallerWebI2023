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
        Jugada jugadaAUsar = new Jugada(TipoJugada.TRUCO);

        doReturn(jugadaAUsar).when(servicioIA).calcularJugada(null);

        Mockito.verify(servicioPartida, Mockito.times(1)).actualizarCambiosDePartida(null, jugadaAUsar, Jugador.IA);;
    }
}

