package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Partida;

import java.util.ArrayList;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPartida")
public class RepositorioPartidaImpl implements RepositorioPartida {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long guardarNuevaPartida(Partida partida) {
        return (Long)sessionFactory.getCurrentSession().save(partida);
    }

    @Override
    public Carta buscarCartaPorId(Long id) {
        return (Carta) sessionFactory.getCurrentSession().createCriteria(Carta.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public Partida buscarPartidaPorId(Long id) {
        return (Partida) sessionFactory.getCurrentSession().createCriteria(Partida.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public ArrayList<Carta> buscarCartasDelJugador(Long idPartida) {
        Partida partida = buscarPartidaPorId(idPartida);
        return partida.getCartasDelJugador();
    }

    @Override
    public ArrayList<Carta> buscarCartasDeLaIa(Long idPartida) {
        Partida partida = buscarPartidaPorId(idPartida);
        return partida.getCartasDeLaIa();
    }

    @Override
    public void asignarCartasAlJugador(Long idPartida, ArrayList<Carta> cartasDelJugador) {
        Partida partida = buscarPartidaPorId(idPartida);
        partida.setCartasDelJugador(cartasDelJugador);
        sessionFactory.getCurrentSession().update(partida);
    }

    @Override
    public void asignarCartasALaIa(Long idPartida, ArrayList<Carta> cartasDeLaIA) {
        Partida partida = buscarPartidaPorId(idPartida);
        partida.setCartasDeLaIa(cartasDeLaIA);
        sessionFactory.getCurrentSession().update(partida);
    }

}

