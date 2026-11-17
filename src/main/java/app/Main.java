package app;

import enums.DiaSemana;
import enums.Ejercicio;
import enums.EspecialidadEntrenador;
import enums.TipoActividadGrupal;
import gestor.GestorMembresias;
import gestor.GestorPersonas;
import gestor.GestorSesiones;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import modelo.Administrador;
import modelo.EjercicioRutina;
import modelo.Empleado;
import modelo.Entrenador;
import modelo.Membresia;
import modelo.PaseLibre;
import modelo.PaseTresVeces;
import modelo.Persona;
import modelo.Rutina;
import modelo.SesionGuiada;
import modelo.Socio;


public class Main {
    private static final GestorPersonas gestorPersonas = new GestorPersonas();
    private static final GestorMembresias gestorMembresias = new GestorMembresias();
    private static final GestorSesiones gestorSesiones = new GestorSesiones();
    private static final Scanner scanner;

    public Main() {
    }

    public static void main(String[] args) {
        cargarDatosDePrueba();
        boolean sistemaActivo = true;

        while(sistemaActivo) {
            limpiarPantalla();
            System.out.println("=========================================");
            System.out.println("        FITGYM - SISTEMA DE GESTIÓN      ");
            System.out.println("=========================================");
            System.out.println("1. Ingreso Staff (Administrador / Entrenador)");
            System.out.println("2. Ingreso Socio (con DNI)");
            System.out.println("0. Salir del Sistema");
            System.out.print("Seleccione una opción: ");
            int opcion = leerEntero();
            switch (opcion) {
                case 0:
                    sistemaActivo = false;
                    System.out.println("Cerrando sistema...");
                    break;
                case 1:
                    loginEmpleado();
                    break;
                case 2:
                    accesoSocioPorDni();
                    break;
                default:
                    System.out.println("[!] Opción inválida.");
                    pausar();
            }
        }

        scanner.close();
    }

    private static void loginEmpleado() {
        limpiarPantalla();
        System.out.println("--- INGRESO STAFF ---");
        System.out.print("Usuario: ");
        String user = scanner.nextLine().trim();
        System.out.print("Contraseña: ");
        String pass = scanner.nextLine().trim();
        Empleado empleadoLogueado = null;

        for(Persona p : gestorPersonas.obtenerTodos()) {
            if (p instanceof Empleado emp) {
                if (emp.login(user, pass)) {
                    empleadoLogueado = emp;
                    break;
                }
            }
        }

        if (empleadoLogueado == null) {
            System.out.println("[!] Usuario o contraseña incorrectos.");
            pausar();
        } else {
            if (empleadoLogueado instanceof Administrador) {
                Administrador admin = (Administrador)empleadoLogueado;
                menuAdministrador(admin);
            } else if (empleadoLogueado instanceof Entrenador) {
                Entrenador ent = (Entrenador)empleadoLogueado;
                menuEntrenador(ent);
            }

        }
    }

    private static void accesoSocioPorDni() {
        limpiarPantalla();
        System.out.println("--- INGRESO SOCIO ---");
        System.out.print("Ingrese su DNI: ");
        String dni = leerSoloDigitos();
        Persona p = gestorPersonas.buscarPorDni(dni);
        if (p instanceof Socio socio) {
            menuSocio(socio);
        } else {
            System.out.println("[!] No existe un socio registrado con el DNI " + dni);
            pausar();
        }
    }

    private static void menuAdministrador(Administrador admin) {
        int opcion;
        do {
            limpiarPantalla();
            System.out.println("--- MENÚ ADMINISTRADOR (" + admin.getNombre() + ") ---");
            System.out.println("1. Dar de alta Socio o Entrenador");
            System.out.println("2. Dar de baja a un Entrenador");
            System.out.println("3. Asignar Pase de Sala (Libre o 3 Veces) a Socio");
            System.out.println("4. Dar de baja Pase de Sala de un Socio");
            System.out.println("5. Aplicar Descuento Promocional (Pase o Sesión)");
            System.out.println("6. Crear Sesión Guiada y Asignar Entrenador");
            System.out.println("7. Listar Todos los Socios y su Estado");
            System.out.println("8. Listar Todos los Entrenadores y sus Datos");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 0:
                    System.out.println("Cerrando sesión...");
                    break;
                case 1:
                    altaPersona();
                    break;
                case 2:
                    bajaEntrenador();
                    break;
                case 3:
                    asignarPaseSala();
                    break;
                case 4:
                    bajaPaseSalaSocio();
                    break;
                case 5:
                    aplicarDescuentoPromocionable();
                    break;
                case 6:
                    crearSesionGuiada();
                    break;
                case 7:
                    listarSocios();
                    break;
                case 8:
                    listarEntrenadores();
                    break;
                default:
                    System.out.println("[!] Opción inválida.");
                    pausar();
            }
        } while(opcion != 0);

    }

    private static void altaPersona() {
        System.out.println("\n¿Qué perfil desea crear? 1. Socio | 2. Entrenador");
        int tipo = leerEntero();
        if (tipo != 1 && tipo != 2) {
            System.out.println("[!] Tipo inválido.");
            pausar();
        } else {
            System.out.print("DNI (solo números): ");
            String dni = leerSoloDigitos();
            if (gestorPersonas.buscarPorDni(dni) != null) {
                System.out.println("[ERROR] Ya existe una persona registrada en el sistema con el DNI " + dni + ".");
                pausar();
            } else {
                System.out.print("Nombre completo: ");
                String nombre = scanner.nextLine().trim();
                System.out.print("Email: ");
                String email = scanner.nextLine().trim();
                System.out.print("Teléfono (solo números): ");
                String tel = leerSoloDigitos();

                if (tipo == 1) {
                    Socio nuevoSocio = new Socio(nombre, dni, email, tel);
                    gestorPersonas.agregarPersona(nuevoSocio);
                    System.out.println("[OK] Socio creado (N° Socio: " + nuevoSocio.getNumeroSocio() + ").");
                } else {
                    System.out.print("Usuario de acceso: ");
                    String user = scanner.nextLine().trim();
                    System.out.print("Contraseña: ");
                    String pass = scanner.nextLine().trim();
                    System.out.println("Especialidad (1. MUSCULACION | 2. PILATES | 3. CROSSFIT): ");
                    int espOp = leerEntero();
                    EspecialidadEntrenador var10000;
                    switch (espOp) {
                        case 2 -> var10000 = EspecialidadEntrenador.PILATES;
                        case 3 -> var10000 = EspecialidadEntrenador.CROSSFIT;
                        default -> var10000 = EspecialidadEntrenador.MUSCULACION;
                    }

                    EspecialidadEntrenador esp = var10000;
                    Entrenador nuevoEnt = new Entrenador(nombre, dni, email, tel, user, pass, esp);
                    gestorPersonas.agregarPersona(nuevoEnt);
                    System.out.println("[OK] Entrenador creado correctamente.");
                }

                pausar();
            }
        }
    }

    private static void bajaEntrenador() {
        listarEntrenadoresSinPausa();
        System.out.print("\nIngrese el DNI del entrenador a dar de baja: ");
        String dni = leerSoloDigitos();
        Persona p = gestorPersonas.buscarPorDni(dni);
        if (!(p instanceof Entrenador entrenador)) {
            System.out.println("[!] No se encontró un entrenador con ese DNI.");
            pausar();
        } else {
            ArrayList sesionesAfectadas = new ArrayList();

            for(SesionGuiada s : gestorSesiones.obtenerTodos()) {
                if (s.getEntrenador().getId() == entrenador.getId()) {
                    sesionesAfectadas.add(s);
                }
            }

            int totalSociosDesinscriptos = 0;

            for(SesionGuiada s : sesionesAfectadas) {
                for(Socio socio : new ArrayList(s.getInscriptos())) {
                    s.cancelarReserva(socio);
                    ++totalSociosDesinscriptos;
                }

                gestorSesiones.eliminar(s);
            }

            gestorPersonas.eliminar(entrenador);
            if (!sesionesAfectadas.isEmpty()) {
                PrintStream var10000 = System.out;
                int var10001 = sesionesAfectadas.size();
                var10000.println("[AVISO] Diste de baja a un entrenador con " + var10001 + " sesión(es) guiada(s). Se cancelaron dichas sesiones y " + totalSociosDesinscriptos + " socio(s) fueron dados de baja automáticamente de ellas.");
            } else {
                System.out.println("[OK] Entrenador " + entrenador.getNombre() + " dado de baja.");
            }

            pausar();
        }
    }

    private static void asignarPaseSala() {
        System.out.print("\nDNI del socio: ");
        String dni = leerSoloDigitos();
        Persona p = gestorPersonas.buscarPorDni(dni);
        if (p instanceof Socio socio) {
            Membresia paseActual = socio.getPaseSala();
            int tipoElegido;
            if (paseActual == null) {
                System.out.println("Seleccione pase: 1. Pase Libre (15% desc. base) | 2. Pase 3 Veces por Semana");
                tipoElegido = leerEntero();
                if (tipoElegido != 1 && tipoElegido != 2) {
                    System.out.println("[!] Opción inválida.");
                    pausar();
                    return;
                }
            } else if (paseActual instanceof PaseLibre) {
                System.out.println("[AVISO] El socio " + socio.getNombre() + " ya tiene un Pase Libre activo.");
                System.out.print("¿Desea cambiarlo por un Pase 3 Veces por Semana? (1. Sí | 2. No): ");
                if (leerEntero() != 1) {
                    System.out.println("Operación cancelada.");
                    pausar();
                    return;
                }

                tipoElegido = 2;
            } else {
                System.out.println("[AVISO] El socio " + socio.getNombre() + " ya tiene un Pase 3 Veces por Semana activo.");
                System.out.print("¿Desea cambiarlo por un Pase Libre (15% desc. base)? (1. Sí | 2. No): ");
                if (leerEntero() != 1) {
                    System.out.println("Operación cancelada.");
                    pausar();
                    return;
                }

                tipoElegido = 1;
            }

            System.out.print("Precio base: ");
            double precio = leerDouble();
            if (paseActual != null) {
                gestorMembresias.eliminar(paseActual);
            }

            Membresia nuevoPase = (Membresia)(tipoElegido == 1 ? new PaseLibre(precio, LocalDate.now(), LocalDate.now().plusMonths(1L)) : new PaseTresVeces(precio, LocalDate.now(), LocalDate.now().plusMonths(1L)));
            socio.setPaseSala(nuevoPase);
            gestorMembresias.agregar(nuevoPase);
            System.out.println("[OK] Pase asignado. Costo final: $" + nuevoPase.calcularCosto());
            pausar();
        } else {
            System.out.println("[!] Socio no encontrado.");
            pausar();
        }
    }

    private static void bajaPaseSalaSocio() {
        System.out.print("\nDNI del socio a dar de baja de su pase de sala: ");
        String dni = leerSoloDigitos();
        Persona p = gestorPersonas.buscarPorDni(dni);
        if (p instanceof Socio socio) {
            if (socio.getPaseSala() == null) {
                System.out.println("[!] El socio " + socio.getNombre() + " no tiene ningún pase de sala asignado.");
            } else {
                String tipoPase = socio.getPaseSala().getClass().getSimpleName();
                gestorMembresias.eliminar(socio.getPaseSala());
                socio.setPaseSala((Membresia)null);
                System.out.println("[OK] Se dio de baja el " + tipoPase + " de " + socio.getNombre() + ". Estado actual: " + (socio.estaActivo() ? "ACTIVO (por clases)" : "INACTIVO"));
            }

            pausar();
        } else {
            System.out.println("[!] Socio no encontrado.");
            pausar();
        }
    }

    private static void aplicarDescuentoPromocionable() {
        System.out.println("\nAplicar promoción a: 1. Membresía (Pase) | 2. Sesión Guiada");
        int tipo = leerEntero();
        if (tipo == 1) {
            List<Membresia> lista = gestorMembresias.obtenerTodos();
            if (lista.isEmpty()) {
                System.out.println("[!] No hay membresías cargadas.");
                pausar();
                return;
            }

            System.out.println("\n--- MEMBRESÍAS REGISTRADAS ---");

            for(Membresia m : lista) {
                String duenio = buscarDuenioMembresia(m);
                PrintStream var10000 = System.out;
                int var10001 = m.getId();
                var10000.println("(ID: " + var10001 + ") " + m.getClass().getSimpleName() + " | Socio: " + duenio + " | Costo actual: $" + m.calcularCosto());
            }

            System.out.print("Ingrese el ID de la membresía: ");
            Membresia elegida = gestorMembresias.buscarPorId(leerEntero());
            if (elegida == null) {
                System.out.println("[!] Membresía no encontrada.");
                pausar();
                return;
            }

            System.out.print("Porcentaje de descuento: ");
            double desc = leerDouble();
            elegida.aplicarDescuento(desc);
            System.out.println("[OK] Descuento aplicado. Nuevo costo: $" + elegida.calcularCosto());
        } else if (tipo == 2) {
            List<SesionGuiada> sesiones = gestorSesiones.obtenerTodos();
            if (sesiones.isEmpty()) {
                System.out.println("[!] No hay sesiones guiadas.");
                pausar();
                return;
            }

            System.out.println("\n--- SESIONES GUIADAS ---");

            for(SesionGuiada s : sesiones) {
                PrintStream var13 = System.out;
                int var14 = s.getIdSesion();
                var13.println("(ID: " + var14 + ") " + String.valueOf(s.getActividad()) + " | Día: " + String.valueOf(s.getDiaSemana()) + " | Horario: " + String.valueOf(s.getHorario()) + " | Precio: $" + s.getPrecioBase());
            }

            System.out.print("Ingrese el ID de la sesión: ");
            SesionGuiada s = gestorSesiones.buscarPorId(leerEntero());
            if (s != null) {
                System.out.print("Porcentaje de descuento: ");
                double desc = leerDouble();
                s.aplicarDescuento(desc);
                System.out.println("[OK] Nuevo precio de sesión: $" + s.getPrecioBase());
            } else {
                System.out.println("[!] Sesión no encontrada.");
            }
        }

        pausar();
    }

    private static String buscarDuenioMembresia(Membresia m) {
        for(Socio s : gestorPersonas.obtenerSocios()) {
            if (s.getPaseSala() != null && s.getPaseSala().getId() == m.getId()) {
                return s.getNombre();
            }
        }

        return "Sin asignar";
    }

    private static void crearSesionGuiada() {
        System.out.println("\nActividad (1. YOGA | 2. PILATES | 3. CROSSFIT): ");
        int actOp = leerEntero();
        TipoActividadGrupal var10000;
        switch (actOp) {
            case 1 -> var10000 = TipoActividadGrupal.YOGA;
            case 2 -> var10000 = TipoActividadGrupal.PILATES;
            case 3 -> var10000 = TipoActividadGrupal.CROSSFIT;
            default -> var10000 = null;
        }

        TipoActividadGrupal act = var10000;
        if (act == null) {
            System.out.println("[!] Actividad inválida.");
            pausar();
        } else {
            DiaSemana dia;
            for(dia = null; dia == null; dia = var13) {
                System.out.println("\nSeleccione el día de la clase:");
                System.out.println("1. Lunes | 2. Martes | 3. Miércoles | 4. Jueves | 5. Viernes | 6. Sábado");
                System.out.print("Día: ");
                switch (leerEntero()) {
                    case 1:
                        var13 = DiaSemana.LUNES;
                        break;
                    case 2:
                        var13 = DiaSemana.MARTES;
                        break;
                    case 3:
                        var13 = DiaSemana.MIERCOLES;
                        break;
                    case 4:
                        var13 = DiaSemana.JUEVES;
                        break;
                    case 5:
                        var13 = DiaSemana.VIERNES;
                        break;
                    case 6:
                        var13 = DiaSemana.SABADO;
                        break;
                    default:
                        System.out.println("[!] Opción inválida. Elija del 1 al 6.");
                        var13 = null;
                }
            }

            int hora = -1;

            while(hora < 8 || hora > 19) {
                System.out.print("Ingrese la hora (el gimnasio abre de 8:00 a 20:00, último turno a las 19 hs): ");
                hora = leerEntero();
                if (hora < 8 || hora > 19) {
                    System.out.println("[!] Horario inválido. El gimnasio abre de 8:00 a 20:00 (se admiten clases hasta las 19 hs).");
                }
            }

            int minutos = -1;

            while(minutos < 0 || minutos > 59) {
                System.out.print("Ingrese los minutos (0 a 59): ");
                minutos = leerEntero();
                if (minutos < 0 || minutos > 59) {
                    System.out.println("[!] Minutos inválidos. Deben estar entre 0 y 59.");
                }
            }

            LocalTime horario = LocalTime.of(hora, minutos);
            System.out.print("Precio de la clase: ");
            double precio = leerDouble();
            System.out.print("Cupo máximo: ");
            int cupo = leerEntero();
            System.out.print("DNI del Entrenador a cargo: ");
            String dniEnt = leerSoloDigitos();
            Persona p = gestorPersonas.buscarPorDni(dniEnt);
            if (p instanceof Entrenador) {
                Entrenador ent = (Entrenador)p;
                SesionGuiada nuevaSesion = new SesionGuiada(precio, act, LocalDate.now(), dia, horario, cupo, ent);
                gestorSesiones.agregar(nuevaSesion);
                PrintStream var14 = System.out;
                String var10001 = String.valueOf(act);
                var14.println("[OK] Sesión de " + var10001 + " creada | Entrenador: " + ent.getNombre() + " | Cupo máximo: " + cupo + " lugares | Día: " + String.valueOf(dia) + " " + String.valueOf(horario) + " | Vigencia: " + String.valueOf(nuevaSesion.getFechaInicio()) + " al " + String.valueOf(nuevaSesion.getFechaVencimiento()) + ".");
                pausar();
            } else {
                System.out.println("[!] Entrenador no encontrado.");
                pausar();
            }
        }
    }

    private static void listarSocios() {
        System.out.println("\n--- LISTADO DE SOCIOS ---");

        for(Socio s : gestorPersonas.obtenerSocios()) {
            String estado = s.estaActivo() ? "ACTIVO" : "INACTIVO";
            String detallePase = s.getPaseSala() != null ? s.getPaseSala().getClass().getSimpleName() : "Sin pase sala";
            int cantClases = s.getSesionesInscriptas().size();
            PrintStream var10000 = System.out;
            int var10001 = s.getNumeroSocio();
            var10000.println("N° " + var10001 + " | " + s.getNombre() + " | DNI: " + s.getDni() + " | Estado: [" + estado + "] (" + detallePase + " | Clases: " + cantClases + ")");
        }

        pausar();
    }

    private static void listarEntrenadores() {
        listarEntrenadoresSinPausa();
        pausar();
    }

    private static void listarEntrenadoresSinPausa() {
        System.out.println("\n--- LISTADO DE ENTRENADORES ---");
        List<Entrenador> entrenadores = gestorPersonas.obtenerEntrenadores();
        if (entrenadores.isEmpty()) {
            System.out.println("No hay entrenadores registrados.");
        } else {
            for(Entrenador e : entrenadores) {
                PrintStream var10000 = System.out;
                String var10001 = e.getNombre();
                var10000.println("Nombre: " + var10001 + " | DNI: " + e.getDni() + " | Email: " + e.getEmail() + " | Tel: " + e.getTelefono() + " | Usuario: " + e.getUsuario() + " | Especialidad: " + String.valueOf(e.getEspecialidad()));
            }

        }
    }

    private static void menuEntrenador(Entrenador entrenador) {
        int opcion;
        do {
            limpiarPantalla();
            System.out.println("--- MENÚ ENTRENADOR (" + entrenador.getNombre() + ") ---");
            System.out.println("1. Ver Mis Sesiones Guiadas, Fechas, Horarios y Cupos");
            System.out.println("2. Crear y Asignar Rutina a un Socio");
            System.out.println("0. Cerrar Sesión");
            System.out.print("Opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 0:
                    System.out.println("Cerrando sesión...");
                    break;
                case 1:
                    verSesionesEntrenador(entrenador);
                    break;
                case 2:
                    armarRutinaParaSocio();
                    break;
                default:
                    System.out.println("[!] Opción inválida.");
                    pausar();
            }
        } while(opcion != 0);

    }

    private static void verSesionesEntrenador(Entrenador entrenador) {
        System.out.println("\n--- MIS SESIONES GUIADAS ---");
        boolean tiene = false;

        for(SesionGuiada s : gestorSesiones.obtenerTodos()) {
            if (s.getEntrenador().getId() == entrenador.getId()) {
                tiene = true;
                String estadoClase = s.estaActivo() ? "VIGENTE" : "VENCIDA";
                PrintStream var10000 = System.out;
                int var10001 = s.getIdSesion();
                var10000.println("(ID: " + var10001 + ") " + String.valueOf(s.getActividad()) + " [" + estadoClase + "] | Día: " + String.valueOf(s.getDiaSemana()) + " | Horario: " + String.valueOf(s.getHorario()) + " | Vigencia: " + String.valueOf(s.getFechaInicio()) + " al " + String.valueOf(s.getFechaVencimiento()) + " | Lugares disponibles: " + s.getLugaresDisponibles() + "/" + s.getCupoMaximo() + " (Inscriptos: " + s.getInscriptos().size() + ")");
            }
        }

        if (!tiene) {
            System.out.println("No tenés sesiones guiadas asignadas.");
        }

        pausar();
    }

    private static void armarRutinaParaSocio() {
        System.out.print("\nDNI del socio: ");
        Persona p = gestorPersonas.buscarPorDni(leerSoloDigitos());
        if (p instanceof Socio socio) {
            if (!socio.tienePaseSalaActivo()) {
                System.out.println("[ERROR] No se puede asignar una rutina: el socio " + socio.getNombre() + " no posee un Pase Libre o Pase 3 Veces activo.");
                pausar();
            } else {
                System.out.print("Nombre/Objetivo de la rutina: ");
                String nombre = scanner.nextLine().trim();
                Rutina rutina = new Rutina(nombre);
                System.out.print("¿Cuántos ejercicios desea agregar?: ");
                int cant = leerEntero();

                for(int i = 0; i < cant; ++i) {
                    System.out.println("Ejercicio #" + (i + 1) + " (1. SENTADILLA | 2. PRESS_BANCA | 3. PESO_MUERTO | 4. PLANCHA | 5. BURPEES): ");
                    int op = leerEntero();
                    Ejercicio var10000;
                    switch (op) {
                        case 2 -> var10000 = Ejercicio.PRESS_BANCA;
                        case 3 -> var10000 = Ejercicio.PESO_MUERTO;
                        case 4 -> var10000 = Ejercicio.PLANCHA;
                        case 5 -> var10000 = Ejercicio.BURPEES;
                        default -> var10000 = Ejercicio.SENTADILLA;
                    }

                    Ejercicio ej = var10000;
                    System.out.print("Series: ");
                    int series = leerEntero();
                    System.out.print("Repeticiones: ");
                    int reps = leerEntero();
                    rutina.agregarEjercicio(new EjercicioRutina(ej, series, reps));
                }

                socio.setRutinaPersonal(rutina);
                PrintStream var10 = System.out;
                String var10001 = rutina.getNombre();
                var10.println("[OK] Rutina '" + var10001 + "' asignada a " + socio.getNombre() + ".");
                pausar();
            }
        } else {
            System.out.println("[!] Socio no encontrado.");
            pausar();
        }
    }

    private static void menuSocio(Socio socio) {
        int opcion;
        do {
            limpiarPantalla();
            PrintStream var10000 = System.out;
            String var10001 = socio.getNombre();
            var10000.println("--- MENÚ SOCIO (" + var10001 + " | N° " + socio.getNumeroSocio() + ") ---");
            System.out.println("1. Pasar por Molinete (Validar acceso y registrar asistencia)");
            System.out.println("2. Inscribirse a una Sesión Guiada");
            System.out.println("3. Cancelar Inscripción a Sesión Guiada");
            System.out.println("4. Consultar Mi Estado, Clases y Rutina");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            opcion = leerEntero();
            switch (opcion) {
                case 0:
                    System.out.println("Saliendo...");
                    break;
                case 1:
                    validarIngresoMolinete(socio);
                    break;
                case 2:
                    inscribirSocioASesion(socio);
                    break;
                case 3:
                    cancelarInscripcionSocio(socio);
                    break;
                case 4:
                    verResumenSocio(socio);
                    break;
                default:
                    System.out.println("[!] Opción inválida.");
                    pausar();
            }
        } while(opcion != 0);

    }

    private static void validarIngresoMolinete(Socio socio) {
        label23: {
            Membresia var2 = socio.getPaseSala();
            if (var2 instanceof PaseTresVeces pase3) {
                if (pase3.estaActivo()) {
                    pase3.registrarAsistencia();
                    System.out.println("[MOLINETE ABIERTO] Asistencia registrada (" + pase3.getAsistenciasUsadas() + "/3 esta semana).");
                    break label23;
                }
            }

            System.out.println("[MOLINETE ABIERTO] Acceso habilitado para " + socio.getNombre() + ".");
        }

        pausar();
    }

    private static void inscribirSocioASesion(Socio socio) {
        System.out.println("\n--- SESIONES GUIADAS DISPONIBLES ---");
        List<SesionGuiada> disponibles = gestorSesiones.obtenerSesionesDisponibles();
        if (disponibles.isEmpty()) {
            System.out.println("No hay sesiones con lugares disponibles.");
            pausar();
        } else {
            for(SesionGuiada s : disponibles) {
                PrintStream var10000 = System.out;
                int var10001 = s.getIdSesion();
                var10000.println("(ID: " + var10001 + ") " + String.valueOf(s.getActividad()) + " | Día: " + String.valueOf(s.getDiaSemana()) + " | Horario: " + String.valueOf(s.getHorario()) + " | Vigencia: " + String.valueOf(s.getFechaInicio()) + " al " + String.valueOf(s.getFechaVencimiento()) + " | Precio: $" + s.getPrecioBase() + " | Prof: " + s.getEntrenador().getNombre() + " | Lugares disponibles: " + s.getLugaresDisponibles() + "/" + s.getCupoMaximo());
            }

            System.out.print("Ingrese ID de la sesión: ");
            SesionGuiada elegida = gestorSesiones.buscarPorId(leerEntero());
            if (elegida == null) {
                System.out.println("[!] Sesión inexistente.");
                pausar();
            } else {
                elegida.agregarReserva(socio);
                PrintStream var6 = System.out;
                String var7 = String.valueOf(elegida.getActividad());
                var6.println("[OK] Inscripción confirmada a " + var7 + " (" + String.valueOf(elegida.getDiaSemana()) + " " + String.valueOf(elegida.getHorario()) + ").");

                pausar();
            }
        }
    }

    private static void cancelarInscripcionSocio(Socio socio) {
        if (socio.getSesionesInscriptas().isEmpty()) {
            System.out.println("[AVISO] No estás inscripto en ninguna sesión guiada.");
            pausar();
        } else {
            System.out.println("\n--- TUS SESIONES INSCRIPTAS ---");

            for(SesionGuiada s : socio.getSesionesInscriptas()) {
                PrintStream var10000 = System.out;
                int var10001 = s.getIdSesion();
                var10000.println("(ID: " + var10001 + ") " + String.valueOf(s.getActividad()) + " | Día: " + String.valueOf(s.getDiaSemana()) + " | Horario: " + String.valueOf(s.getHorario()));
            }

            System.out.print("Ingrese ID de la sesión a cancelar: ");
            SesionGuiada s = gestorSesiones.buscarPorId(leerEntero());
            if (s == null) {
                System.out.println("[!] Sesión inexistente.");
            } else if (s.cancelarReserva(socio)) {
                System.out.println("[OK] Inscripción cancelada. Estado actual: " + (socio.estaActivo() ? "ACTIVO" : "INACTIVO"));
            } else {
                System.out.println("[AVISO] No estás inscripto en esa sesión guiada.");
            }

            pausar();
        }
    }

    private static void verResumenSocio(Socio socio) {
        System.out.println("\n--- MI PERFIL ---");
        System.out.println("Estado general: " + (socio.estaActivo() ? "ACTIVO" : "INACTIVO"));
        if (socio.getPaseSala() != null) {
            String estadoPase = socio.getPaseSala().estaActivo() ? "VIGENTE" : "VENCIDO";
            System.out.println("Pase de sala: " + socio.getPaseSala().getClass().getSimpleName() + " [" + estadoPase + "] (Vence: " + String.valueOf(socio.getPaseSala().getFechaVencimiento()) + ")");
        } else {
            System.out.println("Pase de sala: Ninguno");
        }

        System.out.println("Sesiones guiadas anotadas: " + socio.getSesionesInscriptas().size());

        for(SesionGuiada s : socio.getSesionesInscriptas()) {
            String estadoSesion = s.estaActivo() ? "VIGENTE" : "VENCIDA";
            PrintStream var10000 = System.out;
            String var10001 = String.valueOf(s.getActividad());
            var10000.println("  - " + var10001 + " [" + estadoSesion + "] | Día: " + String.valueOf(s.getDiaSemana()) + " | Horario: " + String.valueOf(s.getHorario()) + " (Vigencia: " + String.valueOf(s.getFechaInicio()) + " al " + String.valueOf(s.getFechaVencimiento()) + ")");
        }

        if (socio.tienePaseSalaActivo() && socio.getRutinaPersonal() != null) {
            System.out.println("Rutina personal: " + socio.getRutinaPersonal().getNombre());

            for(EjercicioRutina er : socio.getRutinaPersonal().getEjercicios()) {
                PrintStream var7 = System.out;
                String var8 = String.valueOf(er.getEjercicio());
                var7.println("  * " + var8 + ": " + er.getSeries() + "x" + er.getRepeticiones());
            }
        }

        pausar();
    }

    private static void limpiarPantalla() {
        for(int i = 0; i < 30; ++i) {
            System.out.println();
        }

    }

    private static void pausar() {
        System.out.print("\nPresione ENTER para continuar...");
        scanner.nextLine();
    }

    private static int leerEntero() {
        while(true) {
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException var1) {
                System.out.print("[!] Ingrese un número válido: ");
            }
        }
    }

    private static double leerDouble() {
        while(true) {
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException var1) {
                System.out.print("[!] Ingrese un monto numérico válido: ");
            }
        }
    }

    private static String leerSoloDigitos() {
        while(true) {
            String entrada = scanner.nextLine().trim();
            if (entrada.matches("\\d+")) {
                return entrada;
            }

            System.out.print("[!] Ingrese solo números (sin letras ni espacios): ");
        }
    }

    private static void cargarDatosDePrueba() {
        Administrador admin = new Administrador("Carlos Admin", "11111111", "admin@gym.com", "223111111", "admin", "1234");
        Entrenador ent = new Entrenador("Laura Coach", "22222222", "laura@gym.com", "223222222", "laura", "1234", EspecialidadEntrenador.CROSSFIT);
        Socio socioPaseLibre = new Socio("Franco Burcaizea", "33333333", "franco@mail.com", "223333333");
        Membresia paseLibre = new PaseLibre((double)20000.0F, LocalDate.now(), LocalDate.now().plusMonths(1L));
        socioPaseLibre.setPaseSala(paseLibre);
        Socio socioSoloClase = new Socio("Ana Perez", "44444444", "ana@mail.com", "223444444");
        Socio socioPase3 = new Socio("Juan Gomez", "55555555", "juan@mail.com", "223555555");
        Membresia pase3 = new PaseTresVeces((double)20000.0F, LocalDate.now(), LocalDate.now().plusMonths(1L));
        socioPase3.setPaseSala(pase3);
        Socio socioSesionVencida = new Socio("Lucas Vencido", "66666666", "lucas@mail.com", "223666666");
        SesionGuiada sesionVencida = new SesionGuiada((double)6000.0F, TipoActividadGrupal.PILATES, LocalDate.now().minusMonths(2L), LocalDate.now().minusMonths(1L), DiaSemana.LUNES, LocalTime.of(10, 0), 10, ent);
        sesionVencida.getInscriptos().add(socioSesionVencida);
        socioSesionVencida.agregarSesion(sesionVencida);
        SesionGuiada sesionYoga = new SesionGuiada((double)6000.0F, TipoActividadGrupal.YOGA, LocalDate.now(), DiaSemana.MARTES, LocalTime.of(19, 0), 1, ent);

        gestorPersonas.agregarPersona(admin);
        gestorPersonas.agregarPersona(ent);
        gestorPersonas.agregarPersona(socioPaseLibre);
        gestorPersonas.agregarPersona(socioSoloClase);
        gestorPersonas.agregarPersona(socioPase3);
        gestorPersonas.agregarPersona(socioSesionVencida);

        gestorMembresias.agregar(paseLibre);
        gestorMembresias.agregar(pase3);
        gestorSesiones.agregar(sesionYoga);
        gestorSesiones.agregar(sesionVencida);
    }

    static {
        scanner = new Scanner(System.in);
    }
}