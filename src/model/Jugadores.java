package model;

import java.util.ArrayList;
import java.util.List;

public class Jugadores {
    private List<Jugador> jugadores = new ArrayList<>();

    public Jugadores() {
        // No se necesita inicializar la lista aquí, ya que se inicializa en la declaración.
    }

    public void guardarJugadoresEnBD() {
        // Lógica para guardar todos los jugadores en la base de datos.
    }

    public void añadirJugador(Jugador jugador) {
        jugadores.add(jugador);
    }

    public void quitarJugador(Jugador jugador) {
        jugadores.remove(jugador);
    }

    public List<Jugador> getJugadores() {
        return new ArrayList<>(jugadores); // Devuelve una copia de la lista para evitar modificaciones externas.
    }

    public Jugador buscarJugadorPorCedula(String cedula) {
        for (Jugador jugador : jugadores) {
            if (jugador.getCedula().equals(cedula)) {
                return jugador; // Devuelve el jugador si se encuentra
            }
        }
        return null; // Retorna null si no se encuentra ningún jugador con esa cédula
    }
}

