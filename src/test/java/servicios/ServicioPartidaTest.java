package servicios;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioPartida;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;


public class ServicioPartidaTest {

    RepositorioPartida repositorioPartida = mock(RepositorioPartida.class);
    ServicioIA servicioIA = mock(ServicioIA.class);
    ServicioPartida servicioPartida = new ServicioPartidaImpl(repositorioPartida,servicioIA);

   @Test
   public void buscarPartidaExistente(){


       when(repositorioPartida.buscarPartidaPorId(1L)).thenReturn(new Partida());

       boolean partidaEncontrada = whenBuscaPartida();
       thenEncuentraLaPartida(partidaEncontrada);
   }

    private void thenEncuentraLaPartida(boolean partidaEncontrada) {
       assertThat(partidaEncontrada,is(true));
       verify(repositorioPartida,times(1)).buscarPartidaPorId(1L);
    }

    private boolean whenBuscaPartida() {
      return servicioPartida.partidaExiste(1L);
    }

    @Test
    public void sePuedeIniciarPartida(){
       Carta carta1 = new Carta();
       carta1.setId(1L);
       carta1.setPalo("Espada");
       carta1.setNumero((short) 1);
       carta1.setValorTruco((short) 1);
       carta1.setValorEnvido((short) 1);

        Carta carta2 = new Carta();
        carta2.setId(2L);
        carta2.setPalo("Espada");
        carta2.setNumero((short) 2);
        carta2.setValorTruco((short) 2);
        carta2.setValorEnvido((short) 2);

        Carta carta3 = new Carta();
        carta3.setId(3L);
        carta3.setPalo("Espada");
        carta3.setNumero((short) 3);
        carta3.setValorTruco((short) 3);
        carta3.setValorEnvido((short) 3);

        Mano manoJugador = new Mano();
        manoJugador.setCarta(1,carta1);
        manoJugador.setCarta(2,carta2);
        manoJugador.setCarta(3,carta3);

        ArrayList<Carta> cartasDelJugador = new ArrayList<>();
        cartasDelJugador.add(carta1);
        cartasDelJugador.add(carta2);
        cartasDelJugador.add(carta3);

        ArrayList<String> nombreCartasDelJugador = new ArrayList<String>();
        String nombre1 = carta1.getPalo() + carta1.getNumero();
        nombreCartasDelJugador.add(nombre1);

        String nombre2 = carta2.getPalo() + carta2.getNumero();
        nombreCartasDelJugador.add(nombre2);

        String nombre3 = carta3.getPalo() + carta3.getNumero();
        nombreCartasDelJugador.add(nombre3);



        when(servicioPartida.getManoDelJugador(1L)).thenReturn(nombreCartasDelJugador);
        Long idPartida = whenSeIniciaLaPartida();
        thenSeGuardaLaPartida(idPartida);

    }

    private void thenSeGuardaLaPartida(Long idPartida) {
       assertThat(idPartida,is(notNull()));
    }

    private Long whenSeIniciaLaPartida() {
       //Me parece que la mano viene nula ya que no puede calcular el envido
        // Y el resto de cosas
        return servicioPartida.iniciarPartida();
    }
}
