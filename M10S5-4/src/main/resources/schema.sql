DROP TABLE IF EXISTS Alumnos;
create table Alumnos (matricula varchar(10), nombre varchar(25), paterno varchar(25), fnac date, estatura decimal(10,2), id_estado int NULL, PRIMARY KEY (matricula));

DROP TABLE IF EXISTS Calificaciones;
create table Calificaciones (id int not null AUTO_INCREMENT primary key, materia varchar(30), calificacion integer, alumnos_matricula varchar(10));


DROP TABLES IF EXISTS Estados;
CREATE TABLE Estados (
  id_estado int NOT NULL,
  estado varchar(50) DEFAULT NULL,
  abreviatura varchar(5) DEFAULT NULL,
  PRIMARY KEY (id_estado),
  UNIQUE KEY estado_UNIQUE (estado),
  UNIQUE KEY abreviatura_UNIQUE (abreviatura)
);

DROP TABLES IF EXISTS Grupos;
CREATE TABLE Grupos (
    id_grupo INT AUTO_INCREMENT PRIMARY KEY,
    grupo VARCHAR(30)
);

DROP TABLES IF EXISTS Alumnos_Grupos;
CREATE TABLE Alumnos_Grupos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    matricula varchar(10),
    id_grupo INT,
    anio INT
);
