package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection connection;

    public static void connect(String url, String user, String password) {
        try {
            // Intenta establecer una conexión con la base de datos.
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Conexión exitosa a la base de datos.");
        } catch (SQLException e) {
            // Captura excepciones de SQL y muestra un mensaje de error en la consola.
            System.err.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }
    
    // Otros métodos para operaciones con la base de datos

    public static void close() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Conexión a la base de datos cerrada.");
            }
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión a la base de datos: " + e.getMessage());
        }
    }
}

