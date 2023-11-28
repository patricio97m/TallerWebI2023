package com.tallerwebi.servicios;

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

public class ServicioPartidaTest {

    @InjectMocks
    private ServicioPartida servicioPartida;

    @Mock
    private ControladorPartida controladorPartidaMock;

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
    public void testDePrueba(){
        
    }
}