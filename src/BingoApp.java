import console.*;
import model.*;

import java.util.ArrayList;

import Database.*;

public class BingoApp {
    private static ArrayList<CartonDeBingo> cartones;
    private static ArrayList<Jugador> jugadores;
    private static ArrayList<PartidaDeBingo> partidas;

    public static void main(String[] args) {
        try {
            // Lógica para inicializar y cargar los datos desde la base de datos.
            //Database.connect("jdbc:mysql://localhost:3306/bingo", "usuario", "contraseña");

            // Invocar la interfaz principal de la aplicación
            InterfazBingo interfaz = new InterfazBingo();
            interfaz.mostrarMenu();

        } catch (Exception e) {
            // Capturar excepciones generales y mostrar un mensaje de error en la consola.
            System.err.println("Error en la aplicación: " + e.getMessage());
        } finally {
            // Asegurarse de cerrar la conexión a la base de datos al finalizar.
            // Guarda los datos en la base de datos
            
            Database.close();
        }
    }
}
