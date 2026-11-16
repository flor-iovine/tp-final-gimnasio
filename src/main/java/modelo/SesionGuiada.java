package modelo;

import enums.DiaSemana;
import enums.TipoActividadGrupal;
import interfaces.Habilitable;
import interfaces.Promocionable;

import java.time.LocalDate;
import java.time.LocalTime;
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




}
