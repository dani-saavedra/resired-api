CREATE TABLE security_company
(
    id           INTEGER AUTO_INCREMENT PRIMARY KEY,
    nit          VARCHAR(50)  NOT NULL,
    name         VARCHAR(250) NOT NULL,
    created_date TIMESTAMP    NOT NULL
);
