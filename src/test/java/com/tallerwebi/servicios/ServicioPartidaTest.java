package com.tallerwebi.servicios;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockConstruction;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Jugada;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioIA;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.ServicioPartidaImpl;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.infraestructura.RepositorioPartida;
import com.tallerwebi.presentacion.ControladorPartida;

public class ServicioPartidaTest {

    @InjectMocks
    private ServicioPartidaImpl servicioPartida;

    @Mock
    private ServicioIA servicioIaMock;

    @Mock
    private RepositorioPartida repositorioPartidaMock;

    @Mock
    private HttpSession httpSessionMock;

    @Mock
    private Usuario usuarioMock;

    @Mock
    private Partida partidaMock;

    @Mock
    private Mano manoMock;

    private Carta cartaPrueba = new Carta();

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        cartaPrueba.setNumero((short)1);
        cartaPrueba.setPalo("Espada");
    }

    @Test
    public void iniciarPartidaCreaUnaPartidaYDevuelveElId(){
        Long idPartida = (long) 123;

        when(repositorioPartidaMock.guardarNuevaPartida(any(Partida.class))).thenReturn(idPartida);
        when(repositorioPartidaMock.buscarCartaPorId(anyLong())).thenReturn(cartaPrueba);

        Long idPartidaDevuelto = servicioPartida.iniciarPartida();

        verify(repositorioPartidaMock, times(1)).guardarNuevaPartida(any(Partida.class));
        assertThat(idPartidaDevuelto, is(idPartida));
    }

    @Test
    public void alIniciarPartidaSeRepartenLasCartas(){
        ArrayList<Carta> cartasRepartidas = new ArrayList<Carta>();
        cartasRepartidas.add(cartaPrueba);
        cartasRepartidas.add(cartaPrueba);
        cartasRepartidas.add(cartaPrueba);
        Mano manoRepartida = new Mano(cartasRepartidas);

        when(repositorioPartidaMock.buscarCartaPorId(anyLong())).thenReturn(cartaPrueba);

        try(MockedConstruction<Partida> mockConstructorPartida = mockConstruction(Partida.class)){
            servicioPartida.iniciarPartida();

            Partida partidaCreada = mockConstructorPartida.constructed().get(0);

            assertNotNull(partidaCreada);
            verify(partidaCreada, times(1)).setManoDeLaIa(manoRepartida);
            verify(partidaCreada, times(1)).setManoDelJugador(manoRepartida);
        }
    }

    @Test
    public void alIniciarPartidaNoHayCartasJugadas(){
        Mano manoVacia = new Mano();

        when(repositorioPartidaMock.buscarCartaPorId(anyLong())).thenReturn(cartaPrueba);

        try(MockedConstruction<Partida> mockConstructorPartida = mockConstruction(Partida.class)){
            servicioPartida.iniciarPartida();

            Partida partidaCreada = mockConstructorPartida.constructed().get(0);

            assertNotNull(partidaCreada);
            verify(partidaCreada, times(1)).setCartasJugadasIa(manoVacia);
            verify(partidaCreada, times(1)).setCartasJugadasJugador(manoVacia);
        }
    }

    @Test
    public void partidaExisteDevuelveTrueSiElRepositorioEncontroUnaPartida(){
        Long idPartida = (long) 123;

        when(repositorioPartidaMock.buscarPartidaPorId(idPartida)).thenReturn(partidaMock);

        assertThat(servicioPartida.partidaExiste(idPartida), is(true));
    } 

    @Test
    public void partidaExisteDevuelveFalseSiElRepositorioNoEncontroUnaPartida(){
        Long idPartida = (long) 123;

        when(repositorioPartidaMock.buscarPartidaPorId(idPartida)).thenReturn(null);

        assertThat(servicioPartida.partidaExiste(idPartida), is(false));
    } 
}