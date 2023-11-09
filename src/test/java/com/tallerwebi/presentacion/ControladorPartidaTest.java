package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugada;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.excepcion.JugadaInvalidaException;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorPartidaTest {

    private ControladorPartida controladorPartida;
    private ServicioPartida servicioPartidaMock;

    @BeforeEach
    public void init() {
        servicioPartidaMock = mock(ServicioPartida.class);
        controladorPartida = new ControladorPartida(servicioPartidaMock);
    }

    @Test
    public void queElJugadorPuedaCantarTruco() throws JugadaInvalidaException {
        // Ejecución
        controladorPartida.enviarJugada("Truco", 0, null);

        // Validación
        Mockito.verify(servicioPartidaMock, Mockito.times(1)).actualizarCambiosDePartida(null, new Jugada(TipoJugada.TRUCO), Jugador.J1);
    }
    @Test
    public void queElJugadorPuedaCantarEnvido() throws JugadaInvalidaException {
        // Ejecución
        controladorPartida.enviarJugada("Envido", 0, null);

        // Validación
        Mockito.verify(servicioPartidaMock, Mockito.times(1)).actualizarCambiosDePartida(null, new Jugada(TipoJugada.ENVIDO), Jugador.J1);
    }
    @Test
    public void queElJugadorSePuedaIrAlMazo() throws JugadaInvalidaException {
        // Ejecución
        controladorPartida.enviarJugada("Mazo", 0, null);

        // Validación
        Mockito.verify(servicioPartidaMock, Mockito.times(1)).actualizarCambiosDePartida(null, new Jugada(TipoJugada.MAZO), Jugador.J1);
    }
}