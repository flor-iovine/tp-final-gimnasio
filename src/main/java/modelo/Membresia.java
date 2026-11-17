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
