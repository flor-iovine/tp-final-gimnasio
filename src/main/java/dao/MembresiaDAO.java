/**
 - Clase MembresiaDAO encargada de gestionar el acceso a datos (DAO) para la entidad Membresia (y sus subclases PaseLibre y PaseTresVeces) conectándose a MySQL.
 - Implementa la interfaz DAO<Membresia> y provee operaciones de persistencia mediante JDBC:
 - - insertar: Almacena una nueva membresía sin socio asociado delegando en el metodo insertarParaSocio.
 - - insertarParaSocio: Inserta una nueva membresía en la base de datos vinculándola al ID del socio propietario y asigna el ID generado automáticamente.
 - - listarTodos: Retorna todas las membresías registradas delegando en el metodo listarYAsociarSocios sin realizar vinculación con socios.
 - - listarYAsociarSocios: Consulta y mapea todas las membresías instanciando la subclase correspondiente y las vincula automáticamente a su respectivo Socio en memoria.
 - - actualizar: Modifica el precio base, las fechas de vigencia y las asistencias usadas de una membresía existente según su identificador.
 - - eliminar: Remueve una membresía de la base de datos según su identificador único.
 */
package dao;

import config.ConexionDB;
import interfaces.DAO;
import modelo.Membresia;
import modelo.PaseLibre;
import modelo.PaseTresVeces;
import modelo.Socio;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MembresiaDAO implements DAO<Membresia> {

    private static final String SQL_INSERTAR = "INSERT INTO membresias (tipo, precio_base, fecha_inicio, fecha_vencimiento, asistencias_usadas, socio_id) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM membresias ORDER BY id";
    private static final String SQL_ACTUALIZAR = "UPDATE membresias SET precio_base = ?, fecha_inicio = ?, fecha_vencimiento = ?, asistencias_usadas = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM membresias WHERE id = ?";

    @Override
    public void insertar(Membresia m) {
        insertarParaSocio(m, null);
    }


    public void insertarParaSocio(Membresia m, Integer socioId) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            String tipo = (m instanceof PaseLibre) ? "PASE_LIBRE" : "PASE_TRES_VECES";
            int asistencias = (m instanceof PaseTresVeces p3) ? p3.getAsistenciasUsadas() : 0;

            ps.setString(1, tipo);
            ps.setDouble(2, m.getPrecioBase());
            ps.setDate(3, Date.valueOf(m.getFechaInicio()));
            ps.setDate(4, Date.valueOf(m.getFechaVencimiento()));
            ps.setInt(5, asistencias);
            if (socioId != null) {
                ps.setInt(6, socioId);
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    m.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al insertar la membresía en MySQL", e);
        }
    }

    @Override
    public List<Membresia> listarTodos() {
        return listarYAsociarSocios(new ArrayList<>());
    }

    public List<Membresia> listarYAsociarSocios(List<Socio> socios) {
        List<Membresia> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                double precio = rs.getDouble("precio_base");
                LocalDate inicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate vencimiento = rs.getDate("fecha_vencimiento").toLocalDate();
                int socioId = rs.getInt("socio_id");

                Membresia m = tipo.equals("PASE_LIBRE")
                        ? new PaseLibre(precio, inicio, vencimiento)
                        : new PaseTresVeces(precio, inicio, vencimiento);
                m.setId(id);

                if (!rs.wasNull()) {
                    for (Socio s : socios) {
                        if (s.getId() == socioId) {
                            s.setPaseSala(m);
                            break;
                        }
                    }
                }
                lista.add(m);
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar las membresías desde MySQL", e);
        }
        return lista;
    }

    @Override
    public void actualizar(Membresia m) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            int asistencias = (m instanceof PaseTresVeces p3) ? p3.getAsistenciasUsadas() : 0;
            ps.setDouble(1, m.getPrecioBase());
            ps.setDate(2, Date.valueOf(m.getFechaInicio()));
            ps.setDate(3, Date.valueOf(m.getFechaVencimiento()));
            ps.setInt(4, asistencias);
            ps.setInt(5, m.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al actualizar la membresía ID " + m.getId(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al eliminar la membresía ID " + id, e);
        }
    }
}