package model;

import java.util.Properties;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;
import java.io.IOException;
import java.util.ArrayList;


public class CorreoElectronico {
    private String usuario;
    private String clave = "hyzw rrzx dekf lbeq";
    private String servidor = "smtp.gmail.com";
    private String puerto = "587";
    private Properties propiedades;

    public CorreoElectronico() {
        propiedades = new Properties();
        propiedades.put("mail.smtp.host", servidor);
        propiedades.put("mail.smtp.port", puerto);
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        usuario = "proyecto1pooiis2023@gmail.com";
    }

    /**
     * Opens a session for sending email using the given properties, username, and password.
     *
     * @return          the opened session for sending email
     */
    private Session abrirSesion() {
        Session sesion = Session.getInstance(propiedades,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(usuario, clave);
                    }
                }
        );
        return sesion;
    }

    /**
     * Sends an email with the given recipient, email subject, and email body.
     *
     * @param  destinatario      the recipient of the email
     * @param  tituloCorreo      the subject of the email
     * @param  cuerpo            the body of the email
     * @return                   void
     */
    public void enviarCorreo(String destinatario, String tituloCorreo, String cuerpo) {
        Session sesion = abrirSesion();

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );
            message.setSubject(tituloCorreo);
            message.setText(cuerpo);

            // Enviar correo
            Transport.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends an email with bingo cards attached.
     *
     * @param  destinatario     the recipient of the email
     * @param  tituloCorreo     the subject of the email
     * @param  cuerpo           the body of the email
     * @param  cartones         the list of bingo cards to be attached
     */
    public void enviarCorreoConCartones(String destinatario, String tituloCorreo, String cuerpo, ArrayList<CartonDeBingo> cartones) {
        Session sesion = abrirSesion();
        // Directorio temporal para guardar las imágenes de cartones
        String directorioTemporal = "temp_images";

        try {
            Message message = new MimeMessage(sesion);
            message.setFrom(new InternetAddress(usuario));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(destinatario)
            );
            message.setSubject(tituloCorreo);

            // Cuerpo del mensaje
            MimeBodyPart cuerpoMensaje = new MimeBodyPart();
            cuerpoMensaje.setText(cuerpo);

            // Parte de archivos adjuntos
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(cuerpoMensaje);

            // Crear el directorio temporal si no existe
            new File(directorioTemporal).mkdirs();

            // Adjuntar archivos de cartones
            if (cartones != null) {
                for (CartonDeBingo carton : cartones) {
                    String rutaImagenCarton = "resources/" + carton.getIdentificadorUnico() + ".png";
                    BufferedImage imagenCarton = ImageIO.read(new File(rutaImagenCarton));
                    String nombreArchivo = carton.getIdentificadorUnico() + ".png";

                    // Guardar la imagen en el directorio temporal
                    ImageIO.write(imagenCarton, "png", new File(directorioTemporal, nombreArchivo));

                    // Adjuntar el archivo al correo
                    MimeBodyPart archivoAdjuntoParte = new MimeBodyPart();
                    // Read the file into a byte array
                    byte[] fileData = Files.readAllBytes(new File(directorioTemporal, nombreArchivo).toPath());
                    
                    // Create a new ByteArrayDataSource object with the byte array
                    DataSource fuente = new ByteArrayDataSource(fileData, rutaImagenCarton);
                    
                    // Rest of the code...
                    //DataSource fuente = new ByteArrayDataSource(new File(directorioTemporal, nombreArchivo));
                    archivoAdjuntoParte.setDataHandler(new DataHandler(fuente));
                    archivoAdjuntoParte.setFileName(nombreArchivo);
                    multipart.addBodyPart(archivoAdjuntoParte);
                }
            }

            message.setContent(multipart);

            // Enviar correo
            Transport.send(message);
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        } finally {
            // Eliminar los archivos temporales después de enviar el correo
            eliminarArchivosTemporales(directorioTemporal);
        }
    }

    /**
     * Deletes all files in the specified temporary directory.
     *
     * @param  directorioTemporal  the path to the temporary directory
     * @return                    void
     */
    private void eliminarArchivosTemporales(String directorioTemporal) {
        File directorio = new File(directorioTemporal);
        File[] archivos = directorio.listFiles();
        if (archivos != null) {
            for (File archivo : archivos) {
                archivo.delete();
            }
        }
        directorio.delete();
    }
}
