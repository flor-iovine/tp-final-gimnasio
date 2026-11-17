package modelo;


/**
 * Clase PaseLibre que representa un tipo de membresía con acceso libre y un descuento aplicado sobre el precio base.
 * Extiende de Membresia y sobrescribe métodos de cálculo y representación:
 * - Constructor: Inicializa la membresía con precio, fecha de inicio y fecha de vencimiento.
 * - toString: Retorna una representación en texto del estado del pase libre, incluyendo su precio base y los datos heredados.
 * - calcularCosto: Aplica un descuento fijo (15%) sobre el precio base de la membresía.
 */
import java.time.LocalDate;

public class PaseLibre extends Membresia{
    private static final double DESCUENTO_PASE_LIBRE = 0.15;


    public PaseLibre(double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        super(precioBase, fechaInicio, fechaVencimiento);
    }

    @Override
    public String toString() {
        return "PaseLibre{" +
                "precioBase=" + precioBase +
                "} " + super.toString();
    }

    @Override
    public double calcularCosto() {
        return this.precioBase * (1.0 - DESCUENTO_PASE_LIBRE);
    }

}
