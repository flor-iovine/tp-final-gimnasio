/**
 * Clase de utilidad ConexionDB encargada de gestionar y proveer la conexión a la base de datos MySQL mediante JDBC.
 * Define las credenciales y parámetros de conexión y evita la instanciación de la clase mediante un constructor privado:
 * - Constructor: Privado para prevenir la creación de instancias (patrón de clase de utilidad).
 * - getConexion: Metodo estático que establece y retorna una conexión activa a la base de datos gimnasio_db.
 */
package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:mysql://localhost:3306/gimnasio_db";
    private static final String USUARIO = "root";
    private static final String CLAVE = "Programacion2026";

    private ConexionDB() {
    }

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
