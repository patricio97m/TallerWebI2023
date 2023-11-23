package com.tallerwebi.dominio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mercadopago.resources.Preference;
import com.mercadopago.resources.Preference.AutoReturn;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;

@Service("servicioMercadoPago")
@Transactional
public class ServicioMercadoPagoImpl implements ServicioMercadoPago{

    @Autowired
    public ServicioMercadoPagoImpl(){

    }

    @Override
    public Preference crearPreferencia(int potenciadorElegido) {
        String nombrePotenciador = "Potenciador de Truco";
        switch (potenciadorElegido) {
            case 1:
                nombrePotenciador = "Potenciador - Repartir Cartas";
                break;
            case 2:
                nombrePotenciador = "Potenciador - Intercambiar Cartas";
                break;
            case 3:
                nombrePotenciador = "Potenciador - Sumar Puntos";
                break;
        
            default:
                break;
        }

        Preference preference = new Preference();

        BackUrls backurls = new BackUrls()
        .setFailure("localhost:8080/spring/pagoFallido")
        .setSuccess("localhost:8080/spring/pagoAprobado");
        preference.setBackUrls(backurls);

        Item item = new Item()
        .setTitle(nombrePotenciador)
        .setQuantity(1)
        .setUnitPrice((float)100.00);

        preference.appendItem(item);
        preference.setAutoReturn(AutoReturn.approved);
        preference.setBinaryMode(true);

        return preference;
    }

    
}
