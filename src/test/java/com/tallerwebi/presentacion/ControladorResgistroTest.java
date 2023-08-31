package com.tallerwebi.presentacion;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;


public class ControladorResgistroTest {

    /*
    * 1. Necesito un email y una contraseña para que el usuario se pueda registrar
    * 2.
    * */

    ControladorRegistro controladorRegistro = new ControladorRegistro();
    @Test
    public void siIngresoEmailYClaveSeRegistraCorrectamente(){

        //1. given o dado que -> Precondicion

        //2. when o cunado -> ejecucion
        ModelAndView mav = whenRegistroUsuario("patricio@gmail.com");
        //3. then -> validación
        thenElRegistroEsExitoso(mav);
    }

    private ModelAndView whenRegistroUsuario(String email) {
        ModelAndView mav = controladorRegistro.registrar(email);
        return mav;
    }

    private void thenElRegistroEsExitoso(ModelAndView mav) {
        //mensaje El registro fue exitoso
        assertThat(mav.getModel().get("mensaje").toString(), equalToIgnoringCase("El registro fue exitoso"));
        //voy a vista login
        assertThat(mav.getViewName(), equalToIgnoringCase("login"));
    }

    @Test
    public void siElEmailEstaVacioElRegistroFalla(){

        //1. given o dado que -> Precondicion

        //2. when o cunado -> ejecucion
        ModelAndView mav = whenRegistroUsuario("");
        //3. then -> validación
        thenElRegistroFalla(mav);
    }

    private void thenElRegistroFalla(ModelAndView mav) {
        assertThat(mav.getModel().get("mensaje").toString(), equalToIgnoringCase("El registro falló"));
        assertThat(mav.getViewName(), equalToIgnoringCase("registro"));

    }
}
