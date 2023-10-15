package model;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


public class PartidaDeBingo {
    private int idPartida;
    private TipoPartida configuracionPartida;
    private ArrayList<Integer> numerosCantados;
    private ArrayList<CartonDeBingo> cartones;
    private ArrayList<Jugador> jugadoresGanadores;
    private Date fechaJuego;
    private Time horaJuego;
    private boolean partidaFinalizada;
    private CartonDeBingo cartonGanador;

    public PartidaDeBingo(TipoPartida pConfiguracionPartida, int pIdPartida) {
        this.idPartida = pIdPartida;
        this.configuracionPartida = pConfiguracionPartida;
        this.numerosCantados = new ArrayList<>();
        this.cartones = new ArrayList<>(); // Agregamos la inicialización de la lista de cartones.
        this.jugadoresGanadores = new ArrayList<>();
        this.fechaJuego = new Date();
        this.horaJuego = new Time(System.currentTimeMillis());
        this.partidaFinalizada = false; // Inicializamos como partida no finalizada.
        this.cartonGanador = null; // Inicializamos el cartón ganador como nulo.
    }
    

    /**
     * Starts a game session.
     *
     * @return void
     */
    public void iniciarPartida(ArrayList<Jugador> jugadores) {
        // Verificar que haya suficientes jugadores para iniciar una partida.
        if (jugadores.size() < 2) {
            System.out.println("No hay suficientes jugadores para iniciar una partida.");
            return;
        }

        /*
        IniciarPartida(configuracionPartida, cartones):
            Si la partidaActual no está finalizada:
                Mostrar un mensaje de confirmación para continuar con la partida actual.
                Si el usuario confirma continuar:
                    Reiniciar la partida con la nueva configuración y cartones.
                Si el usuario no confirma continuar:
                    Finalizar la partida actual y luego iniciar una nueva partida con la configuración y cartones proporcionados.
            Si la partidaActual está finalizada:
                Iniciar una nueva partida con la configuración y cartones proporcionados.

         */
    }

    private void finalizarPartida() {
        // Finaliza la partida.
        System.out.println("La partida ha finalizado.");

        /*
        finalizarPartida(cartonGanador, numerosCantados):
            Asignar el cartón ganador a la partidaActual.
            Asignar la lista de números cantados a la partidaActual.
            Marcar la partida como finalizada.
            Identificar los jugadores ganadores comparando los cartones con el cartón ganador.
            Mostrar a los jugadores ganadores y los premios si los hay.
            Actualizar la lista de partidas jugadas.
            Limpiar la lista de cartones utilizados en la partida.
        */
    }

    /**
     * Generates a random number between 1 and 75.
     *
     * @return the generated random number
     */
    private int generarNumeroAleatorio() {
        // Genera un número aleatorio entre 1 y 75.
        return new Random().nextInt(75) + 1;
    }

    public ArrayList<Integer> verificarCartonesGanadores(ArrayList<CartonDeBingo> cartones, ArrayList<Integer> numerosCantados, TipoPartida configuracionPartida) {
        ArrayList<Integer> cartonesGanadores = new ArrayList<>();
    
        // Itera a través de la lista de cartones.
        for (CartonDeBingo carton : cartones) {
            // Verifica si el cartón es ganador bajo la configuración actual.
            if (esCartonGanador(carton, numerosCantados, configuracionPartida)) {
                // Agrega el identificador del cartón ganador a la lista.
                cartonesGanadores.add(carton.getIdentificadorUnico());
            }
        }
    
        return cartonesGanadores;
    }

    private boolean esCartonGanador(CartonDeBingo carton, ArrayList<Integer> numerosCantados, TipoPartida configuracionPartida) {
        switch (configuracionPartida) {
            case JUGAR_EN_X:
                return esCartonGanadorEnX(carton, numerosCantados);
            case CUATRO_ESQUINAS:
                return esCartonGanadorCuatroEsquinas(carton, numerosCantados);
            case CARTON_LLENO:
                return esCartonGanadorCartonLleno(carton, numerosCantados);
            case JUGAR_EN_Z:
                return esCartonGanadorEnZ(carton, numerosCantados);
            default:
                // Manejar otros casos de configuración aquí.
                return false;
        }
    }

    private boolean esCartonGanadorEnX(CartonDeBingo carton, ArrayList<Integer> numerosCantados) {
        int[][] numerosCarton = carton.getNumeros();
    
        // Verificar la diagonal principal (de izquierda a derecha).
        boolean diagonalPrincipalCompleta = true;
        for (int i = 0; i < 5; i++) {
            if (!numerosCantados.contains(numerosCarton[i][i])) {
                diagonalPrincipalCompleta = false;
                break;
            }
        }
    
        // Verificar la diagonal secundaria (de derecha a izquierda).
        boolean diagonalSecundariaCompleta = true;
        for (int i = 0; i < 5; i++) {
            if (!numerosCantados.contains(numerosCarton[i][4 - i])) {
                diagonalSecundariaCompleta = false;
                break;
            }
        }
    
        // El cartón es ganador si ambas diagonales están completas.
        return diagonalPrincipalCompleta && diagonalSecundariaCompleta;
    }
    
    
    private boolean esCartonGanadorCuatroEsquinas(CartonDeBingo carton, ArrayList<Integer> numerosCantados) {
        int[][] numerosCarton = carton.getNumeros();
    
        // Verificar si las cuatro esquinas están en la lista de números cantados.
        boolean esquinaSuperiorIzquierda = numerosCantados.contains(numerosCarton[0][0]);
        boolean esquinaSuperiorDerecha = numerosCantados.contains(numerosCarton[0][4]);
        boolean esquinaInferiorIzquierda = numerosCantados.contains(numerosCarton[4][0]);
        boolean esquinaInferiorDerecha = numerosCantados.contains(numerosCarton[4][4]);
    
        // El cartón es ganador si todas las esquinas están en la lista de números cantados.
        return esquinaSuperiorIzquierda && esquinaSuperiorDerecha && esquinaInferiorIzquierda && esquinaInferiorDerecha;
    }
    
    
    private boolean esCartonGanadorCartonLleno(CartonDeBingo carton, ArrayList<Integer> numerosCantados) {
        int[][] numerosCarton = carton.getNumeros();
    
        // Itera a través de la matriz de números del cartón.
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                int numero = numerosCarton[fila][columna];
    
                // Verifica si el número del cartón no está en la lista de números cantados.
                if (!numerosCantados.contains(numero)) {
                    return false; // Si encuentra un número no cantado, el cartón no es ganador.
                }
            }
        }
    
        // Si todos los números del cartón están en la lista de números cantados, el cartón es ganador.
        return true;
    }
    
    
    private boolean esCartonGanadorEnZ(CartonDeBingo carton, ArrayList<Integer> numerosCantados) {
        int[][] numerosCarton = carton.getNumeros();
    
        // Verifica si los números en las esquinas y en el centro están en la lista de números cantados.
        boolean esquinaSuperiorIzquierda = numerosCantados.contains(numerosCarton[0][0]);
        boolean esquinaSuperiorDerecha = numerosCantados.contains(numerosCarton[0][4]);
        boolean esquinaInferiorIzquierda = numerosCantados.contains(numerosCarton[4][0]);
        boolean esquinaInferiorDerecha = numerosCantados.contains(numerosCarton[4][4]);
        boolean centro = numerosCantados.contains(numerosCarton[2][2]);
    
        // El cartón es ganador en el patrón "Jugar en Z" si todas las posiciones están en la lista de números cantados.
        return esquinaSuperiorIzquierda && esquinaSuperiorDerecha && esquinaInferiorIzquierda && esquinaInferiorDerecha && centro;
    }
    

    private void anunciarGanadores() {
        // Anuncia a los jugadores ganadores.
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

