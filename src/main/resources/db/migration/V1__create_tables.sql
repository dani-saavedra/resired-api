CREATE TABLE neighborhood
(
    id      INTEGER AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(255),
    address VARCHAR(255),
    city    VARCHAR(255)
);

CREATE TABLE user_app
(
    id            INTEGER AUTO_INCREMENT PRIMARY KEY,
    document_id   VARCHAR(500) NOT NULL,
    document_type VARCHAR(15)  NOT NULL,
    first_name    VARCHAR(255) NOT NULL,
    last_name     VARCHAR(255),
    email         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    created_date  TIMESTAMP    NOT NULL,
    update_date   TIMESTAMP,
    active        INTEGER      NOT NULL,
    CONSTRAINT U_USER_EMAIL UNIQUE (email),
    CONSTRAINT U_USER_document UNIQUE (document_id)
);

CREATE TABLE home
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    name            VARCHAR(255),
    block           VARCHAR(25),
    home_number     VARCHAR(25),
    type            VARCHAR(75),
    created_date    TIMESTAMP,
    neighborhood_id INTEGER NOT NULL,
    owner_id        INTEGER NOT NULL,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id),
    FOREIGN KEY (owner_id) REFERENCES user_app (id)
);

CREATE TABLE user_rol
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    user_id         INTEGER   NOT NULL,
    rol             VARCHAR(255),
    active          INTEGER,
    neighborhood_id INTEGER   NOT NULL,
    home_id         INTEGER NULL,
    created_date    TIMESTAMP NOT NULL,
    update_date     TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user_app (id),
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id),
    FOREIGN KEY (home_id) REFERENCES home (id)
);

CREATE TABLE news
(
    id              INTEGER AUTO_INCREMENT PRIMARY KEY,
    title           VARCHAR(500)  NOT NULL,
    content         VARCHAR(2500) NOT NULL,
    category        VARCHAR(35)   NOT NULL,
    neighborhood_id INTEGER       NOT NULL,
    image           VARCHAR(500)  NOT NULL,
    created_date    TIMESTAMP     NOT NULL,
    FOREIGN KEY (neighborhood_id) REFERENCES neighborhood (id)
);

CREATE TABLE package
(
    id                  INTEGER AUTO_INCREMENT PRIMARY KEY,
    guard_id            INTEGER      NOT NULL,
    home_id             INTEGER      NOT NULL,
    receiver            varchar(500) NOT NULL,
    tracking_number     VARCHAR(50)  NOT NULL,
    status              varchar(50)  NOT NULL,
    package_transporter varchar(200) NULL,
    description         VARCHAR(200) NULL,
    received_date       TIMESTAMP    NOT NULL,
    update_date         TIMESTAMP NULL,
    FOREIGN KEY (guard_id) REFERENCES user_app (id),
    FOREIGN KEY (home_id) REFERENCES home (id)
);

CREATE TABLE visitor
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    document    VARCHAR(255) NOT NULL,
    home_id     INTEGER      NOT NULL,
    resident_id INTEGER NULL,
    guard_id    INTEGER NULL,
    created_at  TIMESTAMP    NOT NULL,
    deleted     INTEGER      NOT NULL,
    telephone   VARCHAR(30) NULL,
    FOREIGN KEY (home_id) REFERENCES home (id),
    FOREIGN KEY (resident_id) REFERENCES user_app (id),
    FOREIGN KEY (guard_id) REFERENCES user_app (id)
);


CREATE TABLE qr
(
    id          INTEGER AUTO_INCREMENT PRIMARY KEY,
    qr          VARCHAR(5000) NOT NULL,
    available   tinyint       NOT NULL,
    visitor_id  INTEGER       NOT NULL,
    created_at  TIMESTAMP     NOT NULL,
    disabled_at TIMESTAMP NULL,
    FOREIGN KEY (visitor_id) REFERENCES visitor (id)
);

CREATE TABLE visit
(
    id       INTEGER AUTO_INCREMENT PRIMARY KEY,
    qr_id    INTEGER   NOT NULL,
    check_in TIMESTAMP NOT NULL,
    FOREIGN KEY (qr_id) REFERENCES qr (id)
);
