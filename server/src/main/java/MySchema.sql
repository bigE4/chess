
CREATE TABLE authData(
    authToken CHAR(36) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY(authToken)
);

CREATE TABLE gameData(
    gameID SMALLINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
    whiteUsername VARCHAR(255) NULL,
    blackUsername VARCHAR(255) NULL,
    game JSON NOT NULL
);

CREATE TABLE userData(
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    PRIMARY KEY(username)
);
