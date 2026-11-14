package dao;

import config.ConexionDB;
import enums.EspecialidadEntrenador;
import interfaces.DAO;
import modelo.Administrador;
import modelo.Entrenador;
import modelo.Persona;
import modelo.Socio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO implements DAO<Persona> {

    private static final String SQL_INSERTAR = "INSERT INTO personas (nombre, dni, email, telefono, usuario, contrasenia, rol, numero_socio, estado_cuota, legajo, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM personas ORDER BY id";
    private static final String SQL_BUSCAR = "SELECT * FROM personas WHERE id = ?";
    private static final String SQL_ACTUALIZAR = "UPDATE personas SET nombre = ?, dni = ?, email = ?, telefono = ?, usuario = ?, contrasenia = ?, rol = ?, numero_socio = ?, estado_cuota = ?, legajo = ?, especialidad = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM personas WHERE id = ?";

    @Override
    public void insertar(Persona persona) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            cargarParametros(ps, persona);
            ps.executeUpdate();

            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (!claves.next()) {
                    throw new SQLException("MySQL no devolvió el ID generado");
                }
                persona.setId(claves.getInt(1));
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

    // Este metodo seria mas util si buscara por DNI
    @Override
    public Persona buscarPorId(int id) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_BUSCAR)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearPersona(rs);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al buscar la persona con ID " + id, e);
        }
        return null;
    }

    @Override
    public void actualizar(Persona persona) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            cargarParametros(ps, persona);
            ps.setInt(12, persona.getId());
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
        ps.setString(5, persona.getUsuario());
        ps.setString(6, persona.getContrasenia());
        ps.setNull(8, Types.INTEGER);
        ps.setNull(9, Types.BOOLEAN);
        ps.setNull(10, Types.VARCHAR);
        ps.setNull(11, Types.VARCHAR);

        if (persona instanceof Socio) {
            Socio socio = (Socio) persona;
            ps.setString(7, "SOCIO");
            ps.setInt(8, socio.getNumeroSocio());
            ps.setBoolean(9, socio.isEstadoCuota());
        } else if (persona instanceof Entrenador) {
            Entrenador entrenador = (Entrenador) persona;
            ps.setString(7, "ENTRENADOR");
            ps.setString(10, entrenador.getLegajo());
            if (entrenador.getEspecialidad() != null) {
                ps.setString(11, entrenador.getEspecialidad().name());
            }
        } else if (persona instanceof Administrador) {
            Administrador administrador = (Administrador) persona;
            ps.setString(7, "ADMINISTRADOR");
            ps.setString(10, administrador.getLegajo());
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
        String usuario = rs.getString("usuario");
        String contrasenia = rs.getString("contrasenia");

        String rol = rs.getString("rol");
        switch (rol) {
            case "SOCIO":
                Socio socio = new Socio(id, nombre, dni, email, telefono, usuario, contrasenia,
                        rs.getInt("numero_socio"));
                socio.setEstadoCuota(rs.getBoolean("estado_cuota"));
                return socio;
            case "ENTRENADOR":
                String valor = rs.getString("especialidad");
                EspecialidadEntrenador especialidad =
                        valor == null ? null : EspecialidadEntrenador.valueOf(valor);
                return new Entrenador(id, nombre, dni, email, telefono, usuario, contrasenia,
                        rs.getString("legajo"), especialidad);
            case "ADMINISTRADOR":
                return new Administrador(id, nombre, dni, email, telefono, usuario, contrasenia,
                        rs.getString("legajo"));
            default:
                throw new SQLException("Rol desconocido para la persona con ID " + id);
        }
    }
}
