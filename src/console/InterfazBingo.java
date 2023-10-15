package console;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.image.BufferedImage;
import model.Jugador;
import model.Jugadores;
import model.PartidaDeBingo;
import model.RegistroDePartidas;
import model.TipoPartida;
import model.CartonDeBingo;
import model.Cartones;

public class InterfazBingo {

    // Cargar los datos de la partida actual de bingo que está almacenada en la base de datos de la aplicación.
    RegistroDePartidas registroPartidas = new RegistroDePartidas();
    PartidaDeBingo partida = new PartidaDeBingo(TipoPartida.CARTON_LLENO, registroPartidas.getPartidas().size() + 1);
    Jugadores jugadores = new Jugadores();
    Cartones cartones = new Cartones();

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;
    
        Jugador luisSoto = new Jugador("Luis Soto", "luis@gmail.com", "103250410");
        Jugador manuelMurillo = new Jugador("Manuel Murillo", "manuel@gmail.com", "31025321");
        jugadores.añadirJugador(luisSoto);
        jugadores.añadirJugador(manuelMurillo);
        cartones.generarCartones(5);
    
        do {
            clearConsole();
            System.out.println("Sistema de Gestión de Bingos");
            System.out.println("1. Generar Cartones");
            System.out.println("2. Ver Cartón");
            System.out.println("3. Registrar Jugador");
            System.out.println("4. Enviar Cartón a Jugador");
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
                        sleep(2000);
                        break;
                    case 2:
                        clearConsole();
                        verCarton();
                        sleep(2000);
                        break;
                    case 3:
                        clearConsole();
                        registrarJugador();
                        sleep(2000);
                        break;
                    case 4:
                        clearConsole();
                        enviarCartonAJugador();
                        sleep(2000);
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
                        sleep(2000);
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
            // Handle the exception
        }
    }

    private void generarCartones() {
        // Solicita la cantidad de cartones al usuario
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingrese la cantidad de cartones a generar: ");
        int cantidad = scanner.nextInt();
        //scanner.next(); // Limpiamos el búfer de entrada.
        //scanner.close();    
        // Implementar lógica para generar cartones.
        System.out.println("Generando cartones...");

        try {
            // Genera los cartones usando el gestor de bingos
            cartones.generarCartones(cantidad);
        } catch (Exception e) {
            System.err.println("Error al generar cartones: " + e.getMessage());
        }
        
    
        // Muestra un mensaje de confirmación
        System.out.println(cantidad + " cartones generados con éxito.");

        ArrayList<Integer> identificadoresDisponibles = cartones.obtenerIdentificadoresDisponibles();
        // Paso 2: Mostrar la lista de identificadores disponibles.
        System.out.println("Identificadores de cartones disponibles:");
        for (int identificador : identificadoresDisponibles) {
            System.out.println(identificador);
        }
    }

    public void verCarton() {
        // Paso 1: Obtener la lista de identificadores de cartones disponibles.
        ArrayList<Integer> identificadoresDisponibles = cartones.obtenerIdentificadoresDisponibles();

        if (identificadoresDisponibles.isEmpty()) {
            System.out.println("No hay cartones disponibles para mostrar. Lista vacia.");
            return;
        }

        // Paso 2: Mostrar la lista de identificadores disponibles.
        System.out.println("Identificadores de cartones disponibles:");
        for (int identificador : identificadoresDisponibles) {
            System.out.println(identificador);
        }

        // Paso 3: Solicitar al usuario que ingrese el identificador del cartón.
        Scanner scanner = new Scanner(System.in);
        int identificadorCarton;
        
        while (true) {
            System.out.print("Ingrese el identificador del cartón que desea ver: ");
            if (scanner.hasNextInt()) {
                identificadorCarton = scanner.nextInt();
                if (identificadoresDisponibles.contains(identificadorCarton)) {
                    break; // Sal del bucle si el identificador es válido.
                } else {
                    System.out.println("Identificador no válido. Intente de nuevo.");
                }
            } else {
                System.out.println("Entrada no válida. Ingrese un número.");
                scanner.next(); // Limpiar el búfer de entrada.
            }
        }

        // Paso 4: Mostrar la imagen y la información del cartón seleccionado.
        verCarton(identificadorCarton);
    }

    public void verCarton(int identificadorCarton) {


        // Obtén la URL de la imagen del cartón (asumiendo que la carpeta "resources" está en la raíz del proyecto).
        String rutaImagenCarton = "resources/" + identificadorCarton + ".png";
        
        // Carga y muestra la imagen utilizando ImageIO (asegúrate de manejar excepciones).
        try {
            BufferedImage imagenCarton = ImageIO.read(new File(rutaImagenCarton));
            if (imagenCarton != null) {
                // Muestra la imagen.
                JFrame frame = new JFrame("Cartón de Bingo");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(new JLabel(new ImageIcon(imagenCarton)));
                frame.pack();
                frame.setVisible(true);
                
                // Verifica si el cartón tiene un jugador asignado y muestra su información.
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
        // Implementar lógica para registrar jugadores.
        System.out.println("Registrando jugador...");
    }

    private void enviarCartonAJugador() {
        // Implementar lógica para enviar cartón a jugador.
        System.out.println("Enviando cartón a jugador...");
    }

    private void iniciarPartida() {
        // Implementar lógica para iniciar partida.
        System.out.println("Iniciando partida...");
    }

    private void generarWordCloud() {
        // Implementar lógica para generar Word Cloud.
        System.out.println("Generando Word Cloud...");
    }

    private void generarEstadisticas() {
        // Implementar lógica para generar estadísticas.
        System.out.println("Generando estadísticas...");
    }
}

