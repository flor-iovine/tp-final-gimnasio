package dao;

import config.ConexionDB;
import enums.DiaSemana;
import enums.TipoActividadGrupal;
import interfaces.DAO;
import modelo.Entrenador;
import modelo.SesionGuiada;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SesionGuiadaDAO implements DAO<SesionGuiada> {

    private static final String SQL_INSERTAR = "INSERT INTO sesiones_guiadas (precio_base, actividad, fecha_inicio, fecha_vencimiento, dia_semana, horario, cupo_maximo, entrenador_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_LISTAR = "SELECT * FROM sesiones_guiadas ORDER BY id";
    private static final String SQL_ACTUALIZAR = "UPDATE sesiones_guiadas SET precio_base = ?, cupo_maximo = ? WHERE id = ?";
    private static final String SQL_ELIMINAR = "DELETE FROM sesiones_guiadas WHERE id = ?";

    @Override
    public void insertar(SesionGuiada s) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_INSERTAR, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDouble(1, s.getPrecioBase());
            ps.setString(2, s.getActividad().name());
            ps.setDate(3, Date.valueOf(s.getFechaInicio()));
            ps.setDate(4, Date.valueOf(s.getFechaVencimiento()));
            ps.setString(5, s.getDiaSemana().name());
            ps.setTime(6, Time.valueOf(s.getHorario()));
            ps.setInt(7, s.getCupoMaximo());
            ps.setInt(8, s.getEntrenador().getId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    s.setIdSesion(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al insertar la sesión guiada en MySQL", e);
        }
    }

    @Override
    public List<SesionGuiada> listarTodos() {
        return listarConEntrenadores(new ArrayList<>());
    }


    public List<SesionGuiada> listarConEntrenadores(List<Entrenador> entrenadores) {
        List<SesionGuiada> lista = new ArrayList<>();
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_LISTAR);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                double precio = rs.getDouble("precio_base");
                TipoActividadGrupal act = TipoActividadGrupal.valueOf(rs.getString("actividad"));
                LocalDate inicio = rs.getDate("fecha_inicio").toLocalDate();
                LocalDate vencimiento = rs.getDate("fecha_vencimiento").toLocalDate();
                DiaSemana dia = DiaSemana.valueOf(rs.getString("dia_semana"));
                LocalTime horario = rs.getTime("horario").toLocalTime();
                int cupo = rs.getInt("cupo_maximo");
                int entrenadorId = rs.getInt("entrenador_id");

                Entrenador entrenadorAsignado = null;
                for (Entrenador e : entrenadores) {
                    if (e.getId() == entrenadorId) {
                        entrenadorAsignado = e;
                        break;
                    }
                }

                if (entrenadorAsignado != null) {
                    SesionGuiada s = new SesionGuiada(precio, act, inicio, vencimiento, dia, horario, cupo, entrenadorAsignado);
                    s.setIdSesion(id);
                    lista.add(s);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Error al listar sesiones guiadas desde MySQL", e);
        }
        return lista;
    }

    @Override
    public void actualizar(SesionGuiada s) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ACTUALIZAR)) {
            ps.setDouble(1, s.getPrecioBase());
            ps.setInt(2, s.getCupoMaximo());
            ps.setInt(3, s.getIdSesion());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al actualizar la sesión ID " + s.getIdSesion(), e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = ConexionDB.getConexion();
             PreparedStatement ps = con.prepareStatement(SQL_ELIMINAR)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("Error al eliminar la sesión ID " + id, e);
        }
    }
}
