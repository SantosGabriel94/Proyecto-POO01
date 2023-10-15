package model;

import java.util.ArrayList;

public class Jugadores {
    private static ArrayList<Jugador> jugadores;

    public Jugadores() {
        jugadores = new ArrayList<>();
    }

    public void guardarJugadores() {
        // guarda todos los jugadores en la BD
    }

    public void añadirJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void quitarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public ArrayList<Jugador> getJugadores() {
        return Jugadores.jugadores;
    }
}
