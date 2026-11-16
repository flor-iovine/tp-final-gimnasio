package modelo;


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
