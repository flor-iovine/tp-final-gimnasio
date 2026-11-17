/**
 * Interfaz Promocionable que define el contrato para aquellos elementos o entidades
 * a los que se les puede aplicar un descuento comercial.
 * Define el siguiente metodo:
 * - aplicarDescuento: Aplica un porcentaje de descuento sobre el elemento.
 */

package interfaces;

public interface Promocionable {
    void aplicarDescuento(double porcentaje);
}
