package model;

import java.util.ArrayList;
import java.util.List;

public class Jugador {
    private String nombre;
    private String correoElectronico;
    private String cedula;
    private List<CartonDeBingo> cartones;

    public Jugador(String nombre, String correoElectronico, String cedula) {
        this.nombre = nombre;
        this.correoElectronico = correoElectronico;
        this.cedula = cedula;
        this.cartones = new ArrayList<>();
    }

    public void asignarCarton(CartonDeBingo carton) {
        cartones.add(carton);
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

    public List<CartonDeBingo> getCartones() {
        return cartones;
    }
}