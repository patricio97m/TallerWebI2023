package repositorio;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.infraestructura.RepositorioPartida;
import com.tallerwebi.infraestructura.RepositorioPartidaImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;

//No tenemos SpringTest
public class RepositorioPartidaTest extends SpringTest{


    @Autowired
    RepositorioPartida repositorioPartida;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaPartida(){
    Partida partida = new Partida();
    Partida partidaMock = mock(new Partida());

        repositorioPartida.guardarNuevaPartida(partida);

        assertThat(partida.getId(),isNotNull());

    }

    @Test
    public void puedoBuscarUnaPartidaPorId(){

        //given
        Partida partida1 = new Partida();
        Partida partida2 = new Partida();

        session().save(partida1);
        session().save(partida2);

        //when
        Partida partidaBuscada = repositorioPartida.buscarPartidaPorId(1L);

        //then
          assertThat(partidaBuscada,isNotNull());
    }

}
