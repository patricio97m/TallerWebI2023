package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Payment;
import com.mercadopago.resources.Preference;
import com.tallerwebi.dominio.ServicioMercadoPago;

import org.apache.maven.model.Model;
import javax.servlet.http.HttpServletRequest;

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
    
    @Autowired
    public ControladorTienda(ServicioMercadoPago servicioMercadoPago) throws MPConfException{
        MercadoPago.SDK.setAccessToken("APP_USR-7016693409174793-111920-9f953953b0a3db410143dd6bc366814a-1554853452");
        this.servicioMercadoPago = servicioMercadoPago;
    }

    @RequestMapping("/tienda")
    public ModelAndView irATienda(){
        return new ModelAndView("tienda");
    }

    @RequestMapping("/realizarCompra")
    public ModelAndView realizarCompra(){
        Preference preference = servicioMercadoPago.crearPreferencia();
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
                                       Model model
                                       ) throws MPException
    {
        Payment payment = Payment.findById(collectionId);
        return "OK";
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
                                       Model model) throws MPException
    {
        Payment payment = Payment.findById(collectionId);
        return "FALLO";
    }

}

