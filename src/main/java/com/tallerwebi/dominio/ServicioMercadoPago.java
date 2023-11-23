package com.tallerwebi.dominio;

import com.mercadopago.resources.Preference;

public interface ServicioMercadoPago {
    Preference crearPreferencia(int potenciadorElegido);
}
