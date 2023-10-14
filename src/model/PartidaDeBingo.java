package model;

import java.io.File;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.FontMetrics;


public class PartidaDeBingo {
    private int idPartida;
    private TipoPartida configuracionPartida;
    private ArrayList<Integer> numerosCantados;
    private ArrayList<Jugador> jugadores;
    private ArrayList<Jugador> jugadoresGanadores;
    private ArrayList<CartonDeBingo> cartones;
    private Date fechaJuego;
    private Time horaJuego;

    public PartidaDeBingo(TipoPartida pConfiguracionPartida, int pIdPartida) {
        // Inicializamos el ID de la partida, puedes asignar un valor adecuado.
        this.idPartida = pIdPartida;
        this.configuracionPartida = pConfiguracionPartida;
        this.numerosCantados = new ArrayList<>();
        this.jugadoresGanadores = new ArrayList<>();
        this.cartones = new ArrayList<>();
        this.fechaJuego = new Date(); // Utilizamos la fecha actual.
        this.horaJuego = new Time(System.currentTimeMillis()); // Utilizamos la hora actual.
    }

    public void generarImagenesCartones(CartonDeBingo[] listaDeCartones) {
        try {
            for (CartonDeBingo carton : listaDeCartones) {
                // Crear una nueva imagen con un tamaño de 300x200.
                int width = 300;
                int height = 400;
                BufferedImage imagenCarton = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
                // Obtener el contexto gráfico para dibujar en la imagen.
                Graphics2D g = imagenCarton.createGraphics();
    
                // Establecer un fondo blanco para toda la imagen.
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                // Establecer el fondo de color azul claro para el encabezado.
                g.setColor(new Color(173, 216, 230));
                g.fillRect(0, 0, width, 30); // 30 píxeles para el encabezado.

                // Pintar la celda de la columna 2 y fila 2 de amarillo claro.
                g.setColor(new Color(255, 255, 153)); // Amarillo claro.
                int cellWidth = width / 5;
                int cellHeight = (height - 30) / 5;
                int x = 2 * cellWidth;
                int y = 2 * cellHeight + 30;
                g.fillRect(x, y, cellWidth, cellHeight);

                // Dibujar un marco negro alrededor de la imagen.
                g.setColor(new Color(0, 0, 0));
                g.drawRect(0, 0, width - 1, height - 1);

                // Dibujar líneas divisorias.
                g.setColor(Color.BLACK);
                // Líneas verticales.
                for (int i = 1; i < 5; i++) {
                    int xLine = i * cellWidth;
                    g.drawLine(xLine, 30, xLine, height);
                }
                // Líneas horizontales.
                for (int i = 0; i < 5; i++) {
                    int yLine = i * cellHeight + 30;
                    g.drawLine(0, yLine, width, yLine);
                }
    
                // Agregar la palabra "BINGO".
                g.setColor(Color.BLACK);
                g.setFont(new Font("Arial", Font.BOLD, 24));
                g.drawString("BINGO", 110, 24);
    
                // Configurar fuente y color para los números.
                g.setColor(Color.BLACK);
                Font font = new Font("Arial", Font.BOLD, 20);
                g.setFont(font);
                FontMetrics fm = g.getFontMetrics();
    
                int[][] numeros = carton.getNumeros();
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        int numero = numeros[fila][columna];
                        String numeroStr = String.valueOf(numero);
                        int xNum = columna * (width / 5) + (width / 10) - fm.stringWidth(numeroStr) / 2;
                        int yNum = 30 + fila * ((height - 30) / 5) + 50; // Ajusta la posición vertical para centrar el número.
                        g.drawString(numeroStr, xNum, yNum);
                    }
                }
    
                // Guardar la imagen en un archivo con el nombre del identificador único del cartón.
                int identificador = carton.getIdentificadorUnico();
                String nombreArchivo = identificador + ".png";
                String rutaCarpetaResources = "resources/";
                File archivo = new File(rutaCarpetaResources,nombreArchivo);
                ImageIO.write(imagenCarton, "png", archivo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    
    public void generarCartones(int cantidad) {
        // Limpiar la lista de cartones existentes si es necesario.
        cartones.clear();

        for (int i = 0; i < cantidad; i++) {
            CartonDeBingo carton = new CartonDeBingo();
            // Genera un cartón y agrégalo a la lista de cartones.
            carton.generarCarton();
            cartones.add(carton);
            carton.setIdentificadorUnico(i + 1);
        }
        generarImagenesCartones(cartones.toArray(new CartonDeBingo[cartones.size()]));
    }

    /**
     * Starts a game session.
     *
     * @return void
     */
    public void iniciarPartida() {
        // Verificar que haya suficientes jugadores para iniciar una partida.
        if (jugadores.size() < 2) {
            System.out.println("No hay suficientes jugadores para iniciar una partida.");
            return;
        }

        // Inicializar la lista de números cantados.
        numerosCantados.clear();

        // Comenzar a cantar números hasta que haya al menos un cartón ganador.
        while (true) {
            // Generar un número aleatorio entre 1 y 75.
            int numeroCantado = generarNumeroAleatorio();

            // Agregar el número a la lista de números cantados.
            numerosCantados.add(numeroCantado);

            // Verificar si hay algún cartón ganador bajo la configuración actual.
            boolean hayGanador = verificarCartonesGanadores(numeroCantado);

            if (hayGanador) {
                // Anunciar a los jugadores ganadores.
                anunciarGanadores();
                break; // Terminar la partida si hay ganadores.
            }
        }

        // Finalizar la partida.
        finalizarPartida();
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

    private boolean verificarCartonesGanadores(int numeroCantado) {
        // Variable para rastrear si hay al menos un ganador.
        boolean hayGanador = false;

        // Itera a través de la lista de cartones.
        for (CartonDeBingo carton : cartones) {
            // Verifica si el cartón está marcado con el número cantado.
            int[][] numerosCarton = carton.getNumeros();
            for (int fila = 0; fila < 5; fila++) {
                for (int columna = 0; columna < 5; columna++) {
                    if (numerosCarton[fila][columna] == numeroCantado) {
                        // Marca el número en el cartón.
                        carton.marcarNumero(numeroCantado);

                        // Verifica si el cartón es ganador bajo la configuración actual.
                        if (esCartonGanador(carton)) {
                            hayGanador = true;
                            // Agrega el jugador ganador si el cartón está asignado a un jugador.
                            Jugador jugadorGanador = carton.getJugadorAsignado();
                            if (jugadorGanador != null) {
                                jugadoresGanadores.add(jugadorGanador);
                            }
                        }
                    }
                }
            }
        }

        return hayGanador;
    }

    private boolean esCartonGanador(CartonDeBingo carton) {
        // Obtén la matriz de números del cartón.
        int[][] numerosCarton = carton.getNumeros();

        // Verifica si todos los números en el cartón están marcados.
        for (int fila = 0; fila < 5; fila++) {
            for (int columna = 0; columna < 5; columna++) {
                if (numerosCarton[fila][columna] != -1) {
                    // Si encuentra un número no marcado, el cartón no es ganador.
                    return false;
                }
            }
        }

        // Si todos los números están marcados, el cartón es ganador.
        return true;
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

    private void finalizarPartida() {
        // Finaliza la partida.
        System.out.println("La partida ha finalizado.");

        // Registra la partida en el historial o registro de partidas.
        RegistroDePartidas registroPartidas = new RegistroDePartidas();
        registroPartidas.agregarPartida(this);

        // Limpia la lista de jugadores ganadores y números cantados.
        jugadoresGanadores.clear();
        numerosCantados.clear();
    }

    public void verificarCartones(ArrayList<CartonDeBingo> cartones, int numeroCantado) {
        // Itera a través de la lista de cartones.
        for (CartonDeBingo carton : cartones) {
            // Obtiene la matriz de números del cartón.
            int[][] numerosCarton = carton.getNumeros();

            // Verifica si el número cantado está presente en el cartón.
            for (int fila = 0; fila < 5; fila++) {
                for (int columna = 0; columna < 5; columna++) {
                    if (numerosCarton[fila][columna] == numeroCantado) {
                        // Marca el número en el cartón.
                        carton.marcarNumero(numeroCantado);

                        // Verifica si el cartón es ganador bajo la configuración actual.
                        if (esCartonGanador(carton)) {
                            // Agrega el jugador ganador si el cartón está asignado a un jugador.
                            Jugador jugadorGanador = carton.getJugadorAsignado();
                            if (jugadorGanador != null) {
                                jugadoresGanadores.add(jugadorGanador);
                            }
                        }
                    }
                }
            }
        }
    }

    public CartonDeBingo buscarCartonPorIdentificador(int identificador) {
        for (CartonDeBingo carton : cartones) {
            if (carton.getIdentificadorUnico() == identificador) {
                return carton; // Si se encuentra el cartón, lo retornamos.
            }
        }
        return null; // Si no se encuentra, retornamos null.
    }

    public ArrayList<Jugador> getJugadores() {
        return this.jugadores;
    }
}

