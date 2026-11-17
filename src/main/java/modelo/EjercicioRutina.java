/**
 * Clase EjercicioRutina que representa la asociación de un ejercicio específico
 * con su respectiva configuración de entrenamiento (series y repeticiones dentro de una rutina).
 * - Constructor: Inicializa el ejercicio base, la cantidad de series y las repeticiones configuradas.
 * - Métodos getter: Permiten consultar el ejercicio, las series y las repeticiones.
 */
package modelo;

import enums.Ejercicio;

public class EjercicioRutina {
    private Ejercicio ejercicio;
    private int series;
    private int repeticiones;

    public EjercicioRutina(Ejercicio ejercicio, int series, int repeticiones) {
        this.ejercicio = ejercicio;
        this.series = series;
        this.repeticiones = repeticiones;
    }

    public Ejercicio getEjercicio() {
        return ejercicio;
    }

    public int getSeries() {
        return series;
    }

    public int getRepeticiones() {
        return repeticiones;
    }
}
