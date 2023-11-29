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
import org.mockito.Mockito;
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
        verify(servicioPartidaMock, times(1)).iniciarPartida();
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
        verify(servicioPartidaMock, times(1)).iniciarPartida();
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
        verify(servicioPartidaMock, times(0)).iniciarPartida();
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
        prepararPartida();

        when(partidaMock.getUltimaJugada()).thenReturn("Truco");
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ultimaJugada"), is("Truco"));
    }

    @Test
    public void verificarModeloContieneUltimoJugador() {
        prepararPartida();

        when(partidaMock.getUltimoJugador()).thenReturn(Jugador.J1);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ultimoJugador"), is(Jugador.J1));
    }

    @Test
    public void verificarModeloContieneTurnoIa() {
        prepararPartida();

        when(partidaMock.isTurnoIA()).thenReturn(true);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("turnoIa"), is(true));
    }

    @Test
    public void verificarModeloContieneManoDelJugador() {
        prepararPartida();

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

        when(manoMock.getCartas()).thenReturn(cartasDelJugador);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("manoDelJugador"), is(resultadoEsperado));

        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCartasRestantesIa() {
        prepararPartida();

        when(partidaMock.getCartasJugadasIa()).thenReturn(manoMock);
        when(manoMock.size()).thenReturn(1);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasRestantesIa"), is(2)); // Suponiendo que son 3 cartas en total

        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCartasJugadasIa() {
        prepararPartida();

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

        when(manoMock.getCartas()).thenReturn(cartasDeLaIa);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);
        
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasJugadasIa"), is(resultadoEsperado));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCartasJugadasJugador() {
        prepararPartida();
        
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

        when(manoMock.getCartas()).thenReturn(cartasJugadasJugador);
        when(cartaMock.getPalo()).thenReturn(paloCarta);
        when(cartaMock.getNumero()).thenReturn(numeroCarta);

        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cartasJugadasJugador"), is(resultadoEsperado));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContienePuntosIa() {
        prepararPartida();

        when(partidaMock.getPuntosIa()).thenReturn(15);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosIa"), is(15));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContienePuntosJugador() {
        prepararPartida();

        when(partidaMock.getPuntosJugador()).thenReturn(10);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosJugador"), is(10));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneEstadoTruco() {
        prepararPartida();

        when(partidaMock.getEstadoTruco()).thenReturn(2); // Suponiendo algún valor para el estado del truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("truco"), is(2));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneTrucoAQuerer() {
        prepararPartida();

        when(partidaMock.getTrucoAQuerer()).thenReturn(1); // Suponiendo algún valor para el truco a querer
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("trucoAQuerer"), is(1));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCantoTruco() {
        prepararPartida();

        when(partidaMock.getCantoTruco()).thenReturn(false); // Suponiendo algún valor para el canto del truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoTruco"), is(false));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContienePuedeCantarTruco() {
        prepararPartida();

        when(partidaMock.puedeCantarTruco(Jugador.J1)).thenReturn(true);
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puedeCantarTruco"), is(true));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneQuienCantoTruco() {
        prepararPartida();

        when(partidaMock.getQuienCantoTruco()).thenReturn(Jugador.NA); // Suponiendo algún valor para el que canta el truco
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("quienCantoTruco"), is(Jugador.NA));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneEstadoEnvido() {
        prepararPartida();

        when(partidaMock.getEstadoEnvido()).thenReturn(0); // Suponiendo algún valor para el estado del envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("envido"), is(0));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneEnvidoAQuerer() {
        prepararPartida();

        when(partidaMock.getEnvidoAQuerer()).thenReturn(2); // Suponiendo algún valor para el envido a querer
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("envidoAQuerer"), is(2));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCantoEnvido() {
        prepararPartida();

        when(partidaMock.getCantoEnvido()).thenReturn(false); // Suponiendo algún valor para el canto del envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoEnvido"), is(false));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneCantoFaltaEnvido() {
        prepararPartida();

        when(partidaMock.getCantoFaltaEnvido()).thenReturn(false); // Suponiendo algún valor para el canto de falta de envido
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("cantoFaltaEnvido"), is(false));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContienePuntosEnvidoIA() {
        prepararPartida();

        Short puntosEnvido = 25;
        when(partidaMock.getManoDeLaIa().getValorEnvido()).thenReturn(puntosEnvido); // Suponiendo algún valor para los puntos de envido de la IA
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosEnvidoIa"), is(puntosEnvido));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContienePuntosEnvidoJugador() {
        prepararPartida();

        Short puntosEnvido = 25;
        when(partidaMock.getManoDelJugador().getValorEnvido()).thenReturn(puntosEnvido); // Suponiendo algún valor para los puntos de envido del jugador
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("puntosEnvidoJugador"), is(puntosEnvido));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneTiradaActual() {
        prepararPartida();

        when(partidaMock.getTiradaActual()).thenReturn(1); // Suponiendo algún valor para la tirada actual
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("tiradaActual"), is(1));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneRecanto() {
        prepararPartida();

        when(partidaMock.getRecanto()).thenReturn(true); // Suponiendo algún valor para el recanto
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("recanto"), is(true));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneQuienEsMano() {
        prepararPartida();

        when(partidaMock.getQuienEsMano()).thenReturn(Jugador.J1); // Suponiendo algún valor para el jugador que es mano
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("quienEsMano"), is(Jugador.J1));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneSeRepartieronCartas() {
        prepararPartida();

        when(partidaMock.isSeRepartieronCartas()).thenReturn(false); // Suponiendo algún valor para si se repartieron las cartas
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("seRepartieronCartas"), is(false));
        
        Mockito.reset();
    }

    @Test
    public void verificarModeloContieneGanador() {
        prepararPartida();

        when(partidaMock.getGanador()).thenReturn(Jugador.NA); // Suponiendo algún valor para el ganador de la partida
        ModelAndView modelAndView = controladorPartida.irAPartida(requestMock, responseMock);
        ModelMap model = (ModelMap) modelAndView.getModel();
        assertThat(model.get("ganador"), is(Jugador.NA));
        
        Mockito.reset();
    }



    
    //Metodos Auxiliares

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



