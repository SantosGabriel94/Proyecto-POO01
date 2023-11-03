package console;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.CartonDeBingo;
import model.Cartones;
import model.CorreoElectronico;
import model.Jugador;
import model.Jugadores;
import model.PartidaDeBingo;
import model.RegistroDePartidas;
import model.TipoPartida;

public class InterfazBingo {
    private RegistroDePartidas registroPartidas;
    private PartidaDeBingo partida;
    private Jugadores jugadores;
    private Cartones cartones;
    private Connection connection; // Conexión a la base de datos

    public InterfazBingo() {
        registroPartidas = new RegistroDePartidas();
        partida = new PartidaDeBingo(TipoPartida.CARTON_LLENO, registroPartidas.getPartidas().size() + 1);
        jugadores = new Jugadores();
        cartones = new Cartones();
        
        // Inicializar la conexión a la base de datos
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:BD_BINGO.db");
            System.out.println("Conexión a la base de datos establecida.");
        } catch (SQLException e) {
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        inicializarJugadores();
        cartones.generarCartones(5);

        do {
            clearConsole();
            System.out.println("Sistema de Gestión de Bingos");
            System.out.println("1. Generar Cartones");
            System.out.println("2. Ver Cartón");
            System.out.println("3. Registrar Jugador");
            System.out.println("4. Asignar Jugador a Cartón");
            System.out.println("5. Iniciar Partida");
            System.out.println("6. Generar Word Cloud");
            System.out.println("7. Generar Estadísticas");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                String input = scanner.nextLine();
                opcion = Integer.parseInt(input);

                switch (opcion) {
                    case 1:
                        clearConsole();
                        generarCartones();
                        sleep(5000);
                        break;
                    case 2:
                        clearConsole();
                        verCarton();
                        sleep(6000);
                        break;
                    case 3:
                        clearConsole();
                        registrarJugador();
                        sleep(2000);
                        break;
                    case 4:
                        clearConsole();
                        enviarCartonAJugador();
                        sleep(5000);
                        break;
                    case 5:
                        clearConsole();
                        iniciarPartida();
                        sleep(2000);
                        break;
                    case 6:
                        clearConsole();
                        generarWordCloud();
                        sleep(2000);
                        break;
                    case 7:
                        clearConsole();
                        generarEstadisticas();
                        sleep(2000);
                        break;
                    case 8:
                        System.out.println("Saliendo del sistema...");
                        sleep(1000);
                        break;
                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                        sleep(2000);
                        break;
                }
            } catch (Exception e) {
                System.err.println("Error al leer la opción. Asegúrate de ingresar un número válido.");
                opcion = 0; // Reiniciamos la opción en caso de error.
                scanner.nextLine(); // Limpiamos el búfer de entrada.
            }
        } while (opcion != 8);

        scanner.close();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void inicializarJugadores() {
        Jugador luisSoto = new Jugador("Luis Soto", "erickrobre46@gmail.com", "103250410");
        Jugador manuelMurillo = new Jugador("Manuel Murillo", "erirodriguez@estudiantec.cr", "31025321");
        Jugador erickRojas = new Jugador("Erick Rojas", "erick.rodriguez2@aiesec.net", "303250410");

        jugadores.anadirJugador(luisSoto);
        jugadores.anadirJugador(manuelMurillo);
        jugadores.anadirJugador(erickRojas);
    }

    private void generarCartones() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de cartones a generar: ");
        int cantidad = scanner.nextInt();

        try {
            cartones.generarCartones(cantidad);
        } catch (Exception e) {
            System.err.println("Error al generar cartones: " + e.getMessage());
        }

        System.out.println(cantidad + " cartones generados con éxito.");

        ArrayList<Integer> identificadoresDisponibles = cartones.obtenerIdentificadoresDisponibles();
        System.out.println("Identificadores de cartones disponibles:");
        for (int identificador : identificadoresDisponibles) {
            if (identificador % 21 == 0) {
                System.out.println();
            }
            System.out.print(" " + identificador + " ");
        }
    }

    private void verCarton() {
        ArrayList<Integer> identificadoresDisponibles = cartones.obtenerIdentificadoresDisponibles();

        if (identificadoresDisponibles.isEmpty()) {
            System.out.println("No hay cartones disponibles para mostrar. Lista vacía.");
            return;
        }

        System.out.println("Identificadores de cartones disponibles:");
        for (int identificador : identificadoresDisponibles) {
            if (identificador % 21 == 0) {
                System.out.println();
            }
            System.out.print(" " + identificador + " ");
        }

        int identificadorCarton = obtenerIdentificadorCarton(identificadoresDisponibles);
        verCarton(identificadorCarton);
    }

    private int obtenerIdentificadorCarton(ArrayList<Integer> identificadoresDisponibles) {
        Scanner scanner = new Scanner(System.in);
        int identificadorCarton;

        while (true) {
            System.out.println("\nIngrese el identificador del cartón que desea ver: ");
            if (scanner.hasNextInt()) {
                identificadorCarton = scanner.nextInt();
                if (identificadoresDisponibles.contains(identificadorCarton)) {
                    break;
                } else {
                    System.out.println("Identificador no válido. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.next();
            }
        }

        return identificadorCarton;
    }

    private void verCarton(int identificadorCarton) {
        String rutaImagenCarton = "resources/" + identificadorCarton + ".png";

        try {
            BufferedImage imagenCarton = ImageIO.read(new File(rutaImagenCarton));
            if (imagenCarton != null) {
                JFrame frame = new JFrame("Cartón de Bingo");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new JLabel(new ImageIcon(imagenCarton)));
                frame.pack();
                frame.setVisible(true);

                CartonDeBingo carton = cartones.buscarCartonPorIdentificador(identificadorCarton);
                Jugador jugador = carton.getJugadorAsignado();
                if (jugador != null) {
                    System.out.println("Este cartón está asignado a:");
                    System.out.println("Nombre: " + jugador.getNombre());
                    System.out.println("Cédula: " + jugador.getCedula());
                    System.out.println("Correo: " + jugador.getCorreoElectronico());
                } else {
                    System.out.println("Este cartón no está asignado a ningún jugador.");
                }
            } else {
                System.out.println("No se pudo cargar la imagen del cartón.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrarJugador() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Registrando jugador...");
        System.out.print("Ingrese el nombre del jugador: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese el correo electrónico del jugador: ");
        String correo = scanner.nextLine();

        System.out.print("Ingrese la cédula del jugador: ");
        String cedula = scanner.nextLine();

        Jugador nuevoJugador = new Jugador(nombre, correo, cedula);
        jugadores.anadirJugador(nuevoJugador);

        System.out.println("Jugador registrado con éxito.");
    }

    private void enviarCartonAJugador() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cédula del jugador: ");
        String cedula = scanner.nextLine();

        Jugador jugador = jugadores.buscarJugadorPorCedula(cedula);

        if (jugador == null) {
            System.out.println("No se encontró un jugador con esa cédula.");
            return;
        }

        System.out.print("Ingrese la cantidad de cartones a enviar: ");
        int cantidadCartones;

        try {
            cantidadCartones = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Cantidad de cartones no válida. Debe ingresar un número entero.");
            return;
        }

        ArrayList<Integer> identificadoresDisponibles = cartones.obtenerIdentificadoresDisponibles();
        ArrayList<Integer> cartonesSinAsignar = obtenerCartonesSinAsignar(identificadoresDisponibles);

        if (cartonesSinAsignar.isEmpty()) {
            System.out.println("No hay cartones disponibles para enviar a este jugador.");
            return;
        }

        if (cantidadCartones > cartonesSinAsignar.size()) {
            System.out.println("No hay suficientes cartones disponibles para enviar la cantidad solicitada.");
            return;
        }

        Collections.shuffle(cartonesSinAsignar);
        List<Integer> cartonesAEnviar = cartonesSinAsignar.subList(0, cantidadCartones);

        ArrayList<CartonDeBingo> cartonesEnviados = new ArrayList<>();

        for (int identificador : cartonesAEnviar) {
            CartonDeBingo carton = cartones.buscarCartonPorIdentificador(identificador);
            carton.setJugadorAsignado(jugador);
            cartonesEnviados.add(carton);
        }

        try {
            StringBuilder cuerpoCorreo = new StringBuilder();
            cuerpoCorreo.append("¡Hola ").append(jugador.getNombre()).append("!\n\n");
            cuerpoCorreo.append("Adjunto encontrarás ").append(cantidadCartones).append(" cartones de bingo.\n\n");

            CorreoElectronico correoElectronico = new CorreoElectronico();
            correoElectronico.enviarCorreoConCartones(jugador.getCorreoElectronico(), "Cartones de Bingo",
                    cuerpoCorreo.toString(), cartonesEnviados);

            System.out.println(
                    "Se han enviado los cartones al jugador " + jugador.getNombre() + " a su dirección de correo: "
                            + jugador.getCorreoElectronico());
        } catch (Exception e) {
            System.out.println("Se ha producido un error al enviar el correo electrónico.");
            e.printStackTrace();
        }
    }

    private ArrayList<Integer> obtenerCartonesSinAsignar(ArrayList<Integer> identificadoresDisponibles) {
        ArrayList<Integer> cartonesSinAsignar = new ArrayList<>();

        for (int identificador : identificadoresDisponibles) {
            CartonDeBingo carton = cartones.buscarCartonPorIdentificador(identificador);

            if (carton.getJugadorAsignado().getNombre().equals("Sin asignar")) {
                cartonesSinAsignar.add(identificador);
            }
        }

        return cartonesSinAsignar;
    }

    private void iniciarPartida() {
        System.out.println("Iniciando partida...");
        if (preguntarContinuarPartida()) {
            obtenerConfiguracionYPremio();
        }
    }

    private boolean preguntarContinuarPartida() {
        boolean continuarPartida = false;
        Scanner scanner = new Scanner(System.in);

        System.out.print("¿Desea continuar con la partida actual? (Sí/No): ");
        String respuesta = scanner.nextLine();

        if (respuesta.equalsIgnoreCase("Sí") || respuesta.equalsIgnoreCase("si")) {
            continuarPartida = true;
        }

        return continuarPartida;
    }

    private void obtenerConfiguracionYPremio() {
        Scanner scanner = new Scanner(System.in);
        int configuracionJuego = 0;
        double montoPremio = 0.0;
        TipoPartida configuracionPartida = TipoPartida.CARTON_LLENO;

        System.out.println("Seleccione la configuración de juego:");
        System.out.println("1. Jugar en X");
        System.out.println("2. Cuatro esquinas");
        System.out.println("3. Cartón lleno");
        System.out.println("4. Jugar en Z");

        while (configuracionJuego < 1 || configuracionJuego > 4) {
            System.out.print("Por favor, elija una opción válida: ");
            if (scanner.hasNextInt()) {
                configuracionJuego = scanner.nextInt();
                switch (configuracionJuego) {
                    case 1:
                        configuracionPartida = TipoPartida.JUGAR_EN_X;
                        break;
                    case 2:
                        configuracionPartida = TipoPartida.CUATRO_ESQUINAS;
                        break;
                    case 3:
                        configuracionPartida = TipoPartida.CARTON_LLENO;
                        break;
                    case 4:
                        configuracionPartida = TipoPartida.JUGAR_EN_Z;
                        break;
                    default:
                        System.out.println("Selección no válida. Por favor, elija una opción válida.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un número entre 1 y 4.");
                scanner.next();
            }
        }

        while (montoPremio <= 0) {
            System.out.print("Ingrese el monto del premio: ");
            if (scanner.hasNextDouble()) {
                montoPremio = scanner.nextDouble();
                if (montoPremio <= 0) {
                    System.out.println(
                            "El monto del premio debe ser un valor positivo. Por favor, ingrese un monto válido.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un monto válido.");
                scanner.next();
            }
        }

        // Aquí puedes usar las variables configuracionJuego y montoPremio según sea
        // necesario.
    }

    private void generarWordCloud() {
        System.out.println("Generando Word Cloud...");
    }

    private void generarEstadisticas() {
        System.out.println("Generando estadísticas...");
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
     public static void main(String[] args) {
        InterfazBingo bingoApp = new InterfazBingo();
        bingoApp.mostrarMenu();
        bingoApp.closeConnection(); // Cerrar la conexión a la base de datos al finalizar
    }

}
