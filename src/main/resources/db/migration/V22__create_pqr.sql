CREATE TABLE pqrs
(
    id             INTEGER AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(100)  NOT NULL,
    description    VARCHAR(5000) NOT NULL,
    category       VARCHAR(75)   NOT NULL,
    resident       INTEGER       NOT NULL,
    creation_date  TIMESTAMP     NOT NULL,
    neighborhood   INTEGER       NOT NULL,
    state          VARCHAR(75)   NOT NULL,
    ticket_number  VARCHAR(10)   NOT NULL,
    response_date  TIMESTAMP,
    admin_responds INTEGER,
    FOREIGN KEY (neighborhood) REFERENCES neighborhood (id),
    FOREIGN KEY (resident) REFERENCES user_app (id),
    FOREIGN KEY (admin_responds) REFERENCES user_app (id)
);
