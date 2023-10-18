package model;

import java.util.Random;

public class CartonDeBingo {
    private int[][] numeros;
    private int identificadorUnico; 
    private Jugador jugadorAsignado;

    public CartonDeBingo() {
        this.numeros = new int[5][5];
        this.identificadorUnico = 0;
        this.jugadorAsignado = new Jugador("Sin asignar", "Sin correo", "0000000");;
    }

    /**
     * Generates a bingo card.
     *
     * @param  None
     * @return None
     */
    public void generarCarton() {
        Random rand = new Random();
        int[][] numerosCarton = new int[5][5];

        for (int columna = 0; columna < 5; columna++) {
            int min = columna * 15 + 1; // Mínimo valor de la columna actual.
            int max = min + 14; // Máximo valor de la columna actual.

            for (int fila = 0; fila < 5; fila++) {
                int numero;
                do {
                    numero = rand.nextInt(max - min + 1) + min;
                } while (existeNumeroEnCarton(numerosCarton, numero));
                if (fila == 2 && columna == 2){
                    numerosCarton[fila][columna] = -1;
                    continue;
                }
                numerosCarton[fila][columna] = numero;
            }
        }

        this.numeros = numerosCarton;
    }

    /**
     * A method to check if a given number exists in the given 2D array.
     *
     * @param  numerosCarton  the 2D array of integers representing the carton
     * @param  numero         the number to search for
     * @return                true if the number exists in the carton, false otherwise
     */
    private boolean existeNumeroEnCarton(int[][] numerosCarton, int numero) {
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (numerosCarton[fila][columna] == numero) {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * A description of the entire Java function.
     *
     * @param  numeros  the 2D array of numbers to be assigned to the board
     * @throws IllegalArgumentException if the 'numeros' matrix does not have dimensions 5x5
     * @throws IllegalArgumentException if the numbers in the board are not unique
     */
    public void setNumeros(int[][] numeros) {
        // Lógica para asignar los números proporcionados al cartón.
        if (numeros.length == 5 && numeros[0].length == 5) {
            // Validar que los números sean únicos en el cartón.
            if (sonNumerosUnicos(numeros)) {
                this.numeros = numeros;
            } else {
                throw new IllegalArgumentException("Los números en el cartón no son únicos.");
            }
        } else {
            throw new IllegalArgumentException("La matriz 'numeros' debe tener dimensiones 5x5.");
        }
    }

    /**
     * Recorre la matriz de números del cartón y busca el número a marcar.
     *
     * @param  numero  el número que se desea marcar en el cartón
     * @return         nada
     */
    public void marcarNumero(int numero) {
        // Recorre la matriz de números del cartón y busca el número a marcar.
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (numeros[fila][columna] == numero) {
                    // Marca el número en el cartón (usamos 0 para representar "no marcado").
                    numeros[fila][columna] = 0;
                    return; // Termina la función después de marcar el número.
                }
            }
        }
        // Si el número no se encuentra en el cartón, puedes lanzar una excepción o realizar alguna acción adicional.
    }

    /**
     * Verifies the winner based on the given game configuration.
     *
     * @param  configuracionPartida	the game configuration to check for the winner
     * @return         				true if there is a winner, false otherwise
     */
    public boolean verificarGanador(TipoPartida configuracionPartida) {
        switch (configuracionPartida) {
            case JUGAR_EN_X:
                return verificarGanadorX();
            case CUATRO_ESQUINAS:
                return verificarGanadorCuatroEsquinas();
            case CARTON_LLENO:
                return verificarGanadorCartonLleno();
            case JUGAR_EN_Z:
                return verificarGanadorZ();
            default:
                // Manejar otros casos de configuración aquí.
                return false;
        }
    }

    /**
     * Verifies if the X player is the winner.
     *
     * @return true if the X player is the winner, false otherwise.
     */
    private boolean verificarGanadorX() {
        // Verificar la diagonal principal (de izquierda a derecha).
        boolean diagonalPrincipalCompleta = true;
        for (int i = 0; i < 5; i++) {
            if (!esNumeroMarcado(numeros[i][i])) {
                diagonalPrincipalCompleta = false;
                break;
            }
        }

        // Verificar la diagonal secundaria (de derecha a izquierda).
        boolean diagonalSecundariaCompleta = true;
        for (int i = 0; i < 5; i++) {
            if (!esNumeroMarcado(numeros[i][4 - i])) {
                diagonalSecundariaCompleta = false;
                break;
            }
        }

        // El cartón es ganador si ambas diagonales están completas.
        return diagonalPrincipalCompleta && diagonalSecundariaCompleta;
    }

    /**
     * Recorre la matriz de números del cartón y busca el número.
     *
     * @param  numero    el número que se está buscando en la matriz
     * @return           true si el número está marcado y false si no está marcado
     */
    private boolean esNumeroMarcado(int numero) {
        // Recorre la matriz de números del cartón y busca el número.
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (numeros[fila][columna] == numero) {
                    // Si el número es igual a 0, significa que está marcado.
                    return numeros[fila][columna] == 0;
                }
            }
        }
        // Si el número no se encuentra en el cartón, consideramos que no está marcado.
        return false;
    }

    /**
     * Verifies if all four corners are marked.
     *
     * @return  true if all four corners are marked, false otherwise
     */
    private boolean verificarGanadorCuatroEsquinas() {
        // Verificar si las cuatro esquinas están marcadas.
        boolean esquinaSuperiorIzquierda = esNumeroMarcado(numeros[0][0]);
        boolean esquinaSuperiorDerecha = esNumeroMarcado(numeros[0][4]);
        boolean esquinaInferiorIzquierda = esNumeroMarcado(numeros[4][0]);
        boolean esquinaInferiorDerecha = esNumeroMarcado(numeros[4][4]);

        // El cartón es ganador si todas las esquinas están marcadas.
        return esquinaSuperiorIzquierda && esquinaSuperiorDerecha && esquinaInferiorIzquierda && esquinaInferiorDerecha;
    }

    /**
     * Verifica si todos los números del cartón están marcados.
     *
     * @return true si todos los números están marcados, false si hay al menos un número no marcado.
     */
    private boolean verificarGanadorCartonLleno() {
        // Verificar si todos los números del cartón están marcados.
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (!esNumeroMarcado(numeros[fila][columna])) {
                    return false; // El cartón no es ganador si hay al menos un número no marcado.
                }
            }
        }

        // Si todos los números están marcados, el cartón es ganador.
        return true;
    }

    /**
     * Verificar si los números en las esquinas y en el centro están marcados.
     *
     * @return true si el cartón es ganador en caso de "Jugar en Z", false si el cartón no es ganador.
     */
    private boolean verificarGanadorZ() {
        // Verificar si los números en las esquinas y en el centro están marcados.
        if (esNumeroMarcado(numeros[0][0]) && esNumeroMarcado(numeros[0][4]) &&
            esNumeroMarcado(numeros[4][0]) && esNumeroMarcado(numeros[4][4]) &&
            esNumeroMarcado(numeros[2][2])) {
            return true; // El cartón es ganador en caso de "Jugar en Z".
        }

        return false; // El cartón no es ganador.
    }


    /**
     * Checks if the numbers in the given matrix are unique.
     *
     * @param  numeros   the matrix of numbers to check
     * @return           true if all numbers are unique, false otherwise
     */
    private boolean sonNumerosUnicos(int[][] numeros) {
        // Use a boolean array to keep track of seen numbers.
        boolean[] seenNumbers = new boolean[101];

        // Recorre la matriz de números del cartón.
        for (int[] row : numeros) {
            for (int number : row) {
                // Check if the number has been seen before.
                if (seenNumbers[number]) {
                    return false; // If the number is repeated, they are not unique.
                }
                // Mark the number as seen.
                seenNumbers[number] = true;
            }
        }

        return true; // Todos los números son únicos.
    }
    

    /**SET Method Propertie jugadorAsignado*/
    public void setJugadorAsignado(Jugador jugadorAsignado){
        this.jugadorAsignado = jugadorAsignado;
    }//end method setJugadorAsignado

    /**GET Method Propertie jugadorAsignado*/
    public Jugador getJugadorAsignado(){
        return this.jugadorAsignado;
    }//end method getJugadorAsignado

    /**GET Method Propertie numeros*/
    public int[][] getNumeros(){
        return this.numeros;
    }//end method getNumeros

    /**GET Method Propertie identificadorUnico*/
    public int getIdentificadorUnico(){
        return this.identificadorUnico;
    }//end method getIdentificadorUnico

    /**SET Method Propertie identificadorUnico*/
    public void setIdentificadorUnico(int identificadorUnico){
        this.identificadorUnico = identificadorUnico;
        this.numeros[2][2] = identificadorUnico;
    }//end method setIdentificadorUnico
}
