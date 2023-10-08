package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Carta;
import com.tallerwebi.dominio.Mano;
import com.tallerwebi.dominio.Partida;

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
    public Long guardarNuevaMano(Mano mano) {
         return (Long)sessionFactory.getCurrentSession().save(mano);
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
    public Mano buscarCartasDelJugador(Long idPartida) {
        Partida partida = buscarPartidaPorId(idPartida);
        return partida.getManoDelJugador();
    }

    @Override
    public Mano buscarCartasDeLaIa(Long idPartida) {
        Partida partida = buscarPartidaPorId(idPartida);
        return partida.getManoDeLaIa();
    }

    @Override
    public void asignarCartasAlJugador(Long idPartida, Mano cartasDelJugador) {
        Partida partida = buscarPartidaPorId(idPartida);
        partida.setManoDelJugador(cartasDelJugador);
        sessionFactory.getCurrentSession().update(partida);
    }

    @Override
    public void asignarCartasALaIa(Long idPartida, Mano cartasDeLaIA) {
        Partida partida = buscarPartidaPorId(idPartida);
        partida.setManoDeLaIa(cartasDeLaIA);
        sessionFactory.getCurrentSession().update(partida);
    }

}

