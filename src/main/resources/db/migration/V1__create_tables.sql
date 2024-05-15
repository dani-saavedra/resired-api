CREATE TABLE neighborhood
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255),
    address VARCHAR(255),
    city    VARCHAR(255)
);

CREATE TABLE user_app
(
    document_id   VARCHAR(500) NOT NULL,
    document_type VARCHAR(15)  NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255),
    email         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    created_date  TIMESTAMP    NOT NULL,
    update_date   TIMESTAMP,
    active        INTEGER      NOT NULL,
    PRIMARY KEY (document_id)
);

CREATE TABLE home
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    home_type       VARCHAR(255),
    created_date    TIMESTAMP,
    neighborhood_id BIGINT       NOT NULL,
    owner_id        VARCHAR(500) NOT NULL,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id),
    FOREIGN KEY (owner_id) REFERENCES user_app (document_id)
);

CREATE TABLE user_rol
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    user_document   VARCHAR(500) NOT NULL,
    rol             VARCHAR(255),
    active          BOOLEAN,
    neighborhood_id BIGINT       NOT NULL,
    home_id         BIGINT       NOT NULL,
    created_date    TIMESTAMP    NOT NULL,
    update_date     TIMESTAMP,
    FOREIGN KEY (user_document) REFERENCES user_app (document_id),
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id),
    FOREIGN KEY (home_id) REFERENCES home (id)
);

CREATE TABLE news
(
    id              INT AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(500)  NOT NULL,
    content         VARCHAR(2500) NOT NULL,
    category        VARCHAR(35)   NOT NULL,
    neighborhood_id BIGINT        NOT NULL,
    image           VARCHAR(500)  NOT NULL,
    created_date    TIMESTAMP     NOT NULL,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id)
);
