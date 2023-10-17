package model;

import java.util.ArrayList;
import java.util.List;

public class RegistroDePartidas {
    private List<PartidaDeBingo> partidas = new ArrayList<>();

    public RegistroDePartidas() {
        // No es necesario inicializar la lista aquí, ya que se inicializa en la declaración.
    }

    public List<PartidaDeBingo> getPartidas() {
        return new ArrayList<>(partidas); // Devuelve una copia de la lista para evitar modificaciones externas.
    }

    public void agregarPartida(PartidaDeBingo partida) {
        partidas.add(partida);
    }
    
    public void cargarRegistroPartidas() {
        // Aquí puedes implementar la lógica para cargar el registro de partidas, si es necesario.
    }
}
