/**
 * Clase Rutina que representa una rutina de entrenamiento compuesta por múltiples ejercicios.
 * Utiliza un contador estático para autoincrementar un identificador único (id) por cada instancia creada:
 * - Constructor: Asigna un ID único, establece el nombre de la rutina e inicializa la lista vacía de ejercicios.
 * - agregarEjercicio: Añade un nuevo elemento de tipo EjercicioRutina a la lista de ejercicios de la rutina.
 * - Métodos getter: Permiten consultar el identificador, el nombre y la lista de ejercicios de la rutina.
 */

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
