package modelo;

import java.util.ArrayList;
import java.util.List;

public class Rutina {
    private static int contadorId = 1;
    private int id;
    private String nombre;
    private List<EjercicioRutina> ejercicios;

    public Rutina(String nombre) {
        this.id = contadorId++;
        this.nombre = nombre;
        this.ejercicios = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public List<EjercicioRutina> getEjercicios() {
        return ejercicios;
    }

    public void agregarEjercicio(EjercicioRutina e) {
        this.ejercicios.add(e);
    }

}
