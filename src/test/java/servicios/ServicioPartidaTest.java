package servicios;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioIA;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.ServicioPartidaImpl;
import com.tallerwebi.infraestructura.RepositorioPartida;
import org.junit.jupiter.api.Test;
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
