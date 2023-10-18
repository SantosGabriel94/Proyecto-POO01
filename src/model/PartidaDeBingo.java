package model;

import java.util.ArrayList;
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

    /**
     * Inicia una partida de bingo con la lista de jugadores y los nuevos cartones.
     *
     * @param  jugadores        la lista de jugadores que participarán en la partida
     * @param  nuevosCartones   la lista de nuevos cartones de bingo para la partida
     */
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

    /**
     * Resets the variables used in the Java function.
     */
    private void reiniciarVariables() {
        numerosCantados.clear();
        jugadoresGanadores.clear();
        cartonGanador = null;
        montoPremio = 0.0;
    }

    /**
     * Initializes a new game of Bingo with the given list of bingo cards.
     *
     * @param  nuevosCartones  a list of new bingo cards to be added to the game
     */
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

    /**
     * Finalizes the game by assigning the winning card,
     * adding the called numbers to the list of called numbers,
     * marking the game as finished, identifying the winning players,
     * announcing the winners, and clearing the list of cards.
     *
     * @param  None
     * @return None
     */
    public void finalizarPartida() {
        asignarCartonGanador();
        numerosCantados.addAll(obtenerNumerosCantados());
        partidaFinalizada = true;
        identificarJugadoresGanadores();
        anunciarGanadores();
        cartones.clear();
    }

    /**
     * Verifies the winning bingo cards based on the specified game configuration.
     *
     * @param  configuracionPartida  the game configuration to be used for verification
     * @return                       a list of winning bingo cards
     */
    private List<CartonDeBingo> verificarCartonesGanadores(TipoPartida configuracionPartida) {
        List<CartonDeBingo> cartonesGanadores = new ArrayList<>();
    
        for (CartonDeBingo carton : cartones) {
            if (carton.verificarGanador(configuracionPartida)) {
                cartonesGanadores.add(carton);
            }
        }

        return cartonesGanadores;
    }

    /**
     * Retrieves the list of numbers that have been sung.
     *
     * @return         	A list of integers representing the numbers that have been sung.
     */
    private List<Integer> obtenerNumerosCantados() {
        List<Integer> numeros = new ArrayList<>();
        // Lógica para obtener los números cantados
        return numeros;
    }

    /**
     * Identifies the players who have won the game.
     *
     * @param  None    This function does not take any parameters.
     * @return None    This function does not return any value.
     */
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

    /**
     * Anuncia los jugadores ganadores de la partida.
     *
     * @param  None  No recibe parámetros.
     * @return       No devuelve ningún valor.
     */
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
