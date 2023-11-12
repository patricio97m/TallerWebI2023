package repositorio;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.infraestructura.RepositorioPartida;
import com.tallerwebi.infraestructura.RepositorioPartidaImpl;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.mock;

//No tenemos SpringTest
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

public class RepositorioPartidaTest{


    @Autowired
    RepositorioPartida repositorioPartida;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void queSePuedaGuardarUnaPartida(){
    Partida partida = new Partida();
    Partida partidaMock = mock();

        repositorioPartida.guardarNuevaPartida(partida);

        assertThat(partida.getId(),isNotNull());

    }

    @Test
    @Transactional
    @Rollback
    public void puedoBuscarUnaPartidaPorId(){

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

        Mano manoIA = new Mano();
        manoJugador.setCarta(1,carta1);
        manoJugador.setCarta(2,carta2);
        manoJugador.setCarta(3,carta3);

        //given
        repositorioPartida.guardarNuevaMano(manoIA);
        repositorioPartida.guardarNuevaMano(manoJugador);

        Partida partida1 = new Partida();
        Partida partida2 = new Partida();

        partida1.setManoDelJugador(manoJugador);
        partida1.setManoDeLaIa(manoIA);

        partida2.setManoDelJugador(manoJugador);
        partida2.setManoDeLaIa(manoIA);

        sessionFactory.getCurrentSession().save(partida1);
        sessionFactory.getCurrentSession().save(partida2);

        //when
        Partida partidaBuscada = repositorioPartida.buscarPartidaPorId(1L);

        //then
          assertThat(partidaBuscada,isNotNull());
    }

}
