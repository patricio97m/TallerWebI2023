package com.tallerwebi.presentacion;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Jugada;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.JugadaInvalidaException;
import com.tallerwebi.enums.Jugador;
import com.tallerwebi.enums.TipoJugada;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControladorPartida {

    private ServicioPartida servicioPartida;

    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida) {
        this.servicioPartida = servicioPartida;
    }

    @RequestMapping("/partida")
    public ModelAndView irAPartida(HttpServletRequest request, HttpServletResponse response) {
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");

        if (idPartida == null || !servicioPartida.partidaExiste(idPartida)) {
            idPartida = servicioPartida.iniciarPartida();
            
            request.getSession().setAttribute("idPartida", idPartida);
        }

        Partida partida = servicioPartida.buscarPartida(idPartida);

        ModelMap model = convertirPartidaAModelMap(partida, usuario);
        return new ModelAndView("partida", model);
    }

    private ModelMap convertirPartidaAModelMap(Partida partida, Usuario usuario) {
        int ayudasRepartirCartas = 0;
        int ayudasIntercambiarCartas = 0;
        int ayudasSumarPuntos = 0;
        if(usuario != null){
            ayudasRepartirCartas = usuario.getAyudasRepartirCartas();
            ayudasIntercambiarCartas = usuario.getAyudasIntercambiarCartas();
            ayudasSumarPuntos = usuario.getAyudasSumarPuntos();
        }
        
        ModelMap model = new ModelMap();
        model.put("ultimaJugada", partida.getUltimaJugada());
        model.put("ultimoJugador", partida.getUltimoJugador());
        model.put("turnoIa", partida.isTurnoIA());
        model.put("manoDelJugador", convertirManoAStrings(partida.getManoDelJugador()));
        model.put("cartasRestantesIa", (3 - partida.getCartasJugadasIa().size()));
        model.put("cartasJugadasIa", convertirManoAStrings(partida.getCartasJugadasIa()));
        model.put("cartasJugadasJugador", convertirManoAStrings(partida.getCartasJugadasJugador()));
        model.put("puntosIa", partida.getPuntosIa());
        model.put("puntosJugador", partida.getPuntosJugador());
        model.put("truco", partida.getEstadoTruco());
        model.put("trucoAQuerer", partida.getTrucoAQuerer());
        model.put("cantoTruco", partida.getCantoTruco());
        model.put("puedeCantarTruco", partida.puedeCantarTruco(Jugador.J1));
        model.put("quienCantoTruco", partida.getQuienCantoTruco());
        model.put("envido", partida.getEstadoEnvido());
        model.put("envidoAQuerer", partida.getEnvidoAQuerer());
        model.put("cantoEnvido", partida.getCantoEnvido());
        model.put("cantoFaltaEnvido", partida.getCantoFaltaEnvido());
        model.put("puntosEnvidoIa", partida.getManoDeLaIa().getValorEnvido());
        model.put("puntosEnvidoJugador", partida.getManoDelJugador().getValorEnvido());
        model.put("tiradaActual", partida.getTiradaActual());
        model.put("recanto", partida.getRecanto());
        model.put("quienEsMano", partida.getQuienEsMano());
        model.put("seRepartieronCartas", partida.isSeRepartieronCartas());
        model.put("ayudasRepartirCartas", ayudasRepartirCartas);
        model.put("ayudasIntercambiarCartas", ayudasIntercambiarCartas);
        model.put("ayudasSumarPuntos", ayudasSumarPuntos);
        model.put("ganador", partida.getGanador());

        return model;
    }

    private String convertirPartidaAJson(Partida partida, Usuario usuario){
        int ayudasRepartirCartas = 0;
        int ayudasIntercambiarCartas = 0;
        int ayudasSumarPuntos = 0;
        if(usuario != null){
            ayudasRepartirCartas = usuario.getAyudasRepartirCartas();
            ayudasIntercambiarCartas = usuario.getAyudasIntercambiarCartas();
            ayudasSumarPuntos = usuario.getAyudasSumarPuntos();
        }
        // Construye manualmente una cadena JSON
        String json = "{"
                + "\"ultimaJugada\":\"" + partida.getUltimaJugada() + "\","
                + "\"ultimoJugador\":\"" + partida.getUltimoJugador() + "\","
                + "\"turnoIA\":" + partida.isTurnoIA() + ","
                + "\"manoDelJugador\":" + convertirArrayListAJSON(convertirManoAStrings(partida.getManoDelJugador())) + ","
                + "\"cartasRestantesIa\":" + (3 - partida.getCartasJugadasIa().size()) + ","
                + "\"cartasJugadasIa\":" + convertirArrayListAJSON(convertirManoAStrings(partida.getCartasJugadasIa())) + ","
                + "\"cartasJugadasJugador\":" + convertirArrayListAJSON(convertirManoAStrings(partida.getCartasJugadasJugador())) + ","
                + "\"puntosJugador\":" + partida.getPuntosJugador() + ","
                + "\"puntosIa\":" + partida.getPuntosIa() + ","
                + "\"truco\":" + partida.getEstadoTruco() + ","
                + "\"trucoAQuerer\":" + partida.getTrucoAQuerer() + ","
                + "\"cantoTruco\":" + partida.getCantoTruco() + ","
                + "\"puedeCantarTruco\":" + partida.puedeCantarTruco(Jugador.J1) + ","
                + "\"quienCantoTruco\":\"" + partida.getQuienCantoTruco() + "\","
                + "\"envido\":" + partida.getEstadoEnvido() + ","
                + "\"envidoAQuerer\":" + partida.getEnvidoAQuerer() + ","
                + "\"cantoEnvido\":" + partida.getCantoEnvido() + ","
                + "\"cantoFaltaEnvido\":" + partida.getCantoFaltaEnvido() + ","
                + "\"puntosEnvidoIA\":" + partida.getManoDeLaIa().getValorEnvido() + ","
                + "\"puntosEnvidoJugador\":" + partida.getManoDelJugador().getValorEnvido() + ","
                + "\"tiradaActual\":" + partida.getTiradaActual() + ","
                + "\"recanto\":" + partida.getRecanto() + ","
                + "\"quienEsMano\":\"" + partida.getQuienEsMano() + "\","
                + "\"seRepartieronCartas\":" + partida.isSeRepartieronCartas() + ","
                + "\"ayudasRepartirCartas\":" + ayudasRepartirCartas + ","
                + "\"ayudasIntercambiarCartas\":" + ayudasIntercambiarCartas + ","
                + "\"ayudasSumarPuntos\":" + ayudasSumarPuntos + ","
                + "\"ganador\":\"" + partida.getGanador() + "\""
                + "}";

        return json;
    }

    @PostMapping("/enviarJugada")
    @ResponseBody
    public String enviarJugada(HttpServletRequest request, HttpServletResponse response, @RequestParam("tipoJugada") String tipoJugada, @RequestParam("indice") int indice) {
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
        if(Objects.equals(tipoJugada, "Truco")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.TRUCO), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Envido")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.ENVIDO, indice), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Mazo")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.MAZO), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Carta")){
            System.out.println("Carta");
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.CARTA, indice), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Quiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 1), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "NoQuiero")){
            try {
                servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.RESPUESTA, 0), Jugador.J1, null);
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
        else if(Objects.equals(tipoJugada, "Potenciador")){
            try {
                if(usuario != null){
                    servicioPartida.actualizarCambiosDePartida(idPartida, new Jugada(TipoJugada.POTENCIADOR, indice), Jugador.J1, usuario);
                }
                else{
                    // Devuelve la URL de redirección
                    return "{\"redirect\": \"/spring/login\"}";
                }    
            } catch (JugadaInvalidaException e) {
                e.printStackTrace();
            }
        }
    
        return convertirPartidaAJson(servicioPartida.buscarPartida(idPartida), usuario);
    }

    @PostMapping("/recibirCambios")
    @ResponseBody
    public String recibirCambios(HttpServletRequest request){
        Long idPartida = (Long)request.getSession().getAttribute("idPartida");
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
        servicioPartida.calcularJugadaIA(idPartida);
        return convertirPartidaAJson(servicioPartida.buscarPartida(idPartida), usuario);
    }




    //METODOS AUXILIARES

    private String convertirArrayListAJSON(ArrayList<String> arrayList) {
        StringBuilder json = new StringBuilder("[");
        for (String item : arrayList) {
            json.append("\"").append(item).append("\",");
        }
        if (arrayList.size() > 0) {
            json.deleteCharAt(json.length() - 1); // Elimina la última coma
        }
        json.append("]");
        return json.toString();
    }

    public ArrayList<String> convertirManoAStrings(Mano mano) {
        ArrayList<Carta> cartasDeLaMano = mano.getCartas();
        ArrayList<String> nombreCartasDeLaMano = new ArrayList<String>();
        for (Carta carta : cartasDeLaMano) {
            if(carta != null){
                String nombre = carta.getPalo() + carta.getNumero();
                nombreCartasDeLaMano.add(nombre);
            }
            else{
                nombreCartasDeLaMano.add("NULL");
            }
            
        }
        return nombreCartasDeLaMano;
    }
}
