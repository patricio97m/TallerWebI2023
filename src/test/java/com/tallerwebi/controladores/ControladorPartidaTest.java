package com.tallerwebi.controladores;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorPartida;

public class ControladorPartidaTest {

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

    @Mock
    private Partida partidaMock;

    @Mock
    private Mano manoMock;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void siElIdPartidaEsNuloSeCreaUnaNuevaPartida() {
        Long idPartida = 1L;
        when(httpSessionMock.getAttribute("idPartida")).thenReturn(null);
        when(servicioPartidaMock.iniciarPartida()).thenReturn(idPartida); // Simulamos la creación de una nueva partida
        when(servicioPartidaMock.partidaExiste(idPartida)).thenReturn(true); // Simulamos que la partida existe
        when(servicioPartidaMock.buscarPartida(idPartida)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock);        
        when(requestMock.getSession()).thenReturn(httpSessionMock);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);

        assertThat(modelAndView.getViewName(), is("partida"));
        assertThat(modelAndView.getModel(), notNullValue());
        verify(httpSessionMock, times(1)).setAttribute("idPartida", idPartida);
    }

    @Test
    public void siElIdPartidaNoCorrespondeAUnaPartidaExistenteSeCreaUnaNueva() {
        Long idPartidaNoExistente = 123L;
        Long idPartida = 1L;
        when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaNoExistente);
        when(servicioPartidaMock.partidaExiste(idPartidaNoExistente)).thenReturn(false);
        when(servicioPartidaMock.iniciarPartida()).thenReturn(idPartida); // Simulamos la creación de una nueva partida
        when(servicioPartidaMock.buscarPartida(idPartida)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock); 
        when(requestMock.getSession()).thenReturn(httpSessionMock);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);

        assertThat(modelAndView.getViewName(), is("partida"));
        assertThat(modelAndView.getModel(), notNullValue());
        verify(httpSessionMock, times(1)).setAttribute("idPartida", idPartida);
    }

    @Test
    public void siElUsuarioTieneUnIdPartidaValidoSeDevuelvenLosDatosDeLaMisma() {
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

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);

        assertThat(modelAndView.getViewName(), is("partida"));
        assertThat(modelAndView.getModel(), notNullValue());
        // Puedes agregar más validaciones específicas para los mocks de Partida y Usuario
    }
}

