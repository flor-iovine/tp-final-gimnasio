/**
 * Clase SesionGuiada que representa una sesión o clase grupal programada, implementando las interfaces Promocionable y Habilitable.
 * Utiliza un contador estático para autoincrementar un identificador único (idSesion) y gestiona precios, fechas, cupos y reservas de socios:
 * - Constructores: Asignan un ID único, configuran los parámetros de la sesión (precio, actividad, fechas, día, horario, cupo, entrenador) e inicializan la lista de inscriptos.
 * - estaActivo: Evalúa si la sesión se encuentra vigente comparando la fecha actual con el rango de inicio y vencimiento.
 * - cupoDisponible / getLugaresDisponibles: Controlan si la sesión está activa y si quedan cupos libres respecto al máximo permitido.
 * - agregarReserva / cancelarReserva: Gestionan la inscripción de un socio validando duplicados y cupos, o remueven su reserva.
 * - aplicarDescuento: Aplica un porcentaje de descuento válido (entre 0 y 100) sobre el precio base.
 * - Métodos getter y setter: Permiten consultar y modificar el identificador, precio, actividad, fechas, horario, cupo, entrenador y lista de inscriptos.
 */
package modelo;

import enums.DiaSemana;
import enums.TipoActividadGrupal;
import excepciones.CupoExcedidoException;
import excepciones.RegistroDuplicadoException;
import interfaces.Habilitable;
import interfaces.Promocionable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SesionGuiada implements Promocionable, Habilitable {
    private static int contadorId = 1;
    private int idSesion;
    private double precioBase;
    private TipoActividadGrupal actividad;
    private LocalDate fechaInicio;
    private LocalDate fechaVencimiento;
    private DiaSemana diaSemana;
    private LocalTime horario;
    private int cupoMaximo;
    private List<Socio> inscriptos;
    private Entrenador entrenador;

    public SesionGuiada(double precioBase, TipoActividadGrupal actividad, LocalDate fechaInicio, DiaSemana diaSemana, LocalTime horario, int cupoMaximo, Entrenador entrenador) {
        this.idSesion = contadorId++;
        this.precioBase = precioBase;
        this.actividad = actividad;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaInicio.plusMonths(1);
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.entrenador = entrenador;
        this.inscriptos = new ArrayList<>();
    }

    public SesionGuiada(double precioBase, TipoActividadGrupal actividad, LocalDate fechaInicio, LocalDate fechaVencimiento, DiaSemana diaSemana, LocalTime horario, int cupoMaximo, Entrenador entrenador) {
        this.idSesion = contadorId++;
        this.precioBase = precioBase;
        this.actividad = actividad;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.diaSemana = diaSemana;
        this.horario = horario;
        this.cupoMaximo = cupoMaximo;
        this.entrenador = entrenador;
        this.inscriptos = new ArrayList<>();
    }

    @Override
    public boolean estaActivo() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicio) && !hoy.isAfter(fechaVencimiento);
    }

    public boolean cupoDisponible() {
        return estaActivo() && this.inscriptos.size() < this.cupoMaximo;
    }

    public int getLugaresDisponibles() {
        return this.cupoMaximo - this.inscriptos.size();
    }

    public void agregarReserva(Socio s) throws CupoExcedidoException, RegistroDuplicadoException {
        if (this.inscriptos.contains(s)) {
            throw new RegistroDuplicadoException("El socio ya se encuentra inscripto en " + this.actividad + ".");
        }
        if (!cupoDisponible()) {
            throw new CupoExcedidoException("No hay lugares disponibles o la sesión está vencida.");
        }
        this.inscriptos.add(s);
        s.agregarSesion(this);
    }

    public boolean cancelarReserva(Socio s) {
        if (this.inscriptos.remove(s)) {
            s.removerSesion(this);
            return true;
        }
        return false;
    }

    @Override
    public void aplicarDescuento(double porcentaje) {
        if (porcentaje > 0 && porcentaje <= 100) {
            this.precioBase = this.precioBase - (this.precioBase * (porcentaje / 100.0));
        }
    }

    public int getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(int idSesion) {
        this.idSesion = idSesion;
    }

    public double getPrecioBase() {
        return precioBase;
    }

    public TipoActividadGrupal getActividad() {
        return actividad;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public DiaSemana getDiaSemana() {
        return diaSemana;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public int getCupoMaximo() {
        return cupoMaximo;
    }

    public List<Socio> getInscriptos() {
        return inscriptos;
    }

    public Entrenador getEntrenador() {
        return entrenador;
    }
}
