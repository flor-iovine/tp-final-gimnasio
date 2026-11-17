/**
 - Clase PersonaDAO encargada de gestionar el acceso a datos (DAO) para la entidad Persona (y sus subclases Socio, Entrenador y Administrador) conectándose a MySQL.
 - Implementa la interfaz DAO<Persona> y provee operaciones de persistencia mediante JDBC:
 - - insertar: Almacena una nueva persona en la base de datos y asigna el ID generado automáticamente.
 - - listarTodos: Consulta y mapea todas las personas registradas en la base de datos instanciando la subclase correspondiente según su rol.
 - - buscarPorDNI: Busca y retorna una persona específica a partir de su número de DNI, o null si no se encuentra.
 - - actualizar: Modifica los datos de una persona existente (información personal, credenciales, estado de cuota o especialidad) según su identificador.
 - - eliminar: Remueve una persona de la base de datos según su identificador único.
 */
package dao;

import config.ConexionDB;
import enums.EspecialidadEntrenador;
import interfaces.DAO;
import modelo.Administrador;
import modelo.Empleado;
import modelo.Entrenador;
import modelo.Persona;
import modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO implements DAO<Persona> {

    private static final String SQL_INSERTAR = "INSERT INTO personas (nombre, dni, email, telefono, usuario, contrasenia, rol,estado_cuota, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM personas ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT * FROM personas WHERE dni = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE personas SET nombre = ?, dni = ?, email = ?, telefono = ?, usuario = ?, contrasenia = ?, rol = ?,estado_cuota = ?, especialidad = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM personas WHERE id = ?";

    @Override
    public void insertar(Persona persona) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            cargarParametros(ps, persona);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (!rs.next()) {
                    throw new SQLException("MySQL no devolvió el ID generado");
                }
                persona.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al insertar la persona con DNI " + persona.getDni(), e);
        }
    }

    @Override
    public List<Persona> listarTodos() {
        List<Persona> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(crearPersona(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar las personas", e);
        }
        return lista;
    }

    public Persona buscarPorDNI(String dni) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR)) {
            ps.setString(1, dni);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearPersona(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar la persona con DNI " + dni, e);
        }
        return null;
    }

    @Override
    public void actualizar(Persona persona) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            cargarParametros(ps, persona);
            ps.setInt(10, persona.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al actualizar la persona con ID " + persona.getId(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al eliminar la persona con ID " + id, e);
        }
    }

    private void cargarParametros(PreparedStatement ps, Persona persona) throws SQLException {
        ps.setString(1, persona.getNombre());
        ps.setString(2, persona.getDni());
        ps.setString(3, persona.getEmail());
        ps.setString(4, persona.getTelefono());
        ps.setNull(5, Types.VARCHAR); // usuario
        ps.setNull(6, Types.VARCHAR); // contrasenia
        ps.setNull(8, Types.BOOLEAN); // estado_cuota
        ps.setNull(9, Types.VARCHAR); // especialidad

        if (persona instanceof Socio socio) {
            ps.setString(7, "SOCIO");
            ps.setBoolean(8, socio.estaActivo());
        } else if (persona instanceof Empleado emp) {
            ps.setString(5, emp.getUsuario());
            ps.setString(6, emp.getContrasenia());

            if (emp instanceof Entrenador entrenador) {
                ps.setString(7, "ENTRENADOR");
                if (entrenador.getEspecialidad() != null) {
                    ps.setString(9, entrenador.getEspecialidad().name());
                }
            } else if (emp instanceof Administrador) {
                ps.setString(7, "ADMINISTRADOR");
            }
        } else {
            throw new IllegalArgumentException("Tipo de persona desconocido");
        }
    }

    private Persona crearPersona(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nombre = rs.getString("nombre");
        String dni = rs.getString("dni");
        String email = rs.getString("email");
        String telefono = rs.getString("telefono");
        String rol = rs.getString("rol");

        Persona p = switch (rol) {
            case "SOCIO" -> new Socio(nombre, dni, email, telefono);
            case "ENTRENADOR" -> {
                String valor = rs.getString("especialidad");
                EspecialidadEntrenador especialidad = (valor == null) ? null : EspecialidadEntrenador.valueOf(valor);
                yield new Entrenador(nombre, dni, email, telefono,
                        rs.getString("usuario"), rs.getString("contrasenia"), especialidad);
            }
            case "ADMINISTRADOR" -> new Administrador(nombre, dni, email, telefono,
                    rs.getString("usuario"), rs.getString("contrasenia"));
            default -> throw new SQLException("Rol desconocido para la persona con ID " + id);
        };

        p.setId(id);
        return p;
    }
}
