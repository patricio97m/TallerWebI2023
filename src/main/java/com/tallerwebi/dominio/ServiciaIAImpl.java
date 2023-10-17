package com.tallerwebi.dominio;

import com.tallerwebi.enums.TipoJugada;
import com.tallerwebi.infraestructura.RepositorioPartida;

import java.util.ArrayList;

public class ServiciaIAImpl implements ServicioIA {

    private RepositorioPartida repositorioPartida;

    @Override
    public Jugada respuestaAleatoria(){
        int numero = (int) (Math.random() * 2+1);

        if(numero==1){
            return new Jugada(TipoJugada.RESPUESTA,1);
        } else{

            if(numero==2){
                return new Jugada(TipoJugada.RESPUESTA,2);
            }
        }

        return null;
    }

    @Override
    public Jugada tirarCartaAleatoria(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }
        ArrayList<Carta> cartasDeLaIA = manoIA.getCartas();
        int numero = (int) (Math.random() * 3);

        if (numero == 0) {
            return new Jugada(TipoJugada.CARTA, 1);
        }
        if (numero == 1) {
            return new Jugada(TipoJugada.CARTA, 2);
        }
        if (numero == 2) {
            return new Jugada(TipoJugada.CARTA, 3);
        }

        return null;
    }

    @Override
    public Jugada tirarMejorCarta(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }

        Carta mejorCarta;
        Integer posicionCarta = 0;

        if (manoIA.getCarta1().getValorTruco() >= manoIA.getCarta2().getValorTruco()) {
            if (manoIA.getCarta1().getValorTruco() >= manoIA.getCarta3().getValorTruco()) {
                mejorCarta = manoIA.getCarta1();
                posicionCarta = 1;
            } else {
                mejorCarta = manoIA.getCarta3();
                posicionCarta = 3;
            }
        } else {
            if (manoIA.getCarta2().getValorTruco() >= manoIA.getCarta3().getValorTruco()) {
                mejorCarta = manoIA.getCarta2();
                posicionCarta = 2;
            } else {
                mejorCarta = manoIA.getCarta3();
                posicionCarta = 3;
            }
        }


        return new Jugada(TipoJugada.CARTA, posicionCarta);
    }

    @Override
    public Jugada tirarPeorCarta(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }
        Carta mejorCarta;
        Integer posicionCarta = 0;

        if (manoIA.getCarta1().getValorTruco() <= manoIA.getCarta2().getValorTruco()) {
            if (manoIA.getCarta1().getValorTruco() <= manoIA.getCarta3().getValorTruco()) {
                mejorCarta = manoIA.getCarta1();
                posicionCarta = 1;
            } else {
                mejorCarta = manoIA.getCarta3();
                posicionCarta = 3;
            }
        } else {
            if (manoIA.getCarta2().getValorTruco() <= manoIA.getCarta3().getValorTruco()) {
                mejorCarta = manoIA.getCarta2();
                posicionCarta = 2;
            } else {
                mejorCarta = manoIA.getCarta3();
                posicionCarta = 3;
            }
        }


        return new Jugada(TipoJugada.CARTA, posicionCarta);
    }

    @Override
    public Jugada cantarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada cantarEnvido(Long idPartida) {

        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }

        Integer envido = getEnvido(manoIA);

        if (envido <= 16) {
            return new Jugada(TipoJugada.ENVIDO, 0);
        }
        if (envido >= 17 && envido <= 25) {
            return new Jugada(TipoJugada.ENVIDO, 1);
        }
        if (envido >= 26 && envido <= 30) {
            return new Jugada(TipoJugada.ENVIDO, 2);
        }
        if (envido >= 31 && envido <= 33) {
            return new Jugada(TipoJugada.ENVIDO, 3);
        }

        return null;
    }

    private static Integer getEnvido(Mano manoIA) {
        Integer envido = 0;

        String paloCarta1 = manoIA.getCarta1().getPalo();
        String paloCarta2 = manoIA.getCarta2().getPalo();
        String paloCarta3 = manoIA.getCarta3().getPalo();

        Short numeroCarta1 = manoIA.getCarta1().getNumero();
        Short numeroCarta2 = manoIA.getCarta2().getNumero();
        Short numeroCarta3 = manoIA.getCarta3().getNumero();

        if (paloCarta1 == paloCarta2) {
            if (numeroCarta1 <= 9) {
                if (numeroCarta2 <= 9) {
                    envido = numeroCarta1 + numeroCarta2 + 20;
                } else {
                    envido = numeroCarta1 + 20;
                }
            } else {
                if (numeroCarta2 <= 9) {
                    envido = numeroCarta2 + 20;
                } else {
                    envido = 20;
                }
            }


        }

        if (paloCarta1 == paloCarta3) {
            if (numeroCarta1 <= 9) {
                if (numeroCarta3 <= 9) {
                    envido = numeroCarta1 + numeroCarta3 + 20;
                } else {
                    envido = numeroCarta1 + 20;
                }
            } else {
                if (numeroCarta3 <= 9) {
                    envido = numeroCarta3 + 20;
                } else {
                    envido = 20;
                }
            }


        }

        if (paloCarta3 == paloCarta2) {
            if (numeroCarta3 <= 9) {
                if (numeroCarta2 <= 9) {
                    envido = numeroCarta3 + numeroCarta2 + 20;
                } else {
                    envido = numeroCarta3 + 20;
                }
            } else {
                if (numeroCarta2 <= 9) {
                    envido = numeroCarta2 + 20;
                } else {
                    envido = 20;
                }
            }

        }
        return envido;
    }

    @Override
    public Jugada aceptarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada rechazarTruco(Long idPartida) {
        return null;
    }

    @Override
    public Jugada aceptarEnvido(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }

        Integer envido = getEnvido(manoIA);

        if (envido >= 17 && envido <= 25) {
            return new Jugada(TipoJugada.ENVIDO, 1);
        }
        if (envido >= 26 && envido <= 30) {
            return new Jugada(TipoJugada.ENVIDO, 2);
        }
        if (envido >= 31 && envido <= 33) {
            return new Jugada(TipoJugada.ENVIDO, 3);
        }

        return null;

    }




    @Override
    public Jugada rechazarEnvido(Long idPartida) {
        Mano manoIA = repositorioPartida.buscarCartasDeLaIa(idPartida);
        if (manoIA == null || manoIA.getCartas().isEmpty()) {
            return null;
        }

        Integer envido = getEnvido(manoIA);

        if(envido <= 16){
            return new Jugada(TipoJugada.ENVIDO,0);
        } else{
            return new Jugada(TipoJugada.ENVIDO,1);
        }

    }


}
