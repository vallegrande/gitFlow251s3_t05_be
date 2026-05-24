CREATE DATABASE agro_pacayales;
GO

USE agro_pacayales;
GO

-- Tabla de productos agrícolas (US4: Inventario de abonos y químicos)
CREATE TABLE producto (
    id_producto INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(MAX),
    precio DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL,
    unidad_medida VARCHAR(20),
    tipo_producto VARCHAR(50),
    proveedor VARCHAR(100),
    presentacion VARCHAR(100),
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Tabla de parcelas / terrenos de cultivo (US1: Registro de terrenos)
CREATE TABLE parcelas (
    id_parcela INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    ubicacion VARCHAR(200),
    area_hectareas DECIMAL(10,2),
    tipo_suelo VARCHAR(80),
    responsable VARCHAR(100),
    estado_riego VARCHAR(50),
    fecha_ultima_siembra DATE,
    produccion_estimada VARCHAR(100),
    cultivo_actual VARCHAR(100),
    observaciones VARCHAR(MAX),
    en_uso BIT DEFAULT 0,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Tabla de cultivos (US5: Registro de siembra)
IF NOT EXISTS (SELECT 1 FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'cultivos')
BEGIN
    CREATE TABLE cultivos (
        id_cultivo INT IDENTITY(1,1) PRIMARY KEY,
        id_parcela INT NOT NULL,
        nombre VARCHAR(100) NOT NULL,
        tipo_cultivo VARCHAR(80) NOT NULL,
        frecuencia_riego_dias INT NOT NULL,
        temperatura_ideal DECIMAL(5,2) NOT NULL,
        fecha_siembra DATE,
        requiere_sombra BIT DEFAULT 0,
        observaciones VARCHAR(MAX),
        estado BIT DEFAULT 1,
        created_at DATETIME2,
        updated_at DATETIME2,
        deleted_at DATETIME2,
        restored_at DATETIME2,
        CONSTRAINT FK_cultivos_parcelas FOREIGN KEY (id_parcela) 
            REFERENCES parcelas(id_parcela) 
            ON DELETE NO ACTION -- Cambio aquí de RESTRICT a NO ACTION
    );
END
GO

-- Tabla de usuarios (US2: Registro de trabajadores)
CREATE TABLE usuarios (
    id_usuario INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255),
    rol VARCHAR(20) DEFAULT 'OPERADOR',
    fecha_contratacion DATE,
    estado BIT DEFAULT 1,
    created_at DATETIME2,
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2
);
GO

-- Tabla transaccional: Asignación de cultivos (Transacción funcional)
CREATE TABLE asignacion_cultivo (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    parcela_id INT NOT NULL,
    nombre_cultivo NVARCHAR(100) NOT NULL,
    tipo_cultivo NVARCHAR(50) NOT NULL,
    area_asignada DECIMAL(10,2) NOT NULL,
    fecha_asignacion DATE DEFAULT GETDATE(),
    costo_estimado DECIMAL(10,2),
    responsable NVARCHAR(100),
    estado CHAR(1) NOT NULL DEFAULT 'A',
    created_at DATETIME2 DEFAULT GETDATE(),
    updated_at DATETIME2,
    deleted_at DATETIME2,
    restored_at DATETIME2,
    
    CONSTRAINT FK_AsignacionCultivo_Parcela 
        FOREIGN KEY (parcela_id) REFERENCES parcelas(id_parcela)
);
GO

-- Insertar usuario administrador por defecto
INSERT INTO usuarios (nombre, apellido, correo, password, rol, estado)
VALUES ('Administrador', 'General', 'admin@agropacayales.com', 'SqlPassword2026!', 'ADMIN', 1);
GO

-- Datos de ejemplo para parcelas
INSERT INTO parcelas (nombre, ubicacion, area_hectareas, tipo_suelo, responsable, estado_riego, cultivo_actual, en_uso, estado)
VALUES 
('Parcela Norte', 'Sector A - Valle Grande', 5.0, 'Franco arcilloso', 'Juan Pérez', 'Riego por goteo', NULL, 0, 1),
('Parcela Sur', 'Sector B - Valle Grande', 3.5, 'Franco arenoso', 'María García', 'Riego por aspersión', NULL, 0, 1),
('Parcela Este', 'Sector C - Valle Grande', 4.2, 'Arcilloso', 'Carlos López', 'Riego manual', NULL, 0, 1);
GO

-- Datos de ejemplo para asignaciones de cultivo
INSERT INTO asignacion_cultivo (parcela_id, nombre_cultivo, tipo_cultivo, area_asignada, costo_estimado, responsable, estado)
VALUES 
(1, 'Café Arábica Premium', 'CAFE', 2.5, 12500.00, 'Juan Pérez', 'A'),
(2, 'Cacao Criollo Orgánico', 'CACAO', 1.8, 8100.00, 'María García', 'A');
GO