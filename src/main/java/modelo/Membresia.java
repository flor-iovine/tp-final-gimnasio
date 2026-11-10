package modelo;

import java.time.LocalDate;

public abstract class Membresia {
    protected int id;
    protected double precioBase;
    protected LocalDate fechaInicio;
    protected LocalDate fechaVencimiento;

    public Membresia(int id, double precioBase, LocalDate fechaInicio, LocalDate fechaVencimiento) {
        this.id = id;
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

    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
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

    // METODOS ABSTRACTOS

    public abstract double calcularCosto() ;

    public abstract boolean permitirAcceso() ;
}
