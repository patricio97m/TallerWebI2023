package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.Item;
import com.tallerwebi.dominio.ServicioMercadoPago;
import com.tallerwebi.dominio.ServicioTienda;
import com.tallerwebi.dominio.Usuario;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.maven.model.Model;
import org.apache.velocity.runtime.log.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


@RestController
public class ControladorTienda {

    private ServicioMercadoPago servicioMercadoPago;
    private ServicioTienda servicioTienda;
    private static final Logger logger = LogManager.getLogger(ControladorTienda.class);
    
    @Autowired
    public ControladorTienda(ServicioMercadoPago servicioMercadoPago, ServicioTienda servicioTienda) throws MPConfException{
        MercadoPago.SDK.setAccessToken("APP_USR-7016693409174793-111920-9f953953b0a3db410143dd6bc366814a-1554853452");
        this.servicioMercadoPago = servicioMercadoPago;
        this.servicioTienda = servicioTienda;
    }

    @RequestMapping("/tienda")
    public ModelAndView irATienda(){
        return new ModelAndView("tienda");
    }

    @RequestMapping("/realizarCompra")
    public ModelAndView realizarCompra(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "potenciadorElegido", required = false) int potenciadorElegido){
        Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
        if(usuario == null){
            try {
                response.sendRedirect("/spring/partida");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        Preference preference = servicioMercadoPago.crearPreferencia(potenciadorElegido);
        try {
            Preference result = preference.save();
            return new ModelAndView("redirect:" + result.getSandboxInitPoint());
        } catch (MPException e) {
            ModelMap model = new ModelMap();
            model.put("error", "Error a la hora de conectar con Mercado Pago");
            return new ModelAndView("partida", model);
        }
        
    }

    @GetMapping("pagoAprobado")
    public String procesarPagoAprovado(HttpServletRequest request, 
                                       @RequestParam("collection_id") String collectionId,
                                       @RequestParam("collection_status") String collectionStatus,
                                       @RequestParam("external_reference") String external_reference,
                                       @RequestParam("payment_type") String payment_type,
                                       @RequestParam("merchant_order_id") String merchant_order_id,
                                       @RequestParam("preference_id") String preferenceId,
                                       @RequestParam("site_id") String siteId,
                                       @RequestParam("processing_mode") String processing_mode,
                                       @RequestParam("merchant_account_id") String merchant_account_id,
                                       Model model, HttpServletResponse response
                                       ) throws MPException
    {
        Preference preference = Preference.findById(preferenceId);

        // Accede a los detalles del ítem en la preferencia
        logger.info(preference);
        ArrayList<Item> items = preference.getItems();
        if (items != null) {
            Item primerItem = items.get(0);
            String nombreDelItem = primerItem.getTitle();

            Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioAutenticado");
            int potenciadorElegido = 0;
            switch (nombreDelItem) {
                case "Potenciador - Repartir Cartas":
                    potenciadorElegido = 1;
                    break;
                
                case "Potenciador - Intercambiar Cartas":
                    potenciadorElegido = 2;
                    break;

                case "Potenciador - Sumar Puntos":
                    potenciadorElegido = 3;
                    break;

                default:
                    break;
            }
            servicioTienda.registrarCompraDePotenciador(usuario, potenciadorElegido);
        }
        try {
            response.sendRedirect("/spring/partida");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Pago Aprobado";
    }

    @GetMapping("pagoFallido")
    public String procesarPagoFallido(HttpServletRequest request,
                                       @RequestParam("collection_id") String collectionId,
                                       @RequestParam("collection_status") String collectionStatus,
                                       @RequestParam("external_reference") String external_reference,
                                       @RequestParam("payment_type") String payment_type,
                                       @RequestParam("merchant_order_id") String merchant_order_id,
                                       @RequestParam("preference_id") String preferenceId,
                                       @RequestParam("site_id") String siteId,
                                       @RequestParam("processing_mode") String processing_mode,
                                       @RequestParam("merchant_account_id") String merchant_account_id,
                                       Model model, HttpServletResponse response) throws MPException
    {
        try {
            response.sendRedirect("/spring/partida");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "Pago Fallido";     
    }

}

