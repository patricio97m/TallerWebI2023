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
import com.tallerwebi.enums.Jugador;
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

    @Mock
    private Carta cartaMock;

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

    @Test
    public void siElUsuarioEsNuloElNumeroDeAyudasEsCero(){
        Long idPartidaExistente = 789L;
        when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaExistente);
        when(servicioPartidaMock.partidaExiste(idPartidaExistente)).thenReturn(true);
        when(servicioPartidaMock.buscarPartida(idPartidaExistente)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock); 
        when(requestMock.getSession()).thenReturn(httpSessionMock);
        when(requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(null);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);

        // Verificar que el modelo contiene la cantidad correcta de ayudas
        ModelMap model = (ModelMap)modelAndView.getModel();
        assertThat(model.get("ayudasRepartirCartas"), is(0));
        assertThat(model.get("ayudasIntercambiarCartas"), is(0));
        assertThat(model.get("ayudasSumarPuntos"), is(0));
    }

    @Test 
    public void siElUsuarioExisteSeUsaElNumeroDeAyudasQueTiene(){
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
        
        when(usuarioMock.getAyudasRepartirCartas()).thenReturn(1);
        when(usuarioMock.getAyudasIntercambiarCartas()).thenReturn(2);
        when(usuarioMock.getAyudasSumarPuntos()).thenReturn(3);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);

        // Verificar que el modelo contiene la cantidad correcta de ayudas
        ModelMap model = (ModelMap)modelAndView.getModel();
        assertThat(model.get("ayudasRepartirCartas"), is(1));
        assertThat(model.get("ayudasIntercambiarCartas"), is(2));
        assertThat(model.get("ayudasSumarPuntos"), is(3));
    }

    @Test
    public void verificarModeloContieneUltimaJugada() {
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

        when(partidaMock.getUltimaJugada()).thenReturn("Truco");
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ultimaJugada"), is("Truco"));
    }

    @Test
    public void verificarModeloContieneUltimoJugador() {
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

        when(partidaMock.getUltimoJugador()).thenReturn(Jugador.J1);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ultimoJugador"), is(Jugador.J1));
    }

    @Test
    public void verificarModeloContieneTurnoIa() {
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

        when(partidaMock.isTurnoIA()).thenReturn(true);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("turnoIa"), is(true));
    }

    @Test
    public void verificarModeloContieneManoDelJugador() {
        Long idPartidaExistente = 789L;
        String paloCarta = "Espada";
        Short numeroCarta = 7;
        ArrayList<Carta> cartasDelJugador = new ArrayList<Carta>();
        cartasDelJugador.add(0, null);
        cartasDelJugador.add(1, cartaMock);
        cartasDelJugador.add(2, null);
        ArrayList<String> resultadoEsperado = new ArrayList<String>();
        resultadoEsperado.add(0, "NULL");
        resultadoEsperado.add(1, paloCarta + numeroCarta);
        resultadoEsperado.add(2, "NULL");

        when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaExistente);
        when(servicioPartidaMock.partidaExiste(idPartidaExistente)).thenReturn(true);
        when(servicioPartidaMock.buscarPartida(idPartidaExistente)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock); 
        when(requestMock.getSession()).thenReturn(httpSessionMock);
        when(requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(usuarioMock);

        when(manoMock.getCartas()).thenReturn(cartasDelJugador);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("manoDelJugador"), is(resultadoEsperado));
    }

    @Test
    public void verificarModeloContieneCartasRestantesIa() {
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

        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(manoMock.size()).thenReturn(1);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasRestantesIa"), is(2)); // Suponiendo que son 3 cartas en total
    }

    @Test
    public void verificarModeloContieneCartasJugadasIa() {
        Long idPartidaExistente = 789L;
        String paloCarta = "Espada";
        Short numeroCarta = 7;
        ArrayList<Carta> cartasDeLaIa = new ArrayList<Carta>();
        cartasDeLaIa.add(0, null);
        cartasDeLaIa.add(1, cartaMock);
        cartasDeLaIa.add(2, null);
        ArrayList<String> resultadoEsperado = new ArrayList<String>();
        resultadoEsperado.add(0, "NULL");
        resultadoEsperado.add(1, paloCarta + numeroCarta);
        resultadoEsperado.add(2, "NULL");

        when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaExistente);
        when(servicioPartidaMock.partidaExiste(idPartidaExistente)).thenReturn(true);
        when(servicioPartidaMock.buscarPartida(idPartidaExistente)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock); 
        when(requestMock.getSession()).thenReturn(httpSessionMock);
        when(requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(usuarioMock);

        when(manoMock.getCartas()).thenReturn(cartasDeLaIa);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);
        
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasJugadasIa"), is(resultadoEsperado));
    }

    @Test
    public void verificarModeloContieneCartasJugadasJugador() {
        Long idPartidaExistente = 789L;
        String paloCarta = "Espada";
        Short numeroCarta = 7;
        ArrayList<Carta> cartasJugadasJugador = new ArrayList<Carta>();
        cartasJugadasJugador.add(0, null);
        cartasJugadasJugador.add(1, cartaMock);
        cartasJugadasJugador.add(2, null);
        ArrayList<String> resultadoEsperado = new ArrayList<String>();
        resultadoEsperado.add(0, "NULL");
        resultadoEsperado.add(1, paloCarta + numeroCarta);
        resultadoEsperado.add(2, "NULL");

        when(httpSessionMock.getAttribute("idPartida")).thenReturn(idPartidaExistente);
        when(servicioPartidaMock.partidaExiste(idPartidaExistente)).thenReturn(true);
        when(servicioPartidaMock.buscarPartida(idPartidaExistente)).thenReturn(partidaMock);
        when(partidaMock.getManoDelJugador()).thenReturn(manoMock);
        when(partidaMock.getManoDeLaIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(partidaMock.getCartasJugadasJugador()).thenReturn(manoMock); 
        when(requestMock.getSession()).thenReturn(httpSessionMock);
        when(requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(usuarioMock);

        when(manoMock.getCartas()).thenReturn(cartasJugadasJugador);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasJugadasJugador"), is(resultadoEsperado));
    }

    @Test
    public void verificarModeloContienePuntosIa() {
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

        when(partidaMock.getPuntosIa()).thenReturn(15);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosIa"), is(15));
    }

    @Test
    public void verificarModeloContienePuntosJugador() {
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

        when(partidaMock.getPuntosJugador()).thenReturn(10);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosJugador"), is(10));
    }

    @Test
    public void verificarModeloContieneEstadoTruco() {
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

        when(partidaMock.getEstadoTruco()).thenReturn(2); // Suponiendo algún valor para el estado del truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("truco"), is(2));
    }

    @Test
    public void verificarModeloContieneTrucoAQuerer() {
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

        when(partidaMock.getTrucoAQuerer()).thenReturn(1); // Suponiendo algún valor para el truco a querer
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("trucoAQuerer"), is(1));
    }

    @Test
    public void verificarModeloContieneCantoTruco() {
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

        when(partidaMock.getCantoTruco()).thenReturn(false); // Suponiendo algún valor para el canto del truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoTruco"), is(false));
    }

    @Test
    public void verificarModeloContienePuedeCantarTruco() {
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

        when(partidaMock.puedeCantarTruco(Jugador.J1)).thenReturn(true);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puedeCantarTruco"), is(true));
    }

    @Test
    public void verificarModeloContieneQuienCantoTruco() {
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

        when(partidaMock.getQuienCantoTruco()).thenReturn(Jugador.NA); // Suponiendo algún valor para el que canta el truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("quienCantoTruco"), is(Jugador.NA));
    }

    @Test
    public void verificarModeloContieneEstadoEnvido() {
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

        when(partidaMock.getEstadoEnvido()).thenReturn(0); // Suponiendo algún valor para el estado del envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("envido"), is(0));
    }

    @Test
    public void verificarModeloContieneEnvidoAQuerer() {
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

        when(partidaMock.getEnvidoAQuerer()).thenReturn(2); // Suponiendo algún valor para el envido a querer
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("envidoAQuerer"), is(2));
    }

    @Test
    public void verificarModeloContieneCantoEnvido() {
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

        when(partidaMock.getCantoEnvido()).thenReturn(false); // Suponiendo algún valor para el canto del envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoEnvido"), is(false));
    }

    @Test
    public void verificarModeloContieneCantoFaltaEnvido() {
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

        when(partidaMock.getCantoFaltaEnvido()).thenReturn(false); // Suponiendo algún valor para el canto de falta de envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoFaltaEnvido"), is(false));
    }

    @Test
    public void verificarModeloContienePuntosEnvidoIA() {
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

        Short puntosEnvido = 25;
        when(partidaMock.getManoDeLaIa().getValorEnvido()).thenReturn(puntosEnvido); // Suponiendo algún valor para los puntos de envido de la IA
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosEnvidoIa"), is(puntosEnvido));
    }

    @Test
    public void verificarModeloContienePuntosEnvidoJugador() {
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

        Short puntosEnvido = 25;
        when(partidaMock.getManoDelJugador().getValorEnvido()).thenReturn(puntosEnvido); // Suponiendo algún valor para los puntos de envido del jugador
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosEnvidoJugador"), is(puntosEnvido));
    }

    @Test
    public void verificarModeloContieneTiradaActual() {
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

        when(partidaMock.getTiradaActual()).thenReturn(1); // Suponiendo algún valor para la tirada actual
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("tiradaActual"), is(1));
    }

    @Test
    public void verificarModeloContieneRecanto() {
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

        when(partidaMock.getRecanto()).thenReturn(true); // Suponiendo algún valor para el recanto
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("recanto"), is(true));
    }

    @Test
    public void verificarModeloContieneQuienEsMano() {
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

        when(partidaMock.getQuienEsMano()).thenReturn(Jugador.J1); // Suponiendo algún valor para el jugador que es mano
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("quienEsMano"), is(Jugador.J1));
    }

    @Test
    public void verificarModeloContieneSeRepartieronCartas() {
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

        when(partidaMock.isSeRepartieronCartas()).thenReturn(false); // Suponiendo algún valor para si se repartieron las cartas
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("seRepartieronCartas"), is(false));
    }

    @Test
    public void verificarModeloContieneGanador() {
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

        when(partidaMock.getGanador()).thenReturn(Jugador.NA); // Suponiendo algún valor para el ganador de la partida
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ganador"), is(Jugador.NA));
    }
}

