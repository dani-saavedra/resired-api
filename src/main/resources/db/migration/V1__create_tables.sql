CREATE TABLE Neighborhood
(
    id      VARCHAR(36) PRIMARY KEY,
    name    VARCHAR(255),
    address VARCHAR(255),
    city    VARCHAR(255)
);
CREATE TABLE Home
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    home_type       VARCHAR(255),
    created_date    TIMESTAMP,
    neighborhood_id VARCHAR(36),
    FOREIGN KEY (neighborhood_id) REFERENCES Neighborhood (id)
);

CREATE TABLE user_app
(
    document_id   VARCHAR(500) NOT NULL,
    document_type VARCHAR(15)  NOT NULL,
    first_name    VARCHAR(255),
    last_name     VARCHAR(255),
    email         VARCHAR(255),
    password      VARCHAR(255),
    created_date  TIMESTAMP,
    update_date   TIMESTAMP,
    active        INTEGER,
    PRIMARY KEY (document_id)
);
