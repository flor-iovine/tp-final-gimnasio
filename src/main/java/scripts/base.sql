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
      numero_socio INT,
      estado_cuota BOOLEAN,
      legajo VARCHAR(255),
      especialidad ENUM('MUSCULACION', 'PILATES', 'CROSSFIT')
);

INSERT INTO personas (nombre, dni, email, telefono, usuario, contrasenia, rol) VALUES
("Administrador", "00000000", "administracion@gimnasio.com", "223111111", "admin", "1234", "ADMINISTRADOR");

