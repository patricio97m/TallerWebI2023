package com.tallerwebi.dominio;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioTienda")
@Transactional
public class ServicioTiendaImpl implements ServicioTienda{

    @Autowired
    public ServicioTiendaImpl(){

    }

    @Override
    public void registrarCompraDePotenciador(Usuario usuario, int potenciadorElegido) {
        switch (potenciadorElegido) {
            case 1:
                usuario.setAyudasRepartirCartas(usuario.getAyudasRepartirCartas() + 1);
                break;

            case 2:
                usuario.setAyudasIntercambiarCartas(usuario.getAyudasIntercambiarCartas() + 1);
                break;

            case 3:
                usuario.setAyudasSumarPuntos(usuario.getAyudasSumarPuntos() + 1);
                break;

            default:
                break;
        }
    }   
}
