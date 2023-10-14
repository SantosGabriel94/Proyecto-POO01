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
                // Crear una nueva imagen en blanco con un tamaño adecuado (ajusta el tamaño según tus necesidades).
                int width = 200; // Ancho de la imagen en píxeles.
                int height = 200; // Alto de la imagen en píxeles.
                BufferedImage imagenCarton = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                // Obtener el contexto gráfico para dibujar en la imagen.
                Graphics2D g = imagenCarton.createGraphics();

                // Establecer un fondo blanco.
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, width, height);

                // Configurar fuente y color para los números.
                g.setColor(Color.BLACK);
                Font font = new Font("Arial", Font.PLAIN, 16);
                g.setFont(font);

                int[][] numeros = carton.getNumeros();
                for (int fila = 0; fila < 5; fila++) {
                    for (int columna = 0; columna < 5; columna++) {
                        int numero = numeros[fila][columna];
                        // Dibujar el número en la posición correspondiente.
                        int x = columna * (width / 5);
                        int y = fila * (height / 5) + 16; // Ajusta la posición vertical para centrar el número.
                        g.drawString(String.valueOf(numero), x, y);
                    }
                }

                // Dibujar el identificador único del cartón en el centro de la imagen.
                int identificador = carton.getIdentificadorUnico();
                int x = width / 2 - 20; // Ajusta la posición horizontal para centrar el identificador.
                int y = height / 2 + 20; // Ajusta la posición vertical para centrar el identificador.
                //g.drawString(identificador, x, y);

                // Guardar la imagen en un archivo con el nombre del identificador único del cartón.
                String nombreArchivo = identificador + ".png";
                File archivo = new File(nombreArchivo);
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

