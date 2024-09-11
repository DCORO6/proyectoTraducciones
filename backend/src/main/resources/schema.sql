DROP TABLE IF EXISTS idioma;
DROP TABLE IF EXISTS palabra;

CREATE TABLE idioma (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    codigo_iso CHAR(3) NOT NULL UNIQUE,
    alfabeto VARCHAR(20) CHECK (alfabeto IN ('LATINO', 'TIBETANO', 'ARMENIO', 'ÁRABE', 'CIRÍLICO', 'COREANO', 'GEORGIANO')),  -- Actualizado con tildes
    region VARCHAR(100) NOT NULL
);

CREATE TABLE palabra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    palabra VARCHAR(100) NOT NULL,
    descripcion VARCHAR(1000),
    ejemplo_uso VARCHAR(1000),
    nivel_dificultad INT NOT NULL CHECK (nivel_dificultad BETWEEN 0 AND 10),
    frecuencia_uso INT NOT NULL CHECK (frecuencia_uso BETWEEN 0 AND 10),
    fecha_creacion VARCHAR(100) NOT NULL,
    idioma_id INT NOT NULL,
    FOREIGN KEY (idioma_id) REFERENCES idioma(id)
);