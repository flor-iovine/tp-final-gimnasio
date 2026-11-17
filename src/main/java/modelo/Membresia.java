/**
 * Clase abstracta Membresia que representa una membresía del gimnasio, implementando las interfaces Promocionable y Habilitable.
 * Utiliza un contador estático para autoincrementar un identificador único (id) y gestiona precios, fechas de vigencia y descuentos:
 * - Constructor: Asigna un ID único y configura el precio base, la fecha de inicio y la fecha de vencimiento.
 * - estaActivo: Evalúa si la membresía se encuentra vigente comparando la fecha actual con el rango de inicio y vencimiento.
 * - aplicarDescuento: Aplica un porcentaje de descuento válido (entre 0 y 100) sobre el precio base.
 * - calcularCosto: Metodo abstracto que debe ser implementado por las subclases para calcular el costo final de la membresía.
 * - Métodos getter y setter: Permiten consultar y modificar el identificador, el precio base, las fechas y representar el objeto en texto (toString).
 */
package modelo;

import interfaces.Habilitable;
import interfaces.Promocionable;

import java.time.LocalDate;

public abstract class Membresia implements Promocionable, Habilitable {
    private static int contadorId = 1;
    private int id;
    protected double precioBase;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;

    public Membresia(double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.id = contadorId ++;
        this.precioBase = precioBase;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    @Override
    public String toString() {
        return "Membresia{" +
                "id=" + id +
                ", precioBase=" + precioBase +
                ", fechaInicio=" + fechaInicio +
                ", fechaVencimiento=" + fechaVencimiento +
                '}';
    }

    @Override
    public boolean estaActivo() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaVencimiento);
    }


    @Override
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
            this.precioBase -= this.precioBase * (porcentaje / 100.0);
        } else {
            System.out.println("Porcentaje inválido. No se aplicará ningún descuento.");
        }
    }

    public abstract double calcularCosto() ;

}
