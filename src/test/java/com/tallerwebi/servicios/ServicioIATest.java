package com.tallerwebi.servicios;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioIA;
import com.tallerwebi.dominio.ServicioIAImpl;
import com.tallerwebi.infraestructura.RepositorioPartida;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;

public class ServicioIATest {
    
    RepositorioPartida repositorioPartida = mock(RepositorioPartida.class);
    ServicioIA servicioIa = new ServicioIAImpl(repositorioPartida);

    @Test
    public void calcularJugadaDevuelveUnaJugadaValida(){
        Partida partida = new Partida();
        partida.setTurnoIA(true);

        doReturn(partida).when(repositorioPartida).buscarPartidaPorId(null);
        assertThat(servicioIa.calcularJugada(null),is(notNullValue()));
    }
}
