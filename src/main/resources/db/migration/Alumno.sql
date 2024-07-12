CREATE TABLE IF NOT EXISTS alumnos (
 	id INT NOT NULL, 
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    estado VARCHAR(10) NOT NULL,
    edad INT NOT NULL,
    PRIMARY KEY (id)
);