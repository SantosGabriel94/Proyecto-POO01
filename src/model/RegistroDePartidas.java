package model;

import java.util.ArrayList;

public class RegistroDePartidas {
    private ArrayList<PartidaDeBingo> partidas;

    public RegistroDePartidas() {
        partidas = new ArrayList<>();
    }

    public ArrayList<PartidaDeBingo> getPartidas() {
        return partidas;
    }

    public void setPartidas(ArrayList<PartidaDeBingo> partidas) {
        this.partidas = partidas;
    }

    public void agregarPartida(PartidaDeBingo partida) {
        partidas.add(partida);
    }
    
    public static void cargarRegistroPartidas(){
        int num = 0;
    }
}