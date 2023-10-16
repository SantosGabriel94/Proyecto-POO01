CREATE TABLE Jugadores (
    ID_Jugador INT PRIMARY KEY,
    Nombre VARCHAR(255) NOT NULL,
    CorreoElectronico VARCHAR(255) NOT NULL,
    Cedula VARCHAR(20) NOT NULL UNIQUE
);

-- Tabla de Partidas
CREATE TABLE Partidas (
    ID_Partida INT PRIMARY KEY,
    ConfiguracionJuego INT, -- Clave Foránea a ConfiguracionesJuego
    FechaHoraInicio DATETIME,
    MontoPremio DECIMAL(10, 2)
);

-- Tabla de Cartones
CREATE TABLE Cartones (
    ID_Carton INT PRIMARY KEY,
    IdentificadorUnico VARCHAR(20),
    NumerosCarton VARCHAR(255), -- Puede ser una cadena JSON que representa una matriz de números
    AsignadoAJugador INT, -- Clave Foránea a Jugadores
    PartidaPertenece INT, -- Clave Foránea a Partidas
);

-- Tabla de NumerosCantados
CREATE TABLE NumerosCantados (
    ID_NumeroCantado INT PRIMARY KEY,
    Numero INT,
    PartidaPertenece INT, -- Clave Foránea a Partidas
);

-- Tabla de ConfiguracionesJuego
CREATE TABLE ConfiguracionesJuego (
    ID_Configuracion INT PRIMARY KEY,
    TipoConfiguracion VARCHAR(255)
);

-- Definir las relaciones
ALTER TABLE Partidas
ADD FOREIGN KEY (ConfiguracionJuego) REFERENCES ConfiguracionesJuego(ID_Configuracion);

ALTER TABLE Cartones
ADD FOREIGN KEY (AsignadoAJugador) REFERENCES Jugadores(ID_Jugador);

ALTER TABLE Cartones
ADD FOREIGN KEY (PartidaPertenece) REFERENCES Partidas(ID_Partida);

ALTER TABLE NumerosCantados
ADD FOREIGN KEY (PartidaPertenece) REFERENCES Partidas(ID_Partida);
