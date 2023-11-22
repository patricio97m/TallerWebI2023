package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }

    @RequestMapping("/login")
    public ModelAndView irALogin(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        modelo.put("datosLogin", new DatosLogin());
        return new ModelAndView("login", modelo);
    }

    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(datosLogin.getEmail(), datosLogin.getPassword());
        if (usuarioBuscado != null) {
            //Recuperar usuario de la sesion
            HttpSession session = request.getSession();
            session.setAttribute("usuarioAutenticado", usuarioBuscado);
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/home");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("login", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario) {
        ModelMap model = new ModelMap();
        try{
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e){
            model.put("error", "El usuario ya existe");
            return new ModelAndView("nuevo-usuario", model);
        } catch (Exception e){
            model.put("error", "Error al registrar el nuevo usuario");
            return new ModelAndView("nuevo-usuario", model);
        }
        return new ModelAndView("redirect:/login");
    }

    @RequestMapping(path = "/nuevo-usuario", method = RequestMethod.GET)
    public ModelAndView nuevoUsuario(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        model.put("usuario", new Usuario());
        return new ModelAndView("nuevo-usuario", model);
    }

    @RequestMapping(path = "/home", method = RequestMethod.GET)
    public ModelAndView irAHome(HttpServletRequest request) {

        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");

            model.put("usuario", usuarioAutenticado);
            return new ModelAndView("home", model);
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");

        model.put("usuario", usuarioAutenticado);
        return new ModelAndView("redirect:/home",model);
    }

    @RequestMapping(path = "/opciones", method = RequestMethod.GET)
    public ModelAndView irAOpciones(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");

        model.put("usuario", usuarioAutenticado);
        return new ModelAndView("opciones",model);
    }

    @RequestMapping(path = "/perfil", method = RequestMethod.GET)
    public ModelAndView irAlPerfil(HttpServletRequest request) {
        ModelMap model = new ModelMap();

        HttpSession session = request.getSession();
        Usuario usuarioAutenticado = (Usuario) session.getAttribute("usuarioAutenticado");

        if (usuarioAutenticado != null) {
            model.put("usuario", usuarioAutenticado);
            return new ModelAndView("perfil", model);
        } else {
            // Maneja el caso en el que el usuario no está autenticado
            return new ModelAndView("redirect:/login");
        }
    }
}

