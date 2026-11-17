/**
 * Clase PaseTresVeces que representa un tipo de membresía limitada a un máximo de tres asistencias semanales.
 * Extiende de Membresia e implementa el control de uso y validación de estado:
 * - Constructor: Inicializa la membresía con precio, fechas y establece las asistencias usadas en cero.
 * - toString: Retorna una representación en texto del estado del pase, incluyendo el límite y asistencias usadas.
 * - calcularCosto: Retorna el precio base de la membresía.
 * - estaActivo: Evalúa si el pase está activo verificando la vigencia general y que no se haya superado el límite semanal.
 * - registrarAsistencia: Valida que la membresía esté activa e incrementa el contador de asistencias usadas, o lanza una excepción si no es posible.
 * - Métodos getter: Permiten consultar la cantidad de asistencias usadas.
 */
package modelo;

import excepciones.MembresiaInactivaException;

import java.time.LocalDate;

public class PaseTresVeces extends Membresia{
    private static final int LIMITE_SEMANAL = 3;
    private int asistenciasUsadas;

    public PaseTresVeces( double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        super(precioBase, fechaInicio, fechaVencimiento);
        this.asistenciasUsadas = 0;
    }

    public int getAsistenciasUsadas() {
        return asistenciasUsadas;
    }


    @Override
    public String toString() {
        return "PaseTresVeces{" +
                "asistenciasSemanalesTotales=" + LIMITE_SEMANAL +
                ", asistenciasUsadas=" + asistenciasUsadas +
                "} " + super.toString();
    }

    @Override
    public double calcularCosto() {
        return this.precioBase;
    }

    @Override
    public boolean estaActivo() {
        return super.estaActivo() && this.asistenciasUsadas < LIMITE_SEMANAL;
    }


    public void registrarAsistencia() throws MembresiaInactivaException {
        if (!estaActivo()) {
            throw new MembresiaInactivaException("Pase vencido o límite semanal de 3 asistencias alcanzado.");
        }
        this.asistenciasUsadas++;
    }
    
}
