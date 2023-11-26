package com.tallerwebi.controladores;
import com.tallerwebi.dominio.*;

import com.tallerwebi.presentacion.ControladorPartida;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.*;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorPartidaTest {

    private ControladorPartida controladorPartida;
    private ServicioPartida servicioPartida;

    private Usuario usuarioMock;

    private HttpServletRequest requestMock;

    private HttpServletResponse responseMock;

    private Partida partidaMock;

    private HttpSession sessionMock;

    private Mano manoMock;

    @BeforeEach
    public void init() {
        servicioPartida = mock(ServicioPartida.class);
        controladorPartida = new ControladorPartida(servicioPartida);
        usuarioMock = mock(Usuario.class);
        requestMock = mock(HttpServletRequest.class);
        responseMock = mock(HttpServletResponse.class);
        partidaMock = mock(Partida.class);
        sessionMock = mock(HttpSession.class);
        manoMock = mock(Mano.class);
    }

    @Test
    public void irAPartida(){

        //Preparacion
        Usuario usuarioEncontradoMock = mock(Usuario.class);
        usuarioEncontradoMock.setNombre("Carlos");
        usuarioEncontradoMock.setRol("Admin");
        usuarioEncontradoMock.setEmail("carlosMaslaton@gmail.com");
        usuarioEncontradoMock.setGenero(1);
        usuarioEncontradoMock.setPassword("FrutillaFrozen23");
        usuarioEncontradoMock.setId(1L);

        Partida partidaEncontradaMock = mock(Partida.class);

        Carta carta1 = new Carta();
        carta1.setNumero((short) 1);
        carta1.setValorEnvido((short) 1);
        carta1.setPalo("Oro");
        carta1.setId(1L);
        carta1.setValorTruco((short) 1);

        Carta carta2 = new Carta();
        carta2.setNumero((short) 2);
        carta2.setValorEnvido((short) 2);
        carta2.setPalo("Oro");
        carta2.setId(2L);
        carta2.setValorTruco((short) 2);

        Carta carta3 = new Carta();
        carta1.setNumero((short) 3);
        carta1.setValorEnvido((short) 3);
        carta1.setPalo("Oro");
        carta1.setId(3L);
        carta1.setValorTruco((short) 3);

        when(partidaEncontradaMock.getId()).thenReturn(1L);
        when(requestMock.getSession()).thenReturn(sessionMock);
        when(sessionMock.getAttribute("idPartida")).thenReturn(1L);
        when((Long)requestMock.getSession().getAttribute("idPartida")).thenReturn(1L);

        when(sessionMock.getAttribute("usuarioAutenticado")).thenReturn(usuarioEncontradoMock);
        when((Usuario)requestMock.getSession().getAttribute("usuarioAutenticado")).thenReturn(usuarioEncontradoMock);

        when(servicioPartida.buscarPartida(anyLong())).thenReturn(partidaEncontradaMock);

        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(carta1);
        cartas.add(carta2);
        cartas.add(carta3);
        when(manoMock.getCartas()).thenReturn(cartas);

        //Error NullPointerException en el metodo ConvertirLaManoAStrings




        //Ejecucion
        ModelAndView mav = controladorPartida.irAPartida(requestMock,responseMock);

        //Validacion
        assertThat(mav.getViewName(), equalToIgnoringCase("partida"));



    }


}
