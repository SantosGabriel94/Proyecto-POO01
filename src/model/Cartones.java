package model;

import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Cartones {
    private static ArrayList<CartonDeBingo> cartones;

    public Cartones() {
        cartones = new ArrayList<>();
    }

    /**
     * Adds a CartonDeBingo to the list of cartones.
     *
     * @param  carton  the CartonDeBingo to be added
     */
    public void añadirCarton(CartonDeBingo carton) {
        cartones.add(carton);
    }

    /**
     * Generates an image of a bingo card.
     *
     * @param  carton   The bingo card object to be used for generating the image.
     */
    public void generarImagenCarton(CartonDeBingo carton) {
        try {
            // Crear una nueva imagen con un tamaño de 300x400.
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
            g.setColor(Color.BLACK);
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
                    int yNum = 30 + fila * ((height - 30) / 5) + 50;
                    g.drawString(numeroStr, xNum, yNum);
                }
            }

            // Guardar la imagen en un archivo con el nombre del identificador único del cartón.
            int identificador = carton.getIdentificadorUnico();
            String nombreArchivo = identificador + ".png";
            String rutaCarpetaResources = "resources/";
            File archivo = new File(rutaCarpetaResources, nombreArchivo);
            ImageIO.write(imagenCarton, "png", archivo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all files in the "resources" folder.
     *
     * @param  None  This function does not have any parameters.
     * @return None  This function does not return anything.
     */
    public void limpiarCarpetaResources() {
        String rutaCarpetaResources = "resources";
        File carpetaResources = new File(rutaCarpetaResources);

        if (carpetaResources.exists() && carpetaResources.isDirectory()) {
            File[] archivos = carpetaResources.listFiles();

            if (archivos != null) {
                for (File archivo : archivos) {
                    if (archivo.isFile()) {
                        archivo.delete();
                    }
                }
            }
        }
    }

    /**
     * Generates the specified number of bingo cards.
     *
     * @param  cantidad  the number of cards to generate
     */
    public void generarCartones(int cantidad) {
        cartones.clear();
        limpiarCarpetaResources();

        for (int i = 0; i < cantidad; i++) {
            CartonDeBingo carton = new CartonDeBingo();
            carton.generarCarton();
            carton.setIdentificadorUnico(i + 1);
            cartones.add(carton);
            generarImagenCarton(carton);
        }
    }

    /**
     * A function to search for a CartonDeBingo object by its identifier.
     *
     * @param  identificador  the identifier of the CartonDeBingo object to be searched
     * @return                the CartonDeBingo object with the given identifier, or null if not found
     */
    public CartonDeBingo buscarCartonPorIdentificador(int identificador) {
        for (CartonDeBingo carton : cartones) {
            if (carton.getIdentificadorUnico() == identificador) {
                return carton;
            }
        }
        return null;
    }

    /**
     * Retrieves the list of available identifiers.
     *
     * @return  the list of available identifiers
     */
    public ArrayList<Integer> obtenerIdentificadoresDisponibles() {
        ArrayList<Integer> identificadoresDisponibles = new ArrayList<>();

        for (CartonDeBingo carton : cartones) {
            if (carton.getJugadorAsignado() != null) {
                identificadoresDisponibles.add(carton.getIdentificadorUnico());
            }
        }

        return identificadoresDisponibles;
    }
}
