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
