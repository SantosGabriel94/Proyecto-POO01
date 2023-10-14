package console;

import java.util.Scanner;

public class InterfazBingo {

    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

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

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    clearConsole();
                    generarCartones();
                    sleep(5000);
                    break;
                case 2:
                    clearConsole();
                    verCarton();
                    sleep(5000);
                    break;
                case 3:
                    clearConsole();
                    registrarJugador();
                    sleep(5000);
                    break;
                case 4:
                    clearConsole();
                    enviarCartonAJugador();
                    sleep(5000);
                    break;
                case 5:
                    clearConsole();
                    iniciarPartida();
                    sleep(5000);
                    break;
                case 6:
                    clearConsole();
                    generarWordCloud();
                    sleep(5000);
                    break;
                case 7:
                    clearConsole();
                    generarEstadisticas();
                    sleep(5000);
                    break;
                case 8:
                    System.out.println("Saliendo del sistema...");
                    sleep(5000);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
                    sleep(2000);
                    break;
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
        // Implementar lógica para generar cartones.
        System.out.println("Generando cartones...");
    }

    private void verCarton() {
        // Implementar lógica para ver cartón.
        System.out.println("Mostrando cartón...");
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

