package com.tallerwebi.repositorio;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioPartida;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import com.tallerwebi.presentacion.ControladorPartida;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.MatcherAssert.assertThat;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

public class RepositorioPartidaTest{

    @Mock
    private Partida partidaMock;
    @Mock
    private Mano manoMock;

    @Mock
    private Ronda rondaMock;

    @Mock
    private Carta cartaMock;
    @Mock
    private Carta cartaMock2;
    @Mock
    private Carta cartaMock3;

    @Mock
    private SessionFactory sessionFactoryMock;

    @Mock
    private ServicioPartida servicioPartidaMock;

    @InjectMocks
    private ControladorPartida controladorPartida;

    @Mock
    private HttpServletRequest requestMock;

    @Mock
    private HttpServletResponse responseMock;

    @Mock
    private HttpSession httpSessionMock;

    @Mock
    private Usuario usuarioMock;



    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Autowired
    RepositorioPartida repositorioPartida;

    @Autowired
    private SessionFactory sessionFactory;


    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void guardarNuevaPartida() {

        Long idPartida = 1L;

        repositorioPartida.guardarNuevaPartida(partidaMock);

      Partida partidaGuardada = sessionFactory.getCurrentSession().get(Partida.class, idPartida);

       assertThat(partidaMock.getId(),is(partidaGuardada.getId()));

    }

    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void guardarNuevaMano() {

        Long idMano = 1L;

        repositorioPartida.guardarNuevaMano(manoMock);

        Mano manoGuardada = sessionFactory.getCurrentSession().get(Mano.class, idMano);

        assertThat(manoMock.getId(),is(manoGuardada.getId()));
    }

    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void guardarNuevaRonda() {

        Long idRonda = 1L;

        repositorioPartida.guardarNuevaRonda(rondaMock);

        Ronda rondaGuardada = sessionFactory.getCurrentSession().get(Ronda.class, idRonda);


        assertThat(rondaMock.getId(),is(rondaGuardada.getId()));
    }

    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void buscarPartidaPorId() {


        Long idPartida = 1L;

        repositorioPartida.guardarNuevaPartida(partidaMock);

        Partida partidaBuscada = repositorioPartida.buscarPartidaPorId(idPartida);


        assertThat(partidaBuscada.getId(), is(partidaMock.getId()));
    }

    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void  buscarCartasDelJugador() {

        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);


        partidaMock.setManoDelJugador(manoMock);

        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

        Mano manoBuscada = repositorioPartida.buscarCartasDelJugador(idpartida);

        assertThat(manoBuscada, notNullValue());
    }

   @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void buscarCartasDeLaIA() {

        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);

        partidaMock.setManoDeLaIa(manoMock);

        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

        Mano manoBuscada = repositorioPartida.buscarCartasDeLaIa(idpartida);


      assertThat(manoBuscada, notNullValue());
    }

    @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void asignarCartasAlJugador() {
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);


        partidaMock.setManoDelJugador(manoMock);
        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

       repositorioPartida.asignarCartasAlJugador(idpartida,manoMock);

       Mano manoBuscada = repositorioPartida.buscarCartasDelJugador(idpartida);

        assertThat(manoBuscada, notNullValue());

    }

   @DirtiesContext
    @Transactional
    @Rollback
    @Test
   public void asignarCartasALaIA() {


       when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);


        partidaMock.setManoDeLaIa(manoMock);
        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

       repositorioPartida.asignarCartasALaIa(idpartida,manoMock);

       Mano manoBuscada = repositorioPartida.buscarCartasDeLaIa(idpartida);

        assertThat(manoBuscada, notNullValue());
    }


  @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void buscarCartasJugadasJugador() {
    prepararPartida();

        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

        Mano manoBuscada = repositorioPartida.buscarCartasJugadasJugador(idpartida);


        assertThat(manoBuscada,notNullValue() );
    }

   @DirtiesContext
    @Transactional
    @Rollback
    @Test
    public void buscarCartasJugadasIa() {
prepararPartida();

        Long idpartida = repositorioPartida.guardarNuevaPartida(partidaMock);

        Mano manoBuscada = repositorioPartida.buscarCartasJugadasIa(idpartida);


      assertThat(manoBuscada, notNullValue());
    }
  public void prepararPartida(){
      Long idPartidaExistente = 789L;
      when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaExistente);
      when(servicioPartidaMock.partidaExiste(idPartidaExistente)).thenReturn(true);
      when(servicioPartidaMock.buscarPartida(idPartidaExistente)).thenReturn(partidaMock);
      when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
      when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
      when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
      when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock);
      when(requestMock.getSession()).thenReturn(httpSessionMock);
      when(requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(usuarioMock);
  }
}

