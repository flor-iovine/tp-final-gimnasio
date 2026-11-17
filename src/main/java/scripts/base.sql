DROP DATABASE IF EXISTS gimnasio_db;
CREATE DATABASE gimnasio_db;
USE gimnasio_db;

CREATE TABLE personas (
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          nombre VARCHAR(255) NOT NULL,
                          dni VARCHAR(9) NOT NULL UNIQUE,
                          email VARCHAR(255),
                          telefono VARCHAR(255),
                          usuario VARCHAR(255) UNIQUE,
                          contrasenia VARCHAR(255),
                          rol ENUM('SOCIO', 'ENTRENADOR', 'ADMINISTRADOR') NOT NULL,
                          estado_cuota BOOLEAN,
                          especialidad ENUM('MUSCULACION', 'PILATES', 'CROSSFIT')
);

CREATE TABLE membresias (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            tipo ENUM('PASE_LIBRE', 'PASE_TRES_VECES') NOT NULL,
                            precio_base DOUBLE NOT NULL,
                            fecha_inicio DATE NOT NULL,
                            fecha_vencimiento DATE NOT NULL,
                            asistencias_usadas INT DEFAULT 0,
                            socio_id INT UNIQUE,
                            FOREIGN KEY (socio_id) REFERENCES personas(id) ON DELETE CASCADE
);

CREATE TABLE sesiones_guiadas (
                                  id INT AUTO_INCREMENT PRIMARY KEY,
                                  precio_base DOUBLE NOT NULL,
                                  actividad ENUM('PILATES', 'CROSSFIT', 'YOGA') NOT NULL,
                                  fecha_inicio DATE NOT NULL,
                                  fecha_vencimiento DATE NOT NULL,
                                  dia_semana ENUM('LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES', 'SABADO') NOT NULL,
                                  horario TIME NOT NULL,
                                  cupo_maximo INT NOT NULL,
                                  entrenador_id INT NOT NULL,
                                  FOREIGN KEY (entrenador_id) REFERENCES personas(id) ON DELETE CASCADE
);

INSERT INTO personas (nombre, dni, email, telefono, usuario, contrasenia, rol) VALUES
    ("Carlos Admin", "111", "admin@gym.com", "223111111", "admin", "1234", "ADMINISTRADOR");
