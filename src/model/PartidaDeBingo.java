package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class PartidaDeBingo {
    private int idPartida;
    private TipoPartida configuracionPartida;
    private double montoPremio;
    private List<Integer> numerosCantados = new ArrayList<>();
    private List<CartonDeBingo> cartones = new ArrayList<>();
    private List<Jugador> jugadoresGanadores = new ArrayList<>();
    private boolean partidaFinalizada;
    private CartonDeBingo cartonGanador;

    public PartidaDeBingo(TipoPartida pConfiguracionPartida, int pIdPartida) {
        this.idPartida = pIdPartida;
        this.configuracionPartida = pConfiguracionPartida;
        this.partidaFinalizada = false;
        this.cartonGanador = null;
        this.montoPremio = 0.0;
    }

    public void iniciarPartida(List<Jugador> jugadores, List<CartonDeBingo> nuevosCartones) {
        if (jugadores.size() < 2) {
            System.out.println("No hay suficientes jugadores para iniciar una partida.");
            return;
        }

        if (!partidaFinalizada) {
            System.out.print("¿Desea continuar con la partida actual? (Sí/No): ");
            Scanner scanner = new Scanner(System.in);
            String respuesta = scanner.next();
            if (respuesta.equalsIgnoreCase("Sí")) {
                System.out.println("Continuando con la partida actual.");
            } else {
                finalizarPartida();
                iniciarNuevaPartida(nuevosCartones);
            }
        } else {
            iniciarNuevaPartida(nuevosCartones);
        }
    }

    private void reiniciarVariables() {
        numerosCantados.clear();
        jugadoresGanadores.clear();
        cartonGanador = null;
        montoPremio = 0.0;
    }

    public void iniciarNuevaPartida(List<CartonDeBingo> nuevosCartones) {
        reiniciarVariables();
        cartones.addAll(nuevosCartones);
        System.out.println("Iniciando una nueva partida de Bingo.");

        while (!partidaFinalizada) {
            int numeroAleatorio = generarNumeroAleatorio();
            numerosCantados.add(numeroAleatorio);

            List<CartonDeBingo> cartonesGanadores = verificarCartonesGanadores(configuracionPartida);

            if (!cartonesGanadores.isEmpty()) {
                finalizarPartida();
            }

            System.out.println("Números cantados: " + numerosCantados);
        }

        anunciarGanadores();
    }

    public void finalizarPartida() {
        asignarCartonGanador();
        numerosCantados.addAll(obtenerNumerosCantados());
        partidaFinalizada = true;
        identificarJugadoresGanadores();
        anunciarGanadores();
        cartones.clear();
    }

    private List<CartonDeBingo> verificarCartonesGanadores(TipoPartida configuracionPartida) {
        List<CartonDeBingo> cartonesGanadores = new ArrayList<>();
    
        for (CartonDeBingo carton : cartones) {
            if (carton.verificarGanador(configuracionPartida)) {
                cartonesGanadores.add(carton);
            }
        }

        return cartonesGanadores;
    }

    private List<Integer> obtenerNumerosCantados() {
        List<Integer> numeros = new ArrayList<>();
        // Lógica para obtener los números cantados
        return numeros;
    }

    private void identificarJugadoresGanadores() {
        for (CartonDeBingo carton : cartones) {
            if (carton.verificarGanador(configuracionPartida)) {
                Jugador jugadorGanador = carton.getJugadorAsignado();
                jugadoresGanadores.add(jugadorGanador);
            }
        }
    }

    private int generarNumeroAleatorio() {
        return new Random().nextInt(75) + 1;
    }

    private void asignarCartonGanador() {
        // Lógica para asignar el cartón ganador.
    }

    private void anunciarGanadores() {
        if (!jugadoresGanadores.isEmpty()) {
            System.out.println("Jugadores ganadores:");

            for (Jugador jugador : jugadoresGanadores) {
                System.out.println(jugador.getNombre());
            }
        } else {
            System.out.println("No hay jugadores ganadores en esta partida.");
        }
    }
}
