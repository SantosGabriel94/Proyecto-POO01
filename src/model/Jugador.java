package model;


public class Jugador {
    private String nombre;
    private String correoElectronico;
    private String cedula;

    public Jugador(String nombre, String correoElectronico, String cedula) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getCedula() {
        return cedula;
    }

}