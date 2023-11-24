package servicios;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.RepositorioPartida;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;


public class ServicioPartidaTest {

    RepositorioPartida repositorioPartida = mock(RepositorioPartida.class);
    ServicioIA servicioIA = mock(ServicioIA.class);
    ServicioPartida servicioPartida = new ServicioPartidaImpl(repositorioPartida,servicioIA);

}
