-- Crear base de datos
CREATE DATABASE sistema_hospitalario;
USE sistema_hospitalario;

create user 'root2'@'localhost' identified by '12345';
grant all privileges on sistema_hospitalario.* to 'root2'@'localhost';
flush privileges;
-- Crear tabla USUARIO
CREATE TABLE Usuario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    usuario VARCHAR(50) NOT NULL,
    contraseña VARCHAR(50) NOT NULL,
    rol varchar(40)
);

-- Insertar registros en la tabla USUARIO
INSERT INTO USUARIO (usuario, contraseña, rol) VALUES ('Joseph', 'Caza', "administrador");
INSERT INTO USUARIO (usuario, contraseña, rol) VALUES ('Juan', 'Perez', "personal medico");
INSERT INTO USUARIO (usuario, contraseña, rol) VALUES ('Maria', 'qwerty', "personal medico");

-- Crear tabla PACIENTE
CREATE TABLE PACIENTE (
    cedula VARCHAR(10) PRIMARY KEY,
    n_historial_clinico INT,
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    telefono VARCHAR(15),
    edad INT,
    descripcion_enfermedad TEXT
);

-- Insertar registros en la tabla PACIENTE
INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) 
VALUES ('1723456789', 124, 'Pedro', 'Perez', '0987654321', 45, 'Diabetes tipo 2');
INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) 
VALUES ('0928765432', 125, 'Maria', 'Gomez', '0123456789', 35, 'Hipertensión arterial');
INSERT INTO PACIENTE (cedula, n_historial_clinico, nombre, apellido, telefono, edad, descripcion_enfermedad) 
VALUES ('1122334455', 126, 'Carlos', 'Lopez', '0234567890', 50, 'Cáncer de colon');
-- Crear tabla CITA
CREATE TABLE Cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula_paciente VARCHAR(10),
    id_usuario INT,
    fecha DATETIME unique not null,
    motivo TEXT,
    FOREIGN KEY (cedula_paciente) REFERENCES Paciente(cedula),
    FOREIGN KEY (id_usuario) REFERENCES Usuario(id)
);

-- Crear tabla HISTORIAL_MEDICO
CREATE TABLE Historial_Medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cedula_paciente VARCHAR(10),
    fecha DATETIME,
    descripcion TEXT,
    FOREIGN KEY (cedula_paciente) REFERENCES Paciente(cedula)
);

-- Crear tabla RESULTADO_EXAMEN
CREATE TABLE Resultado_Examen (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_historial_medico INT,
    tipo_examen VARCHAR(100),
    resultado TEXT,
    fecha DATETIME,
    FOREIGN KEY (id_historial_medico) REFERENCES Historial_Medico(id)
);

-- Crear tabla TRATAMIENTO
CREATE TABLE Tratamiento (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_historial_medico INT,
    medicamento VARCHAR(100),
    dosis VARCHAR(100),
    duracion VARCHAR(100),
    FOREIGN KEY (id_historial_medico) REFERENCES Historial_Medico(id)
);

-- Insertar registros en la tabla CITA
INSERT INTO Cita (cedula_paciente, id_usuario, fecha, motivo) 
VALUES ('1723456789', 2, '2023-07-23 10:00:00', 'Chequeo general');
INSERT INTO Cita (cedula_paciente, id_usuario, fecha, motivo) 
VALUES ('0928765432', 2, '2023-07-23 11:00:00', 'Revisión de presión arterial');
INSERT INTO Cita (cedula_paciente, id_usuario, fecha, motivo) 
VALUES ('1122334455', 2, '2023-07-23 12:00:00', 'Consulta sobre tratamiento de cáncer');

-- Insertar registros en la tabla HISTORIAL_MEDICO
INSERT INTO Historial_Medico (cedula_paciente, fecha, descripcion) 
VALUES ('1723456789', '2023-07-23 10:00:00', 'Paciente con historial de diabetes tipo 2');
INSERT INTO Historial_Medico (cedula_paciente, fecha, descripcion) 
VALUES ('0928765432', '2023-07-23 11:00:00', 'Paciente con hipertensión arterial');
INSERT INTO Historial_Medico (cedula_paciente, fecha, descripcion) 
VALUES ('1122334455', '2023-07-23 12:00:00', 'Paciente en tratamiento por cáncer de colon');

-- Insertar registros en la tabla RESULTADO_EXAMEN
INSERT INTO Resultado_Examen (id_historial_medico, tipo_examen, resultado, fecha) 
VALUES (1, 'Examen de sangre', 'Niveles de glucosa elevados', '2023-07-23 10:30:00');
INSERT INTO Resultado_Examen (id_historial_medico, tipo_examen, resultado, fecha) 
VALUES (2, 'Medición de presión arterial', '140/90 mmHg', '2023-07-23 11:30:00');
INSERT INTO Resultado_Examen (id_historial_medico, tipo_examen, resultado, fecha) 
VALUES (3, 'Colonoscopia', 'Presencia de pólipos', '2023-07-23 12:30:00');

-- Insertar registros en la tabla TRATAMIENTO
INSERT INTO Tratamiento (id_historial_medico, medicamento, dosis, duracion) 
VALUES (1, 'Metformina', '500 mg', '3 meses');
INSERT INTO Tratamiento (id_historial_medico, medicamento, dosis, duracion) 
VALUES (2, 'Enalapril', '10 mg', '1 mes');
INSERT INTO Tratamiento (id_historial_medico, medicamento, dosis, duracion) 
VALUES (3, 'Quimioterapia', 'Ciclo 1', '6 meses');

-- Crear vistas para reportes estadísticos

-- Vista para contar citas por personal médico
CREATE VIEW Reporte_Citas_Por_Medico AS
SELECT 
    u.usuario AS medico,
    COUNT(c.id) AS total_citas
FROM 
    Cita c
JOIN 
    Usuario u ON c.id_usuario = u.id
WHERE 
    u.rol = 'personal medico'
GROUP BY 
    u.usuario;

-- Vista para listar pacientes y sus tratamientos
CREATE VIEW Reporte_Pacientes_Tratamientos AS
SELECT 
    p.cedula,
    p.nombre,
    p.apellido,
    t.medicamento,
    t.dosis,
    t.duracion
FROM 
    Paciente p
JOIN 
    Historial_Medico h ON p.cedula = h.cedula_paciente
JOIN 
    Tratamiento t ON h.id = t.id_historial_medico;

