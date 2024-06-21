CREATE TABLE resident_request
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    document     VARCHAR(50)  NOT NULL,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    email        VARCHAR(150) NOT NULL,
    house        VARCHAR(150) NOT NULL,
    neighborhood INTEGER      NOT NULL,
    created_date TIMESTAMP    NOT NULL,
    decision     VARCHAR(50)  NOT NULL,
    updated_date TIMESTAMP
);
