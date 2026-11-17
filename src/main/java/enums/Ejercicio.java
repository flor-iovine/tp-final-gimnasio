/**
 * Enumerador Ejercicio que define los diferentes ejercicios físicos
 * disponibles dentro del sistema (SENTADILLA, PRESS_BANCA, PESO_MUERTO, PLANCHA, BURPEES).
 * Incluye un constructor por defecto privado para la inicialización de los valores del enum.
 */

package enums;

public enum Ejercicio {
    SENTADILLA,
    PRESS_BANCA,
    PESO_MUERTO,
    PLANCHA,
    BURPEES;

    private Ejercicio() {}
}