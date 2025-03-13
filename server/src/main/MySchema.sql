
CREATE TABLE authData(
    authToken CHAR(36) PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

CREATE TABLE gameData(
    gameID SMALLINT UNSIGNED PRIMARY KEY,
    whiteUsername VARCHAR(255) NULL,
    blackUsername VARCHAR(255) NULL,
    game JSON NOT NULL,
    FOREIGN KEY (whiteUsername) REFERENCES userData(username) ON DELETE SET NULL,
    FOREIGN KEY (blackUsername) REFERENCES userData(username) ON DELETE SET NULL,
    INDEX (whiteUsername),
    INDEX (blackUsername)
);

CREATE TABLE userData(
    username VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);
